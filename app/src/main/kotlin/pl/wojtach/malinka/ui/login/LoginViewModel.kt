package pl.wojtach.malinka.ui.login

import android.databinding.ObservableField
import android.view.View
import pl.wojtach.malinka.statemachine.StateMachine
import pl.wojtach.malinka.statemachine.states.State


/**
 * Created by lukaszwojtach on 16.04.2017.
 */
internal class LoginViewModel(stateMachine: StateMachine<State>) {

    var isInProgress = ObservableField(View.INVISIBLE)
    var user = ObservableField("")
    var password = ObservableField("")
    var errorMessage: ObservableField<String?> = ObservableField(null)
    var errorMessageVisible = ObservableField(View.INVISIBLE)

    init {
        render(stateMachine.getState())
        stateMachine.getPublisher().subscribe { render(it) }
    }

    private fun render(state: State) {
        isInProgress.set(determineProgressVisibility(state))
        user.set(state.loginState.currentUser)
        password.set(state.loginState.currentPassword)
        // error = ObservableField(stateMachine.getState().errorState != null )
        errorMessage.set(state.errorState?.errorMessages?.firstOrNull() ?: "")
        errorMessageVisible.set(determineErrorVisiblity(state))
    }

    private fun determineErrorVisiblity(state: State): Int? =
            if (state.errorState?.errorMessages?.first() == null) View.INVISIBLE else View.VISIBLE

    private fun determineProgressVisibility(state: pl.wojtach.malinka.statemachine.states.State) =
            if (state.loginState.phaseOfLogging == pl.wojtach.malinka.statemachine.states.PHASE.IN_PROGRESS) android.view.View.VISIBLE
            else android.view.View.INVISIBLE

}