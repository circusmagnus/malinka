package pl.wojtach.malinka.networking

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import pl.wojtach.malinka.statemachine.StateMachine
import pl.wojtach.malinka.statemachine.entities.Sensor
import pl.wojtach.malinka.statemachine.states.PHASE
import pl.wojtach.malinka.statemachine.states.State
import retrofit2.http.GET


/**
 * Created by Lukasz on 29.05.2017.
 */
interface SensorDataFetcher {
    //fun fetchData(): Single<List<Sensor>>

    companion object {
        fun withRetrofit(stateMachine: StateMachine<State>): SensorDataFetcher = SensorDataFetcherRetrofit(stateMachine)
    }
}

internal class SensorDataFetcherRetrofit(val stateMachine: StateMachine<State>) : SensorDataFetcher {

    init {
        scan(stateMachine.getState())
        stateMachine.getPublisher().subscribe { scan(it) }
    }

    private fun scan(state: State) {
        when (state.loginState.phaseOfLogging) {
            PHASE.IN_PROGRESS -> fetchData()
            else -> {
            }
        }

        when (state.sensorState.phase) {
            PHASE.IN_PROGRESS -> fetchData()
            else -> {
            }
        }
    }

    private fun fetchData() {
        RetrofitProvider
                .getPasswordedRetrofit(stateMachine.getUser(), stateMachine.getPassword())
                .create(DataProvider::class.java)
                .getSensors()
                .subscribeOn(Schedulers.io())
                .subscribe({ signalSensorsLoaded(it) }, { signalError(it) })
    }

    private fun signalError(it: Throwable) {
        LoginErrorAction(it.toString())
    }

    private fun signalSensorsLoaded(sensors: List<Sensor>) {
        stateMachine.dispatch(LoginSuccesAction(sensors))
    }
}

interface DataProvider {

    @GET("lastStatus")
    fun getSensors(): Single<List<Sensor>>

}