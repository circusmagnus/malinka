package pl.wojtach.malinka.statemachine

import pl.wojtach.malinka.statemachine.states.LoginState
import pl.wojtach.malinka.statemachine.states.PHASE
import pl.wojtach.malinka.statemachine.states.SensorState
import pl.wojtach.malinka.statemachine.states.State

/**
 * Created by lukaszwojtach on 17.04.2017.
 */

fun createInitialState() = State(
        errorState = null,
        loginState = LoginState(
                phaseOfLogging = PHASE.NOT_STARTED,
                currentPassword = "",
                currentUser = "",
                currentBaseUrl = "http://smarthomeproject.mybluemix.net/api/"
        ),
        sensorState = SensorState(
                phase = PHASE.NOT_STARTED,
                sensors = emptyList()
        )
)
