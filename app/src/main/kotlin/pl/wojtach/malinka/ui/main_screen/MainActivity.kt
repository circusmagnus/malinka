package pl.wojtach.malinka.ui.main_screen

import android.app.Activity
import android.databinding.DataBindingUtil
import android.os.Bundle
import pl.wojtach.malinka.R
import pl.wojtach.malinka.databinding.ActivityMainBinding
import pl.wojtach.malinka.networking.sensor.list.SensorDataFetcher
import pl.wojtach.malinka.networking.sensor.toggle.RemoteSensorToggler
import pl.wojtach.malinka.statemachine.StateMachine
import pl.wojtach.malinka.statemachine.states.State
import pl.wojtach.malinka.ui.setupStateMachine


class MainActivity : Activity() {


    lateinit var stateMachine: StateMachine<State>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        stateMachine = setupStateMachine(savedBundle = savedInstanceState, intent = intent)
        setupDataBinding()
        setupNetworking()
        setupComponentStarter()
    }

    private fun setupComponentStarter() {
        MainActivityStarter(context = this, stateMachine = stateMachine)
    }

    private fun setupNetworking() {
        SensorDataFetcher.withRetrofit(stateMachine)
        RemoteSensorToggler.withRetrofit(stateMachine)
    }

    private fun setupDataBinding() {
        val view = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        view.mainModel = MainActivityViewModel(stateMachine)
        view.sensorListModel = SensorListViewModel(stateMachine, this)
    }
}

