package pl.wojtach.malinka.core

import org.junit.Assert.assertEquals
import org.junit.Test
import pl.wojtach.malinka.state.ErrorState
import pl.wojtach.malinka.state.LoginState
import pl.wojtach.malinka.state.PHASE
import pl.wojtach.malinka.state.State

/**
 * Created by lukaszwojtach on 17.04.2017.
 */

class InitStateActionTest {

    @Test
    fun newState() {
        //given
        val expectedState = State(
                errorState = null,
                loginState = LoginState(
                        phaseOfLogging = PHASE.NOT_STARTED,
                        currentUser = "",
                        currentPassword = ""
                )
        )

        val oldState = createInitialState().copy(errorState = ErrorState("test error"))

        //when
        val state = InitStateAction().newState(oldState)

        //then
        assertEquals(state, expectedState)
    }

}