package pl.wojtach.malinka.networking

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import pl.wojtach.malinka.statemachine.Action
import pl.wojtach.malinka.statemachine.StateMachine
import pl.wojtach.malinka.statemachine.entities.Sensor
import pl.wojtach.malinka.statemachine.states.State
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by lukaszwojtach on 08.06.2017.
 */
interface RemoteSensorToggler {
    companion object {
        fun withRetrofit(stateMachine: StateMachine<State>): RemoteSensorToggler = RemoteSensorTogglerRetrofit(stateMachine)
    }
}

class RemoteSensorTogglerRetrofit(val stateMachine: StateMachine<State>) : RemoteSensorToggler {

    init {
        scan(stateMachine.getState())
        stateMachine.getPublisher().subscribe { scan(it) }
    }

    private fun scan(state: State) {
        state.sensorState.sensors
                .filter { it.shouldSync == true }
                .forEach { changeStatus(it) }
    }

    private fun changeStatus(sensor: Sensor) {
        RetrofitProvider
                .getPasswordedRetrofit(stateMachine.getUser(), stateMachine.getPassword())
                .create(RetrofitSensorToggler::class.java)
                .setDeviceStatus(macAddres = sensor.mac, type = sensor.type, isActive = if (sensor.isActive) 1 else 0)
                .subscribeOn(Schedulers.io())
                .subscribe({ signalSucces(sensor) }, { signalError(sensor) })
    }

    private fun signalError(sensor: Sensor) {
        stateMachine.dispatch(SetStatusErrorAction(sensor))
    }


    private fun signalSucces(sensor: Sensor) {
        stateMachine.dispatch(SetStatusSuccesAction(sensor))
    }


}

internal class SetStatusSuccesAction(val sensor: Sensor) : Action<State> {

    override fun transformState(oldState: State): State = oldState.copy(
            sensorState = oldState.sensorState.copy(
                    sensors = oldState.sensorState.sensors.map {
                        it.takeIf { it.mac == sensor.mac && it.type == sensor.type }
                                ?.copy(shouldSync = false)
                                ?: it
                    }
            )
    )

}

internal class SetStatusErrorAction(val sensor: Sensor) : Action<State> {
    override fun transformState(oldState: State): State = oldState.copy(
            sensorState = oldState.sensorState.copy(
                    sensors = oldState.sensorState.sensors.map {
                        it.takeIf { it.mac == sensor.mac }
                                ?.copy(hasError = true)
                                ?: it
                    }
            )
    )


}

interface RetrofitSensorToggler {


    @GET("setDeviceStatus")
    fun setDeviceStatus(@Query("sensor") macAddres: String,
                        @Query("type") type: Int,
                        @Query("status") isActive: Int): Observable<Void>
}
