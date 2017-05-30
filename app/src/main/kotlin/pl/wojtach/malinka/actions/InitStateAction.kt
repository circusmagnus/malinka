package pl.wojtach.malinka.actions

import pl.wojtach.malinka.state.State
import pl.wojtach.malinka.state.createInitialState

class InitStateAction : Action<State> {
    override fun transformState(oldState: State): State = createInitialState()

}