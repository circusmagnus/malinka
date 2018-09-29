package pl.wojtach.malinka.networking.list

import org.amshove.kluent.shouldEqual
import org.junit.Test
import pl.wojtach.malinka.networking.WsAlert
import pl.wojtach.malinka.networking.WsSensor
import pl.wojtach.malinka.networking.convertToEntity
import pl.wojtach.malinka.statemachine.createInitialState
import pl.wojtach.malinka.statemachine.entities.Sensor
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

    val alerts = listOf(stapleAlert, stapleAlert.copy(mac = "3", date = "15-12-2017"))

    @Test
    fun transformState_correctlyTranformsInitialState_whenNoAlerts() {
        val loginSuccesAction = LoginSuccesAction(sensors = sensors, alerts = emptyList())
        val result = loginSuccesAction.transformState(createInitialState())

        result shouldEqual createInitialState().copy(
            loginState = createInitialState().loginState.copy(phaseOfLogging = PHASE.FINISHED),
            sensorState = createInitialState().sensorState.copy(
                phase = PHASE.FINISHED, sensors = sensors.convertToEntity()
            )
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
                sensors = listOf(
                    Sensor(
                        mac = "1",
                        name = "1",
                        type = 1,
                        isActive = true,
                        lastValue = "lastValue",
                        lastDate = "16-12-2017",
                        shouldSync = false,
                        hasError = false,
                        valueChanges = listOf()
                    ),
                    Sensor(
                        mac = "2",
                        name = "2",
                        type = 1,
                        isActive = true,
                        lastValue = "lastValue",
                        lastDate = "16-12-2017",
                        shouldSync = false,
                        hasError = false,
                        valueChanges = listOf("16-12-2017")
                    )
                )

            )
        )
    }

    @Test
    fun `TransformState sorts alerts belonging to sensor by date, descending`() {
        val wsSensor = stapleSensor
        val wsAlerts = listOf(
            WsAlert(
                mac = stapleSensor.mac,
                type = stapleSensor.type,
                date = "2-12-2009"
            ),
            WsAlert(
                mac = stapleSensor.mac,
                type = stapleSensor.type,
                date = "1-12-2009"
            ),
            WsAlert(
                mac = stapleSensor.mac,
                type = stapleSensor.type,
                date = "3-12-2009"
            )

        )

        val loginSuccesAction = LoginSuccesAction(sensors = listOf(wsSensor), alerts = wsAlerts)
        val result = loginSuccesAction.transformState(createInitialState())
            .sensorState.sensors.first().valueChanges

        result shouldEqual listOf(
            "3-12-2009",
            "2-12-2009",
            "1-12-2009"
        )
    }
}