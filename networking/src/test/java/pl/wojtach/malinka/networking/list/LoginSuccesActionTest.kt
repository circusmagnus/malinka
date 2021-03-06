package pl.wojtach.malinka.networking.list

import org.amshove.kluent.shouldEqual
import org.junit.Test
import pl.wojtach.malinka.networking.WsAlert
import pl.wojtach.malinka.networking.WsSensor
import pl.wojtach.malinka.networking.convertToEntity
import pl.wojtach.malinka.statemachine.createInitialState
import pl.wojtach.malinka.statemachine.states.PHASE


/**
 * Created by Lukasz on 16.12.2017.
 */
class LoginSuccesActionTest {

    val stapleSensor = WsSensor(
            mac = "1",
            name = "1",
            type = 1,
            isActive = true,
            lastValue = "lastValue",
            lastDate = "16-12-2017"
    )

    val sensors = listOf(
            stapleSensor,
            stapleSensor.copy(mac = "2", name = "2")
    )

    val stapleAlert = WsAlert(
            mac = "2",
            type = 1,
            date = "16-12-2017"
    )

    val alerts = listOf(stapleAlert, stapleAlert.copy(mac = "3"))


    @Test
    fun transformState_correctlyTranformsInitialState_whenNoAlerts() {
        val loginSuccesAction = LoginSuccesAction(sensors = sensors, alerts = emptyList())
        val result = loginSuccesAction.transformState(createInitialState())

        result shouldEqual createInitialState().copy(
                loginState = createInitialState().loginState.copy(phaseOfLogging = PHASE.FINISHED),
                sensorState = createInitialState().sensorState.copy(
                        phase = PHASE.FINISHED, sensors = sensors.convertToEntity())
        )

    }

    @Test
    fun transformState_correctlyTranformsInitialState_whenSomeAlertsMatchSensors() {
        val loginSuccesAction = LoginSuccesAction(sensors = sensors, alerts = alerts)
        val result = loginSuccesAction.transformState(createInitialState())

        result shouldEqual createInitialState().copy(
                loginState = createInitialState().loginState.copy(phaseOfLogging = PHASE.FINISHED),
                sensorState = createInitialState().sensorState.copy(
                        phase = PHASE.FINISHED,
                        sensors = sensors.convertToEntity().map { sensor ->
                            alerts.filter { alert -> sensor.mac == alert.mac && sensor.type == alert.type }
                                    .let { sensor.copy(valueChanges = it.map { it.date }) }
                        }

                )
        )

    }

}