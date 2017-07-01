package pl.wojtach.malinka.ui.main_screen

import pl.wojtach.malinka.statemachine.Action
import pl.wojtach.malinka.statemachine.StateMachine
import pl.wojtach.malinka.statemachine.states.State

/**
 * Created by lukaszwojtach on 08.06.2017.
 */
class MainScreenDispatcher(val stateMachine: StateMachine<State>) {

    fun toggleOn() {
        stateMachine.dispatch(ToggleOnAction())
    }
}

class ToggleOnAction : Action<State> {
    override fun transformState(oldState: State): State {
        return oldState.copy(sensorState =
        oldState.sensorState.copy(sensors =
        oldState.sensorState.sensors.map { it.copy(isActive = true) }))
    }

}
