package pl.wojtach.malinka.networking.list

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import pl.wojtach.malinka.networking.RetrofitProvider
import pl.wojtach.malinka.networking.WsSensor
import pl.wojtach.malinka.networking.getPassword
import pl.wojtach.malinka.networking.getUser
import pl.wojtach.malinka.statemachine.StateMachine
import pl.wojtach.malinka.statemachine.states.PHASE
import pl.wojtach.malinka.statemachine.states.State
import retrofit2.http.GET


/**
 * Created by Lukasz on 29.05.2017.
 */
interface SensorDataFetcher {

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
        if (state.loginState.phaseOfLogging == PHASE.IN_PROGRESS
                || state.sensorState.phase == PHASE.IN_PROGRESS) fetchData()
    }

    private fun fetchData() {
        RetrofitProvider.getPasswordedRetrofit(stateMachine.getUser(), stateMachine.getPassword())
                .create(DataProvider::class.java)
                .getSensors()
                .subscribeOn(Schedulers.io())
                .subscribe({ signalSensorsLoaded(it) }, { signalError(it) })
    }

    private fun signalError(it: Throwable) {
        stateMachine.dispatch(LoginErrorAction(it.toString()))
    }

    private fun signalSensorsLoaded(sensors: List<WsSensor>) {
        stateMachine.dispatch(LoginSuccesAction(sensors))
    }
}

interface DataProvider {

    @GET("lastStatus")
    fun getSensors(): Single<List<WsSensor>>

}