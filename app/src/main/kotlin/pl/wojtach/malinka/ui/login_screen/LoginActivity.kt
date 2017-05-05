package pl.wojtach.malinka.ui.login_screen

import android.app.Activity
import android.databinding.DataBindingUtil
import android.os.Bundle
import pl.wojtach.malinka.R
import pl.wojtach.malinka.core.StateMachine
import pl.wojtach.malinka.databinding.ActivityLoginBinding
import pl.wojtach.malinka.state.State

class LoginActivity : Activity() {

    private lateinit var stateMachine: StateMachine<State>

    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setupStateMachine(savedInstanceState)
        setupDataBinding()
    }

    private fun setupDataBinding() {
        val view = DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
        view.model = LoginViewModel(stateMachine)
        view.dispatcher = LoginActionDispatcher(stateMachine)
    }

    private fun setupStateMachine(savedInstanceState: Bundle) {
        val state = savedInstanceState?.getParcelable<State>("STATE")
                ?: intent.getParcelableExtra("STATE")
                ?: throw AssertionError("No state acquired")
        stateMachine = StateMachine.getInstance(state)
    }


}
