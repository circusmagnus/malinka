package pl.wojtach.malinka.ui.main_screen

import android.view.View
import android.widget.Checkable
import pl.wojtach.malinka.statemachine.Action
import pl.wojtach.malinka.statemachine.StateMachine
import pl.wojtach.malinka.statemachine.entities.SWITCH_TO
import pl.wojtach.malinka.statemachine.entities.Sensor
import pl.wojtach.malinka.statemachine.states.State

interface SensorClicksDispatcher {
    fun toggle(switch: View, id: String, type: Int)

    companion object {
        fun getInstance(stateMachine: StateMachine<State>) = SensorClicksDispatcherImpl(stateMachine)
    }
}

class SensorClicksDispatcherImpl(val stateMachine: StateMachine<State>) : SensorClicksDispatcher {
    override fun toggle(switch: View, id: String, type: Int) {
        stateMachine.dispatch(SensorToggleAction(id, type, (switch as Checkable).isChecked))
    }

}

class SensorToggleAction(val id: String, val type: Int, val toActive: Boolean) : Action<State> {
    override fun transformState(oldState: State): State {

        return oldState.copy(
                sensorState = oldState.sensorState.copy(
                        sensors = oldState.sensorState.sensors
                                .map { toSameOrNewStatus(it) }

                )
        )
    }

    private fun toSameOrNewStatus(sensor: Sensor) =
            sensor
                    .takeIf { it.mac == id && it.type == type }
                    ?.copy(switchTo = if (toActive) SWITCH_TO.ENABLED else SWITCH_TO.DISABLED)
                    ?: sensor
}
