package pl.wojtach.malinka.login.actions

import pl.wojtach.malinka.statemachine.states.PHASE
import pl.wojtach.malinka.statemachine.states.State


internal class LoginSuccesAction(val sensors: List<pl.wojtach.malinka.statemachine.entities.Sensor>) : pl.wojtach.malinka.statemachine.Action<State> {
    override fun transformState(oldState: pl.wojtach.malinka.statemachine.states.State): pl.wojtach.malinka.statemachine.states.State {
        return oldState.copy(
                loginState = oldState.loginState.copy(phaseOfLogging = pl.wojtach.malinka.statemachine.states.PHASE.FINISHED),
                sensorState = pl.wojtach.malinka.statemachine.states.SensorState(phase = PHASE.FINISHED, sensors = sensors)
        )
    }
}

