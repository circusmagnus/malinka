package pl.wojtach.malinka.ui.login

import android.widget.TextView
import pl.wojtach.malinka.statemachine.StartLoginAction
import pl.wojtach.malinka.statemachine.StateMachine
import pl.wojtach.malinka.statemachine.states.State

/**
 * Created by lukaszwojtach on 05.05.2017.
 */


internal class LoginActionDispatcher(val stateMachine: StateMachine<State>) {

    fun login(userField: TextView, passwordField: TextView) =
            stateMachine.dispatch(
                    StartLoginAction(userField.text.toString(), passwordField.text.toString())
            )
}

