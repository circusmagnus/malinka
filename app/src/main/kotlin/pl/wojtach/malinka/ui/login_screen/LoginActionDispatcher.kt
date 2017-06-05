package pl.wojtach.malinka.ui.login_screen

import android.widget.TextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import pl.wojtach.malinka.actions.LoginErrorAction
import pl.wojtach.malinka.actions.LoginSuccesAction
import pl.wojtach.malinka.actions.StartLoginAction
import pl.wojtach.malinka.data.SensorDataFetcher
import pl.wojtach.malinka.statemachine.StateMachine
import pl.wojtach.malinka.statemachine.entities.Sensor
import pl.wojtach.malinka.statemachine.states.State


import java.util.concurrent.TimeUnit

/**
 * Created by lukaszwojtach on 05.05.2017.
 */


class LoginActionDispatcher(val stateMachine: StateMachine<State>, val sensorDataFetcher: SensorDataFetcher) {

    val eventsReceiver: PublishSubject<LoginData> = PublishSubject.create<LoginData>()


    init {
        eventsReceiver
                .throttleFirst(5, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .subscribe { runLoginActions(it) }
    }

    fun login(userField: TextView, passwordField: TextView) {
        eventsReceiver.onNext(LoginData(userField.text.toString(), passwordField.text.toString()))
    }

    private fun runLoginActions(data: LoginData) {
        toLoadingState(data)
        sensorDataFetcher.fetchData().subscribe({ toLoggedInState(it) }, { toLoginErrorState(it.message ?: "Error logging in") })
    }

    private fun toLoadingState(data: LoginData) = stateMachine.dispatch(StartLoginAction(data.user, data.password))

    private fun toLoggedInState(sensors: List<Sensor>) = stateMachine.dispatch(LoginSuccesAction(sensors))

    private fun toLoginErrorState(errorMessage: String) = stateMachine.dispatch(LoginErrorAction(errorMessage))
}

data class LoginData(val user: String, val password: String)