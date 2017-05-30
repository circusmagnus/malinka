package pl.wojtach.malinka.ui.login_screen

import android.content.Context
import android.content.Intent
import pl.wojtach.malinka.Starter
import pl.wojtach.malinka.state.PHASE
import pl.wojtach.malinka.state.State
import pl.wojtach.malinka.state.StateMachine
import pl.wojtach.malinka.ui.main_screen.MainActivity

/**
 * Created by Lukasz on 29.05.2017.
 */
class LoginActivityStarter(val context: Context, val stateMachine: StateMachine<State>) : Starter {

    init {
        stateMachine.getPublisher().subscribe { startComponent(it) }
    }

    override fun startComponent(state: State) {
        if (state.loginState.phaseOfLogging == PHASE.FINISHED) context.startActivity(createMainActivityIntent())
    }


    private fun createMainActivityIntent() = Intent(context, MainActivity::class.java)
            .putExtra(State.BUNDLE_KEY, stateMachine.getState())
}