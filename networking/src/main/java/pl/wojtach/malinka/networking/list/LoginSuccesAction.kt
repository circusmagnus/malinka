package pl.wojtach.malinka.networking.list

import pl.wojtach.malinka.networking.WsAlert
import pl.wojtach.malinka.networking.WsSensor
import pl.wojtach.malinka.networking.convertToEntity
import pl.wojtach.malinka.statemachine.Action
import pl.wojtach.malinka.statemachine.states.PHASE
import pl.wojtach.malinka.statemachine.states.SensorState
import pl.wojtach.malinka.statemachine.states.State


internal class LoginSuccesAction(val sensors: List<WsSensor>, val alerts: List<WsAlert>) : Action<State> {
    override fun transformState(oldState: State): State {
        return oldState.copy(
                loginState = oldState.loginState.copy(phaseOfLogging = PHASE.FINISHED),
                sensorState = SensorState(phase = PHASE.FINISHED, sensors = sensors.convertToEntity())
        )
    }
}

