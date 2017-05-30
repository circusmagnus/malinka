package pl.wojtach.malinka.actions

import pl.wojtach.malinka.state.ErrorState
import pl.wojtach.malinka.state.PHASE
import pl.wojtach.malinka.state.State

class LoginErrorAction(val errorMessage: String) : Action<State> {
    override fun transformState(oldState: State): State {
        return oldState.copy(
                loginState = oldState.loginState.copy(phaseOfLogging = PHASE.FAILED),
                errorState = ErrorState(createNewErrorSet(oldState.errorState?.errorMessages))
        )
    }

    private fun createNewErrorSet(oldErrorMessages: Set<String>?) = (oldErrorMessages ?: emptySet()).plusElement(errorMessage)
}