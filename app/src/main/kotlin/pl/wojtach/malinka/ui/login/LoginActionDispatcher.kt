package pl.wojtach.malinka.ui.login

import android.widget.TextView
import pl.wojtach.malinka.networking.SensorDataFetcher
import pl.wojtach.malinka.statemachine.StateMachine
import pl.wojtach.malinka.statemachine.states.State
import pl.wojtach.malinka.ui.login.actions.StartLoginAction

/**
 * Created by lukaszwojtach on 05.05.2017.
 */


internal class LoginActionDispatcher(val stateMachine: StateMachine<State>, val sensorDataFetcher: SensorDataFetcher) {

    fun login(userField: TextView, passwordField: TextView) =
            stateMachine.dispatch(
                    StartLoginAction(userField.text.toString(), passwordField.text.toString())
            )
}

