package pl.wojtach.malinka.actions

import pl.wojtach.malinka.statemachine.Action
import pl.wojtach.malinka.statemachine.createInitialState
import pl.wojtach.malinka.statemachine.states.State



class InitStateAction : Action<State> {
    override fun transformState(oldState: State): State = createInitialState()

}