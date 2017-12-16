package pl.wojtach.malinka.statemachine


import pl.wojtach.malinka.statemachine.states.LoginState
import pl.wojtach.malinka.statemachine.states.PHASE
import pl.wojtach.malinka.statemachine.states.State

class StartLoginAction(val user: String, val password: String, val baseUrl: String) : Action<State> {
    override fun transformState(oldState: State): State {
        return oldState.copy(loginState = LoginState(
                phaseOfLogging = PHASE.IN_PROGRESS,
                currentUser = user,
                currentPassword = password,
                currentBaseUrl = baseUrl
        ))
    }
}