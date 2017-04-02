package pl.wojtach.malinka.cache

import android.content.Context
import android.content.SharedPreferences
import io.reactivex.schedulers.Schedulers
import pl.wojtach.malinka.statemachine.StateMachine
import pl.wojtach.malinka.statemachine.states.PHASE
import pl.wojtach.malinka.statemachine.states.State


/**
 * Created by Lukasz on 04.07.2017.
 */
interface LoginWriter {
    companion object {
        fun newInstance(stateMachine: StateMachine<State>, context: Context): LoginWriter =
                SharedPrefsLoginWriter(stateMachine, context)
    }
}

internal class SharedPrefsLoginWriter(stateMachine: StateMachine<State>, val context: Context) : LoginWriter {
    companion object {
        const val sharedPrefsName = "Malinka"
        const val userKey = "user"
        const val passwordKey = "password"
    }

    init {
        stateMachine.getPublisher()
                .subscribeOn(Schedulers.io())
                .subscribe { scan(it) }
    }

    private fun scan(state: State) {
        state
                .takeIf { it.loginState.phaseOfLogging == PHASE.IN_PROGRESS }
                ?.let { with(getPrefsEditor()) { saveCredentials(it) } }
    }

    private fun SharedPreferences.Editor.saveCredentials(state: State) {
        putString(userKey, state.loginState.currentUser)
        putString(passwordKey, state.loginState.currentPassword)
        apply()
    }

    private fun getPrefsEditor() = context.getSharedPreferences(sharedPrefsName, Context.MODE_PRIVATE).edit()
}