package pl.wojtach.malinka.ui.login_screen

import pl.wojtach.malinka.core.LoginAction
import pl.wojtach.malinka.core.StateMachine
import pl.wojtach.malinka.state.State

/**
 * Created by lukaszwojtach on 05.05.2017.
 */
//not needed probably

class LoginActionDispatcher(val stateMachine: StateMachine<State>) {

    fun login(user: String, password: String) {
        stateMachine.dispatch(LoginAction(user, password))
    }
}