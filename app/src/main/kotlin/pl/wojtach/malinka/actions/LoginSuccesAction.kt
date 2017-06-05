package pl.wojtach.malinka.actions

import pl.wojtach.malinka.statemachine.Action
import pl.wojtach.malinka.statemachine.entities.Sensor
import pl.wojtach.malinka.statemachine.states.PHASE
import pl.wojtach.malinka.statemachine.states.SensorState
import pl.wojtach.malinka.statemachine.states.State


class LoginSuccesAction(val sensors: List<Sensor>) : Action<State> {
    override fun transformState(oldState: State): State {
        return oldState.copy(
                loginState = oldState.loginState.copy(phaseOfLogging = PHASE.FINISHED),
                sensorState = SensorState(phase = PHASE.FINISHED, sensors = sensors)
        )
    }
}

