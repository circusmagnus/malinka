package pl.wojtach.malinka.ui.login.actions

import org.amshove.kluent.shouldEqual
import pl.wojtach.cache.StartLoginAction
import pl.wojtach.malinka.statemachine.createInitialState
import pl.wojtach.malinka.statemachine.states.LoginState
import pl.wojtach.malinka.statemachine.states.PHASE


/**
 * Created by lukaszwojtach on 05.05.2017.
 */
class StartLoginActionTest {

    //TODO install spek from: https://medium.com/@jozemberi/developing-with-kotlin-and-testing-with-spek-d69a94857d
    @org.junit.Test
    fun transformState() {
        //given
        val oldState = createInitialState()
        val user = "abc"
        val password = "123"

        //when
        val resultState = StartLoginAction(user, password).transformState(oldState)

        //then
        resultState shouldEqual createInitialState().copy(loginState = LoginState(
                phaseOfLogging = PHASE.IN_PROGRESS,
                currentUser = user,
                currentPassword = password
        ))
    }

}