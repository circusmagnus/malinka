package pl.wojtach.malinka.networking.toggle

import pl.wojtach.malinka.statemachine.Action
import pl.wojtach.malinka.statemachine.entities.Sensor
import pl.wojtach.malinka.statemachine.states.State

/**
 * Created by lukaszwojtach on 04.07.2017.
 */
internal class SetStatusErrorAction(val sensor: Sensor) : Action<State> {
    override fun transformState(oldState: State): State = oldState.copy(
            sensorState = oldState.sensorState.copy(
                    sensors = oldState.sensorState.sensors.map {
                        it.takeIf { it.mac == sensor.mac }
                                ?.copy(hasError = true)
                                ?: it
                    }
            )
    )


}