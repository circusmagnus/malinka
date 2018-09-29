package pl.wojtach.malinka.networking.list

import pl.wojtach.malinka.networking.WsAlert
import pl.wojtach.malinka.networking.WsSensor
import pl.wojtach.malinka.networking.toEntitySensor
import pl.wojtach.malinka.statemachine.Action
import pl.wojtach.malinka.statemachine.entities.Sensor
import pl.wojtach.malinka.statemachine.states.PHASE
import pl.wojtach.malinka.statemachine.states.SensorState
import pl.wojtach.malinka.statemachine.states.State

internal class LoginSuccesAction(val sensors: List<WsSensor>, val alerts: List<WsAlert>) : Action<State> {
    override fun transformState(oldState: State): State {
        return oldState.copy(
            loginState = oldState.loginState.copy(phaseOfLogging = PHASE.FINISHED),
            sensorState = SensorState(
                phase = PHASE.FINISHED, sensors = addAlertsToSensors()
            )
        )
    }

    private fun addAlertsToSensors(): List<Sensor> = sensors
        .asSequence()
        .map { toEntitySensor(it) }
        .map { sensor -> addAlertsToSensor(sensor) }
        .toList()

    private fun addAlertsToSensor(sensor: Sensor): Sensor = alerts
        .filter { alert -> doesBelongToSensor(sensor, alert) }
        .let { alerts -> sensor.copy(valueChanges = getDatesAndSort(alerts)) }

    private fun getDatesAndSort(alerts: List<WsAlert>) =
        alerts.asSequence().map { it.date }.sortedByDescending { it }.toList()

//        sensors
//        .convertToEntity()
//        .map { sensor ->
//            alerts.filter { alert -> doesBelongToSensor(sensor, alert) }
//                .let { alert ->
//                    sensor.copy(valueChanges = alert.asSequence().map { it.date }.sortedByDescending { it }.toList())
//                }
//        }

    private fun doesBelongToSensor(sensor: Sensor, alert: WsAlert) =
        sensor.mac == alert.mac && sensor.type == 2
}

