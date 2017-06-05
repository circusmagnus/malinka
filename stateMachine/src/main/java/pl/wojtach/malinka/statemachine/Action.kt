package pl.wojtach.malinka.statemachine


/**
 * Created by lukaszwojtach on 17.04.2017.
 */

interface Action<STATE> {
    fun transformState(oldState: STATE): STATE
}

