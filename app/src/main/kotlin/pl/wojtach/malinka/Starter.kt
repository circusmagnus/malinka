package pl.wojtach.malinka

import pl.wojtach.malinka.statemachine.states.State


interface Starter {
    fun startComponent(state: State)
}