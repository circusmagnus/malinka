package pl.wojtach.malinka.cache

import android.content.Context
import io.reactivex.schedulers.Schedulers
import pl.wojtach.malinka.statemachine.StartLoginAction
import pl.wojtach.malinka.statemachine.StateMachine
import pl.wojtach.malinka.statemachine.states.PHASE
import pl.wojtach.malinka.statemachine.states.State


/**
 * Created by Lukasz on 05.07.2017.
 */
interface CachedLoginPerformer {
    companion object {
        fun newInstance(stateMachine: StateMachine<State>, context: Context): CachedLoginPerformer =
                SharedPrefsLoginPerformer(stateMachine, context)
    }
}

internal class SharedPrefsLoginPerformer(val stateMachine: StateMachine<State>, val context: Context) : CachedLoginPerformer {
    init {
        scan(stateMachine.getState())
        stateMachine.getPublisher()
                .subscribeOn(Schedulers.io())
                .subscribe { scan(it) }
    }

    private fun scan(state: State) {
        state
                .takeIf { state.loginState.phaseOfLogging == PHASE.NOT_STARTED }
                ?.let {
                    with(sharedPreferences(), {
                        val user = getString(SharedPrefsLoginWriter.userKey, "")
                        val password = getString(SharedPrefsLoginWriter.passwordKey, "")
                        val baseUrl = getString(SharedPrefsLoginWriter.baseUrlKey, "http://smarthomeproject.mybluemix.net/api/")
                        stateMachine.dispatch(StartLoginAction(user, password, baseUrl))
                    })
                }

    }

    private fun sharedPreferences() = context.getSharedPreferences(SharedPrefsLoginWriter.sharedPrefsName, Context.MODE_PRIVATE)

}