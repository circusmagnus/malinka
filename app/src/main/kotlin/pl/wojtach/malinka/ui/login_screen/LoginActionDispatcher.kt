package pl.wojtach.malinka.ui.login_screen

import android.view.View
import android.widget.TextView
import pl.wojtach.malinka.core.LoginAction
import pl.wojtach.malinka.core.StateMachine
import pl.wojtach.malinka.state.State

/**
 * Created by lukaszwojtach on 05.05.2017.
 */


class LoginActionDispatcher(val stateMachine: StateMachine<State>,
                            val userField: TextView,
                            val passwordField: TextView) {

    fun login(view: View) {
        stateMachine.dispatch(
                LoginAction(user = userField.text.toString(),
                        password = passwordField.text.toString()
                )
        )
    }
}