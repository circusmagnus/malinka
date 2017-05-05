package pl.wojtach.malinka

import pl.wojtach.malinka.state.LoginState
import pl.wojtach.malinka.state.PHASE
import pl.wojtach.malinka.state.State
import pl.wojtach.malinka.state.createInitialState


/**
 * Created by lukaszwojtach on 17.04.2017.
 */

interface Action<STATE> {
    fun transformState(oldState: STATE): STATE
}

class InitStateAction : Action<State> {
    override fun transformState(oldState: State): State = createInitialState()

}

class LoginAction(val user: String, val password: String) : Action<State> {
    override fun transformState(oldState: State): State {
        return oldState.copy(loginState = LoginState(
                phaseOfLogging = PHASE.IN_PROGRESS,
                currentUser = user,
                currentPassword = password
        ))
    }

}
