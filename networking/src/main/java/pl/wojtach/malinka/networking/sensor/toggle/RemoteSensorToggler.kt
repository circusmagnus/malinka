package pl.wojtach.malinka.networking.sensor.toggle

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import pl.wojtach.malinka.networking.RetrofitProvider
import pl.wojtach.malinka.networking.getPassword
import pl.wojtach.malinka.networking.getUser
import pl.wojtach.malinka.statemachine.StateMachine
import pl.wojtach.malinka.statemachine.entities.Sensor
import pl.wojtach.malinka.statemachine.states.State
import retrofit2.Response
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
        RetrofitProvider.getPasswordedRetrofit(stateMachine.getUser(), stateMachine.getPassword())
                .create(RetrofitSensorToggler::class.java)
                .setDeviceStatus(macAddres = sensor.mac, type = sensor.type, isActive = if (sensor.isActive) 1 else 0)
                .subscribeOn(Schedulers.io())
                .subscribe({ signalSucces(sensor) }, { signalError(sensor, it) })
    }

    private fun signalError(sensor: Sensor, error: Throwable) {
        stateMachine.dispatch(SetStatusErrorAction(sensor))
    }


    private fun signalSucces(sensor: Sensor) {
        stateMachine.dispatch(SetStatusSuccesAction(sensor))
    }


}

interface RetrofitSensorToggler {


    @GET("setDeviceStatus")
    fun setDeviceStatus(@Query("sensor") macAddres: String,
                        @Query("type") type: Int,
                        @Query("status") isActive: Int): Single<Response<Void>>
}
