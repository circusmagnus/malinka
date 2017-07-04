package pl.wojtach.malinka.ui.main_screen

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import io.reactivex.android.schedulers.AndroidSchedulers
import pl.wojtach.malinka.statemachine.StateMachine
import pl.wojtach.malinka.statemachine.states.State

/**
 * Created by Lukasz on 07.01.2017.
 */

class SensorViewModel(stateMachine: StateMachine<State>, val id: String, val type: Int) {


    val isActiveObservable = ObservableBoolean(false)
    val errorOccuredObservable = ObservableBoolean(false)

    val name = ObservableField<String>()
    val value = ObservableField<String>()
    val date = ObservableField<String>()

    init {
        render(stateMachine.getState())
        stateMachine.getPublisher().observeOn(AndroidSchedulers.mainThread()).subscribe { render(it) }
    }

    private fun render(state: State) {
        val sourceSensor = state.sensorState.sensors.first { it.mac == id && it.type == type }
        isActiveObservable.set(sourceSensor.isActive)
        errorOccuredObservable.set(sourceSensor.hasError)
        name.set(sourceSensor.name)
        value.set(sourceSensor.lastValue)
        date.set(sourceSensor.lastDate)
    }
}
