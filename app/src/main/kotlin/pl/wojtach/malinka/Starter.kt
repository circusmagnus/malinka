package pl.wojtach.malinka

import pl.wojtach.malinka.state.State

interface Starter {
    fun startComponent(state: State)
}