package pl.wojtach.malinka.core

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import pl.wojtach.malinka.state.State

/**
 * Created by lukaszwojtach on 17.04.2017.
 */
interface StateMachine<STATE> {
    fun dispatch(action: Action<STATE>)
    fun getState(): STATE
    fun getPublisher(): Observable<STATE>

    companion object {
        fun getInstance(initialState: State) = StateMachineImpl(initialState)
    }
}

class StateMachineImpl(initialState: State) : StateMachine<State> {


    private val publisher: Subject<State> = PublishSubject.create()

    private var currentState: State = initialState
        set(value) {
            field = value
            publisher.onNext(field)
        }


    override fun dispatch(action: Action<State>) {
        currentState = action.transformState(currentState)
    }

    override fun getState(): State = currentState

    override fun getPublisher(): Observable<State> = publisher
}