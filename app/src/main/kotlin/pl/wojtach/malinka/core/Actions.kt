package pl.wojtach.malinka.core

import pl.wojtach.malinka.state.State


/**
 * Created by lukaszwojtach on 17.04.2017.
 */

interface Action<STATE> {
    fun newState(oldState: STATE): STATE
}

class InitStateAction : Action<State> {
    override fun newState(oldState: State): State = createInitialState()

}
