package pl.wojtach.malinka.core

import pl.wojtach.malinka.state.State


/**
 * Created by lukaszwojtach on 17.04.2017.
 */

interface Action<STATE> {
    fun transformState(oldState: STATE): STATE
}

class InitStateAction : Action<State> {
    override fun transformState(oldState: State): State = createInitialState()

}

class LoginAction(val user: String, val password: String) : Action<State> {
    override fun transformState(oldState: State): State {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
