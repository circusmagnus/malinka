package pl.wojtach.malinka.ui.login_screen

import android.databinding.ObservableField
import android.view.View
import pl.wojtach.malinka.core.StateMachine
import pl.wojtach.malinka.state.PHASE
import pl.wojtach.malinka.state.State

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

    private fun render(state: State) {
        isInProgress.set(determineProgressVisibility(state))
        user.set(state.loginState.currentUser)
        password.set(state.loginState.currentPassword)
        // error = ObservableField(stateMachine.getState().errorState != null )
        errorMessage.set(state.errorState?.message)
    }

    private fun determineProgressVisibility(state: State) =
            if (state.loginState.phaseOfLogging == PHASE.IN_PROGRESS) View.VISIBLE
            else View.INVISIBLE

}