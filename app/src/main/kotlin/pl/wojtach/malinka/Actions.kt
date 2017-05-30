package pl.wojtach.malinka

import pl.wojtach.malinka.entities.Sensor
import pl.wojtach.malinka.state.*


/**
 * Created by lukaszwojtach on 17.04.2017.
 */

interface Action<STATE> {
    fun transformState(oldState: STATE): STATE
}

class InitStateAction : Action<State> {
    override fun transformState(oldState: State): State = createInitialState()

}

class StartLoginAction(val user: String, val password: String) : Action<State> {
    override fun transformState(oldState: State): State {
        return oldState.copy(loginState = LoginState(
                phaseOfLogging = PHASE.IN_PROGRESS,
                currentUser = user,
                currentPassword = password
        ))
    }
}

class LoginSuccesAction(val sensors: List<Sensor>) : Action<State> {
    override fun transformState(oldState: State): State {
        return oldState.copy(
                loginState = oldState.loginState.copy(phaseOfLogging = PHASE.FINISHED),
                sensorState = SensorState(phase = PHASE.FINISHED, sensors = sensors)
        )
    }
}

class LoginErrorAction : Action<State> {
    override fun transformState(oldState: State): State {
        return oldState.copy(
                loginState = oldState.loginState.copy(phaseOfLogging = PHASE.FAILED
                )
        )
    }
}
