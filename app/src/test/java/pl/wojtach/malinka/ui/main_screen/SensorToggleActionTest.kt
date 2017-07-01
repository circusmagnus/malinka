package pl.wojtach.malinka.ui.main_screen

import org.amshove.kluent.shouldEqual
import org.junit.Test
import pl.wojtach.malinka.statemachine.entities.Sensor
import pl.wojtach.malinka.statemachine.states.LoginState
import pl.wojtach.malinka.statemachine.states.PHASE
import pl.wojtach.malinka.statemachine.states.SensorState
import pl.wojtach.malinka.statemachine.states.State

/**
 * Created by lukaszwojtach on 02.07.2017.
 */
class SensorToggleActionTest {
    @Test
    fun transformState() {
        //given
        val toggledSensor = Sensor(
                mac = "",
                name = "",
                type = 1,
                isActive = true,
                hasError = false,
                lastDate = "",
                lastValue = "",
                shouldSync = false
        )

        val otherSensor = toggledSensor.copy(mac = "other")
        val sensors = listOf(toggledSensor, otherSensor)
        val oldState = State(
                errorState = null,
                loginState = LoginState(PHASE.FINISHED, "", ""),
                sensorState = SensorState(PHASE.FINISHED, sensors)
        )

        //when
        val resultState = SensorToggleAction(toggledSensor.mac, type = toggledSensor.type).transformState(oldState)

        //then
        val testedSensor = resultState.sensorState.sensors
                .firstOrNull { it.mac == toggledSensor.mac && it.type == toggledSensor.type }

        testedSensor shouldEqual toggledSensor.copy(isActive = false, shouldSync = true)
    }

}