package pl.wojtach.malinka.statemachine

import pl.wojtach.malinka.statemachine.states.State

/**
 * Created by Lukasz on 05.07.2017.
 */
interface StateMachine2<STATE> {
    fun dispatch(action: Action<STATE>)
    fun getState(): STATE
    fun subscribe(consumer: Consumer<STATE>)
}

internal class StateMachineImpl2(initialState: State) : StateMachine2<State> {


    override fun dispatch(action: Action<State>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getState(): State {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun subscribe(consumer: Consumer<State>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}