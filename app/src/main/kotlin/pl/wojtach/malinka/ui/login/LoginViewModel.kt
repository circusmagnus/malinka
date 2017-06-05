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

    init {
        render(stateMachine.getState())
        stateMachine.getPublisher().subscribe { render(it) }
    }

    private fun render(state: pl.wojtach.malinka.statemachine.states.State) {
        isInProgress.set(determineProgressVisibility(state))
        user.set(state.loginState.currentUser)
        password.set(state.loginState.currentPassword)
        // error = ObservableField(stateMachine.getState().errorState != null )
        errorMessage.set(state.errorState?.errorMessages?.firstOrNull() ?: "")
    }

    private fun determineProgressVisibility(state: pl.wojtach.malinka.statemachine.states.State) =
            if (state.loginState.phaseOfLogging == pl.wojtach.malinka.statemachine.states.PHASE.IN_PROGRESS) android.view.View.VISIBLE
            else android.view.View.INVISIBLE

}