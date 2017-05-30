package pl.wojtach.malinka.actions

import pl.wojtach.malinka.entities.Sensor
import pl.wojtach.malinka.state.PHASE
import pl.wojtach.malinka.state.SensorState
import pl.wojtach.malinka.state.State

class LoginSuccesAction(val sensors: List<Sensor>) : Action<State> {
    override fun transformState(oldState: State): State {
        return oldState.copy(
                loginState = oldState.loginState.copy(phaseOfLogging = PHASE.FINISHED),
                sensorState = SensorState(phase = PHASE.FINISHED, sensors = sensors)
        )
    }
}