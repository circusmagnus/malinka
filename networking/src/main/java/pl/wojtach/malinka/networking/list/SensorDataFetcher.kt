package pl.wojtach.malinka.networking.list

import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import pl.wojtach.malinka.networking.*
import pl.wojtach.malinka.statemachine.StateMachine
import pl.wojtach.malinka.statemachine.states.PHASE
import pl.wojtach.malinka.statemachine.states.State
import retrofit2.http.GET
import retrofit2.http.Query


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
        val sensorData = RetrofitProvider
                .getPasswordedRetrofit(stateMachine.getUser(), stateMachine.getPassword(), stateMachine.getBaseUrl())
                .create(LastSensorStatusProvider::class.java)
                .getSensors()
                .subscribeOn(Schedulers.io())
//                .subscribe({ signalSensorsLoaded(it) }, { signalError(it) })

        val alertsData = RetrofitProvider
                .getPasswordedRetrofit(stateMachine.getUser(), stateMachine.getPassword(), stateMachine.getBaseUrl())
                .create(RecentAlertsProvider::class.java)
                .getAlerts(fromPeriod = 1)
                .subscribeOn(Schedulers.io())

        sensorData
                .zipWith(alertsData,
                        BiFunction { wsSensors: List<WsSensor>, wsAlerts: List<WsAlert> -> Pair(wsSensors, wsAlerts) }
                ).subscribe({ sensorsAlertsPair -> signalSensorsLoaded(sensorsAlertsPair.first, sensorsAlertsPair.second) }, { signalError(it) })
    }

    private fun signalError(it: Throwable) {
        stateMachine.dispatch(LoginErrorAction(it.toString()))
    }

    private fun signalSensorsLoaded(sensors: List<WsSensor>, alerts: List<WsAlert>) {
        stateMachine.dispatch(LoginSuccesAction(sensors, alerts))
    }
}

interface LastSensorStatusProvider {

    @GET("lastStatus")
    fun getSensors(): Single<List<WsSensor>>

}

interface RecentAlertsProvider {

    @GET("recentAlerts")
    fun getAlerts(@Query("period") fromPeriod: Int): Single<List<WsAlert>>
}