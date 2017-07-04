package pl.wojtach.cache


import pl.wojtach.malinka.statemachine.states.PHASE
import pl.wojtach.malinka.statemachine.states.State

class StartLoginAction(val user: String, val password: String) : pl.wojtach.malinka.statemachine.Action<State> {
    override fun transformState(oldState: pl.wojtach.malinka.statemachine.states.State): pl.wojtach.malinka.statemachine.states.State {
        return oldState.copy(loginState = pl.wojtach.malinka.statemachine.states.LoginState(
                phaseOfLogging = PHASE.IN_PROGRESS,
                currentUser = user,
                currentPassword = password
        ))
    }
}