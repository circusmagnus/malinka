package pl.wojtach.malinka.networking

import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import pl.wojtach.malinka.networking.list.LoginErrorAction

import pl.wojtach.malinka.statemachine.StateMachine
import pl.wojtach.malinka.statemachine.createInitialState
import pl.wojtach.malinka.statemachine.states.State

/**
 * Created by lukaszwojtach on 04.06.2017.
 */
class LoginErrorActionTest {
    lateinit var stateMachine: StateMachine<State>
    val invalidLoginMessage = "invalid login"
    val secondErrorMessage = "server failure"

    @Before
    fun setUp() {
        stateMachine = StateMachine.getInstance(createInitialState())
    }

    @Test
    fun firstErrorSinceBeginning() {
        stateMachine.dispatch(LoginErrorAction(invalidLoginMessage))

        val result = stateMachine.getState()

        result.errorState?.errorMessages shouldEqual setOf(invalidLoginMessage)
    }

    @Test
    fun secondErrorSinceBeginning() {
        stateMachine.dispatch(LoginErrorAction(invalidLoginMessage))
        stateMachine.dispatch(LoginErrorAction(secondErrorMessage))

        val result = stateMachine.getState()

        result.errorState?.errorMessages shouldEqual setOf(invalidLoginMessage, secondErrorMessage)
    }

    @Test
    fun thirdErrorSameAsFirst() {
        stateMachine.dispatch(LoginErrorAction(invalidLoginMessage))
        stateMachine.dispatch(LoginErrorAction(secondErrorMessage))
        stateMachine.dispatch(LoginErrorAction(invalidLoginMessage))

        val result = stateMachine.getState()

        result.errorState?.errorMessages shouldEqual setOf(invalidLoginMessage, secondErrorMessage)
    }

}