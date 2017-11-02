package pl.wojtach.malinka.networking.list

import pl.wojtach.malinka.statemachine.Action
import pl.wojtach.malinka.statemachine.states.PHASE.FAILED
import pl.wojtach.malinka.statemachine.states.State


internal class LoginErrorAction(val errorMessage: String) : Action<State> {
    override fun transformState(oldState: State): State {
        return oldState.copy(
                loginState = oldState.loginState.copy(phaseOfLogging = FAILED),
                errorState = pl.wojtach.malinka.statemachine.states.ErrorState(createNewErrorSet(oldState.errorState?.errorMessages))
        )
    }

    private fun createNewErrorSet(oldErrorMessages: Set<String>?) = (oldErrorMessages ?: emptySet()).plusElement(errorMessage)
}