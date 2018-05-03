package pl.wojtach.malinka.ui.login

import android.databinding.ObservableField
import android.view.View
import pl.wojtach.malinka.statemachine.StateMachine
import pl.wojtach.malinka.statemachine.states.PHASE
import pl.wojtach.malinka.statemachine.states.State


/**
 * Created by lukaszwojtach on 16.04.2017.
 */
internal class LoginViewModel(stateMachine: StateMachine<State>) {

    val isInProgress = ObservableField(View.INVISIBLE)
    val user = ObservableField("")
    val password = ObservableField("")
    val errorMessage= ObservableField("")
    val errorMessageVisible = ObservableField(View.INVISIBLE)
    val baseUrl = ObservableField("")

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
        baseUrl.set(state.loginState.currentBaseUrl)
    }

    private fun determineErrorVisiblity(state: State): Int? =
            if (state.errorState?.errorMessages?.first() == null) View.INVISIBLE else View.VISIBLE

    private fun determineProgressVisibility(state: State) =
            if (state.loginState.phaseOfLogging == PHASE.IN_PROGRESS) android.view.View.VISIBLE
            else android.view.View.INVISIBLE

}