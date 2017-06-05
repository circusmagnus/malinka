package pl.wojtach.malinka.ui.login_screen

import android.app.Activity
import android.databinding.DataBindingUtil
import android.os.Bundle
import pl.wojtach.malinka.R
import pl.wojtach.malinka.Starter
import pl.wojtach.malinka.databinding.ActivityLoginBinding
import pl.wojtach.malinka.networking.SensorDataFetcher
import pl.wojtach.malinka.statemachine.StateMachine
import pl.wojtach.malinka.statemachine.states.State



class LoginActivity : Activity() {

    private lateinit var stateMachine: StateMachine<State>
    private lateinit var starter: Starter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupStateMachine(savedInstanceState)
        setupDataBinding()
        setupComponentStarter()
    }


    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putParcelable(State.BUNDLE_KEY, stateMachine.getState())
        super.onSaveInstanceState(outState)
    }

    private fun setupDataBinding() {
        val view = DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
        view.model = LoginViewModel(stateMachine)
        view.dispatcher = LoginActionDispatcher(stateMachine, SensorDataFetcher.withRetrofit())
    }

    private fun setupStateMachine(savedInstanceState: Bundle?) {
        val state = savedInstanceState?.getParcelable<State>(State.BUNDLE_KEY)
                ?: intent.getParcelableExtra(State.BUNDLE_KEY)
                ?: throw AssertionError("No state acquired")
        stateMachine = StateMachine.getInstance(state)
    }

    private fun setupComponentStarter() {
        starter = LoginActivityStarter(context = this, stateMachine = stateMachine)
    }
}
