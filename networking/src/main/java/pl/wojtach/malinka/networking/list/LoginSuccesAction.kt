package pl.wojtach.malinka.networking.list

import pl.wojtach.malinka.networking.WsSensor
import pl.wojtach.malinka.statemachine.Action
import pl.wojtach.malinka.statemachine.entities.Sensor
import pl.wojtach.malinka.statemachine.states.PHASE
import pl.wojtach.malinka.statemachine.states.SensorState
import pl.wojtach.malinka.statemachine.states.State


internal class LoginSuccesAction(val sensors: List<WsSensor>) : Action<State> {
    override fun transformState(oldState: State): State {
        return oldState.copy(
                loginState = oldState.loginState.copy(phaseOfLogging = PHASE.FINISHED),
                sensorState = SensorState(phase = PHASE.FINISHED, sensors = sensors.convertToEntity())
        )
    }

    fun List<WsSensor>.convertToEntity() = this.map {
        Sensor(
                mac = it.mac,
                name = it.name,
                type = it.type,
                isActive = it.isActive,
                lastDate = it.lastDate,
                lastValue = it.lastValue,
                hasError = false,
                shouldSync = false)
    }
}

