package pl.wojtach.malinka.ui.main_screen

import android.content.Context
import android.content.Intent
import io.reactivex.android.schedulers.AndroidSchedulers
import pl.wojtach.malinka.Starter
import pl.wojtach.malinka.statemachine.StateMachine
import pl.wojtach.malinka.statemachine.states.PHASE
import pl.wojtach.malinka.statemachine.states.State
import pl.wojtach.malinka.ui.login.LoginActivity

/**
 * Created by lukaszwojtach on 08.06.2017.
 */
class MainActivityStarter(val context: Context, val stateMachine: StateMachine<State>) : Starter {
    init {
        startComponent(stateMachine.getState())
        stateMachine.getPublisher().observeOn(AndroidSchedulers.mainThread()).subscribe { startComponent(it) }
    }

    override fun startComponent(state: State) {
        if (state.loginState.phaseOfLogging == PHASE.FAILED) context.startActivity(createLoginActivityIntent())
    }

    private fun createLoginActivityIntent() = Intent(context, LoginActivity::class.java)
            .putExtra(State.BUNDLE_KEY, stateMachine.getState())
}