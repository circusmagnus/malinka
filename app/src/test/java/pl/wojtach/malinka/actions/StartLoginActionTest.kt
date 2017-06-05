package pl.wojtach.malinka.actions

import org.amshove.kluent.shouldEqual
import org.junit.Test
import pl.wojtach.malinka.statemachine.StateMachine
import pl.wojtach.malinka.statemachine.createInitialState
import pl.wojtach.malinka.statemachine.states.LoginState
import pl.wojtach.malinka.statemachine.states.PHASE


/**
 * Created by lukaszwojtach on 05.05.2017.
 */
class StartLoginActionTest {

    //TODO install spek from: https://medium.com/@jozemberi/developing-with-kotlin-and-testing-with-spek-d69a94857d
    @Test
    fun transformState() {
        //given
        val oldState = createInitialState()
        val stateMachine = StateMachine.getInstance(createInitialState())
        val user = "abc"
        val password = "123"

        //when
        stateMachine.dispatch(StartLoginAction(user, password))
        val resultState = stateMachine.getState()

        //then
        resultState shouldEqual createInitialState().copy(loginState = LoginState(
                phaseOfLogging = PHASE.IN_PROGRESS,
                currentUser = user,
                currentPassword = password
        ))
    }

}