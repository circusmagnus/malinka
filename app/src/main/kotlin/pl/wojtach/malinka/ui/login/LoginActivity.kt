package pl.wojtach.malinka.ui.login

import android.app.Activity
import android.databinding.DataBindingUtil
import android.os.Bundle
import pl.wojtach.malinka.R
import pl.wojtach.malinka.Starter
import pl.wojtach.malinka.databinding.ActivityLoginBinding
import pl.wojtach.malinka.networking.SensorDataFetcher
import pl.wojtach.malinka.statemachine.StateMachine
import pl.wojtach.malinka.statemachine.states.State
import pl.wojtach.malinka.ui.setupStateMachine


class LoginActivity : Activity() {

    private lateinit var stateMachine: StateMachine<State>
    private lateinit var starter: Starter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        stateMachine = setupStateMachine(savedBundle = savedInstanceState, intent = intent)
        setupDataBinding()
        setupNetworking()
        setupComponentStarter()
    }


    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putParcelable(State.BUNDLE_KEY, stateMachine.getState())
        super.onSaveInstanceState(outState)
    }

    private fun setupDataBinding() {
        val view = DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
        view.model = LoginViewModel(stateMachine)
        view.dispatcher = LoginActionDispatcher(stateMachine)
    }

    private fun setupNetworking() {
        SensorDataFetcher.withRetrofit(stateMachine)
    }

    private fun setupComponentStarter() {
        starter = LoginActivityStarter(context = this, stateMachine = stateMachine)
    }
}
