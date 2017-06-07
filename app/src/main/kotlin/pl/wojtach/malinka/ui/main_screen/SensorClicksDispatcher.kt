package pl.wojtach.malinka.ui.main_screen

import android.widget.Checkable
import pl.wojtach.malinka.statemachine.Action
import pl.wojtach.malinka.statemachine.StateMachine
import pl.wojtach.malinka.statemachine.entities.SWITCH_TO
import pl.wojtach.malinka.statemachine.states.State

interface SensorClicksDispatcher {
    fun toggle(switch: Checkable)
}

class SensorClicksDispatcherImpl(val stateMachine: StateMachine<State>) : SensorClicksDispatcher {
    override fun toggle(switch: Checkable, id: String) {
        if (switch.isChecked) stateMachine.dispatch(EnableSensorAction(id))
        else
    }

}

class EnableSensorAction(val id: String) : Action<State> {
    override fun transformState(oldState: State): State {
        val newSensorState = oldState
                .sensorState
                .sensors
                .first { it.mac == id }
                .copy(switchTo = SWITCH_TO.ENABLED)

        return oldState.copy(sensorState = oldState.sensorState.copy(sensors = oldState.sensorState.sensors))

    }

    class EnableSensorAction(val id: String) : Action<State> {
        override fun transformState(oldState: State): State {
            val newSensorState = oldState
                    .sensorState
                    .sensors
                    .first { it.mac == id }
                    .copy(switchTo = SWITCH_TO.ENABLED)

        }

    }
