package pl.wojtach.malinka.ui.login_screen

import android.widget.TextView
import pl.wojtach.malinka.core.LoginAction
import pl.wojtach.malinka.core.StateMachine
import pl.wojtach.malinka.state.State

/**
 * Created by lukaszwojtach on 05.05.2017.
 */


class LoginActionDispatcher(val stateMachine: StateMachine<State>) {

    fun login(userField: TextView, passwordField: TextView) {
        stateMachine.dispatch(LoginAction(userField.text.toString(), passwordField.text.toString()))
    }
}