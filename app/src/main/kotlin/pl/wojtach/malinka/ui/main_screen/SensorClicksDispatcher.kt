package pl.wojtach.malinka.ui.main_screen

import pl.wojtach.malinka.statemachine.Action
import pl.wojtach.malinka.statemachine.StateMachine
import pl.wojtach.malinka.statemachine.entities.Sensor
import pl.wojtach.malinka.statemachine.states.State

interface SensorClicksDispatcher {
    fun toggle(id: String, type: Int)

    companion object {
        fun getInstance(stateMachine: StateMachine<State>): SensorClicksDispatcher
                = SensorClicksDispatcherImpl(stateMachine)
    }
}

private class SensorClicksDispatcherImpl(val stateMachine: StateMachine<State>) : SensorClicksDispatcher {
    override fun toggle(id: String, type: Int) {
        stateMachine.dispatch(SensorToggleAction(id, type))
    }

}

internal class SensorToggleAction(val id: String, val type: Int) : Action<State> {
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
                    ?.copy(isActive = !sensor.isActive, shouldSync = true)
                    ?: sensor
}
