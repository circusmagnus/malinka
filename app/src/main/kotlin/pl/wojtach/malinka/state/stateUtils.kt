package pl.wojtach.malinka.state

/**
 * Created by lukaszwojtach on 17.04.2017.
 */

fun createInitialState() = State(
        errorState = null,
        loginState = LoginState(
                phaseOfLogging = PHASE.NOT_STARTED,
                currentPassword = "",
                currentUser = ""
        ),
        sensorState = SensorState(
                phase = PHASE.NOT_STARTED,
                sensors = emptyList()
        )
)
