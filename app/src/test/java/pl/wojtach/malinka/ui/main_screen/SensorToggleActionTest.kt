package pl.wojtach.malinka.ui.main_screen

import org.amshove.kluent.shouldEqual
import org.junit.Before
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

    lateinit var baseSensor: Sensor
    lateinit var baseState: State

    @Before
    fun setup() {
        baseSensor = Sensor(
                mac = "",
                name = "",
                type = 1,
                isActive = true,
                hasError = false,
                lastDate = "",
                lastValue = "",
                shouldSync = false
        )
        baseState = State(
                errorState = null,
                loginState = LoginState(PHASE.FINISHED, "", ""),
                sensorState = SensorState(PHASE.FINISHED, listOf(baseSensor))
        )
    }

    @Test
    fun toggleSingleExistingSensor() {
        //when
        val result = performActionOnBaseSensor(baseState)

        //then
        result.getSensors().first() shouldEqual baseSensor.copy(isActive = false, shouldSync = true)
    }

    @Test
    fun toggleNonExistingSensorEmptyList() {
        //given
        val oldState = baseState.copy(sensorState = baseState.sensorState.copy(sensors = emptyList()))

        //when
        val result = performActionOnBaseSensor(oldState)

        //then
        result.getSensors() shouldEqual emptyList<Sensor>()
    }

    @Test
    fun toggleNonExistingSensorNonEmptyList() {
        //given
        val otherSensor1 = baseSensor.copy(mac = "other1")
        val otherSensor2 = baseSensor.copy(mac = "other2")
        val list = listOf(otherSensor1, otherSensor2)
        val oldState = baseState.copy(sensorState = baseState.sensorState.copy(sensors = list))

        //when
        val result = performActionOnBaseSensor(oldState)

        //then
        result.getSensors() shouldEqual list
    }


    @Test
    fun toggleSensorInList() {
        //given
        val otherSensor1 = baseSensor.copy(mac = "other1")
        val otherSensor2 = baseSensor.copy(mac = "other2")
        val list = listOf(otherSensor1, baseSensor, otherSensor2)
        val oldState = baseState.copy(sensorState = baseState.sensorState.copy(sensors = list))

        //when
        val result = performActionOnBaseSensor(oldState = oldState)

        //then
        result.getSensors() shouldEqual listOf(otherSensor1, baseSensor.copy(isActive = false, shouldSync = true), otherSensor2)
    }

    private fun performActionOnBaseSensor(oldState: State) =
            SensorToggleAction(baseSensor.mac, baseSensor.type)
                    .transformState(oldState)

    private fun State.getSensors() = this.sensorState.sensors

}