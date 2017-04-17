package pl.wojtach.malinka.core

import pl.wojtach.malinka.state.State


/**
 * Created by lukaszwojtach on 17.04.2017.
 */

interface Action {
    fun newState(oldState: State): State
}

class InitStateAction : Action {
    override fun newState(oldState: State): State = createInitialState()

}
