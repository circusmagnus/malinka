package pl.wojtach.malinka.networking

import pl.wojtach.malinka.statemachine.entities.Sensor
import pl.wojtach.malinka.statemachine.states.PHASE
import pl.wojtach.malinka.statemachine.states.State


internal class LoginSuccesAction(val sensors: List<Sensor>) : pl.wojtach.malinka.statemachine.Action<State> {
    override fun transformState(oldState: State): State {
        return oldState.copy(
                loginState = oldState.loginState.copy(phaseOfLogging = PHASE.FINISHED),
                sensorState = pl.wojtach.malinka.statemachine.states.SensorState(phase = PHASE.FINISHED, sensors = sensors)
        )
    }
}

