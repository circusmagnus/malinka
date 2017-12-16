package pl.wojtach.malinka.networking.list

import pl.wojtach.malinka.statemachine.Action
import pl.wojtach.malinka.statemachine.states.ErrorState
import pl.wojtach.malinka.statemachine.states.PHASE.FAILED
import pl.wojtach.malinka.statemachine.states.State


internal class LoginErrorAction(val errorMessage: String) : Action<State> {
    override fun transformState(oldState: State): State {
        return oldState.copy(
                loginState = oldState.loginState.copy(phaseOfLogging = FAILED),
                errorState = ErrorState(createNewErrorSet(oldState.errorState?.errorMessages)),
                sensorState = oldState.sensorState.copy(phase = FAILED)
        )
    }

    private fun createNewErrorSet(oldErrorMessages: Set<String>?) = (oldErrorMessages ?: emptySet()).plusElement(errorMessage)
}