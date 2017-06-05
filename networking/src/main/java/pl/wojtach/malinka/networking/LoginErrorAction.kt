package pl.wojtach.malinka.networking


internal class LoginErrorAction(val errorMessage: String) : pl.wojtach.malinka.statemachine.Action<pl.wojtach.malinka.statemachine.states.State> {
    override fun transformState(oldState: pl.wojtach.malinka.statemachine.states.State): pl.wojtach.malinka.statemachine.states.State {
        return oldState.copy(
                loginState = oldState.loginState.copy(phaseOfLogging = pl.wojtach.malinka.statemachine.states.PHASE.FAILED),
                errorState = pl.wojtach.malinka.statemachine.states.ErrorState(createNewErrorSet(oldState.errorState?.errorMessages))
        )
    }

    private fun createNewErrorSet(oldErrorMessages: Set<String>?) = (oldErrorMessages ?: emptySet()).plusElement(errorMessage)
}