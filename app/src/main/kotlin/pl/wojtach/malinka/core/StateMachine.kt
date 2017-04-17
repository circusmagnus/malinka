package pl.wojtach.malinka.core

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import pl.wojtach.malinka.state.State

/**
 * Created by lukaszwojtach on 17.04.2017.
 */
interface StateMachine<STATE> {
    fun dispatch(action: Action)
    fun getState(): STATE
    fun getObservable(): Observable<STATE>
}

class StateMachineImpl : StateMachine<State> {

    var currentState: State = createInitialState()

    var publisher: Subject<State> = PublishSubject.create()

    override fun dispatch(action: Action) {
        publisher.onNext(createNewState(action))
    }

    private fun createNewState(action: Action): State {
        currentState = action.newState(currentState)
        return currentState
    }

    override fun getState(): State = currentState

    override fun getObservable(): Observable<State> = publisher
}