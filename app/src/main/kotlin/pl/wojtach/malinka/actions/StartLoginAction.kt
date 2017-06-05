package pl.wojtach.malinka.actions


import pl.wojtach.malinka.statemachine.Action
import pl.wojtach.malinka.statemachine.states.LoginState
import pl.wojtach.malinka.statemachine.states.PHASE
import pl.wojtach.malinka.statemachine.states.State

class StartLoginAction(val user: String, val password: String) : Action<State> {
    override fun transformState(oldState: State): State {
        return oldState.copy(loginState = LoginState(
                phaseOfLogging = PHASE.IN_PROGRESS,
                currentUser = user,
                currentPassword = password
        ))
    }
}