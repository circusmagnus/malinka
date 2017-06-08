package pl.wojtach.malinka.ui.main_screen

import android.widget.Checkable
import pl.wojtach.malinka.statemachine.Action
import pl.wojtach.malinka.statemachine.StateMachine
import pl.wojtach.malinka.statemachine.entities.SWITCH_TO
import pl.wojtach.malinka.statemachine.states.State

interface SensorClicksDispatcher {
    fun toggle(switch: Checkable, id: String)

    companion object {
        fun getInstance(stateMachine: StateMachine<State>) = SensorClicksDispatcherImpl(stateMachine)
    }
}

class SensorClicksDispatcherImpl(val stateMachine: StateMachine<State>) : SensorClicksDispatcher {
    override fun toggle(switch: Checkable, id: String) {
        stateMachine.dispatch(SensorToggleAction(id, switch.isChecked))
    }

}

class SensorToggleAction(val id: String, val isChecked: Boolean) : Action<State> {
    override fun transformState(oldState: State): State {

        return oldState.copy(
                sensorState = oldState.sensorState.copy(
                        sensors = oldState.sensorState.sensors
                                .map { it.takeIf { it.mac == id }?.copy(switchTo = if (isChecked) SWITCH_TO.ENABLED else SWITCH_TO.DISABLED) ?: it }

                )
        )
    }
    }
