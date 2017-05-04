package pl.wojtach.malinka.ui.login_screen

import android.app.Activity
import android.databinding.DataBindingUtil
import android.os.Bundle
import pl.wojtach.malinka.R
import pl.wojtach.malinka.core.StateMachine
import pl.wojtach.malinka.databinding.ActivityLoginBinding
import pl.wojtach.malinka.state.State

class LoginActivity : Activity() {

    lateinit var stateMachine: StateMachine<State>

    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        stateMachine = savedInstanceState?.getParcelable<StateMachine<State>>("STATE_MACHINE")
        val binding = DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
        binding.model = LoginViewModel()
    }
}
