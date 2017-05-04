package pl.wojtach.malinka.core

import android.os.Parcel
import android.os.Parcelable
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import pl.wojtach.malinka.state.State

/**
 * Created by lukaszwojtach on 17.04.2017.
 */
interface StateMachine<STATE> : Parcelable {
    fun dispatch(action: Action<STATE>)
    fun getState(): STATE
    fun getPublisher(): Observable<STATE>
}

class StateMachineImpl(initialState: State) : StateMachine<State> {


    private val publisher: Subject<State> = PublishSubject.create()

    private var currentState: State = initialState
        set(value) {
            field = value
            publisher.onNext(field)
        }


    override fun dispatch(action: Action<State>) {
        currentState = action.newState(currentState)
    }

    override fun getState(): State = currentState

    override fun getPublisher(): Observable<State> = publisher

    override fun describeContents(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}