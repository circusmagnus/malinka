package pl.wojtach.malinka.actions

import org.amshove.kluent.shouldContain
import org.amshove.kluent.shouldEqualTo
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import pl.wojtach.malinka.state.ErrorState
import pl.wojtach.malinka.state.StateMachine
import pl.wojtach.malinka.state.createInitialState

/**
 * Created by lukaszwojtach on 31.05.2017.
 */

@RunWith(JUnitPlatform::class)
class LoginErrorActionTest : Spek({
    given("No previous errors", {
        val stateMachine = StateMachine.getInstance(createInitialState())
        on("action signalling an login error", {
            val error = "failed to log in"
            stateMachine.dispatch(LoginErrorAction(error))
            it("should create ErrorState with single entry containing its message", {
                stateMachine.getState().errorState?.errorMessages ?: emptySet<String>() shouldContain error
            })
        })
    })
    given("Previous error", {
        val previousError = "login error"
        val stateMachine = StateMachine
                .getInstance(createInitialState()
                        .copy(errorState = ErrorState(errorMessages = setOf(previousError))))
        on("action signalling the same error", {
            val nextError = "login error"
            stateMachine.dispatch(LoginErrorAction(nextError))
            it("should not add another error", {
                stateMachine.getState().errorState?.errorMessages?.size ?: 0 shouldEqualTo 3
            })
            it("should retain previous error", {
                stateMachine.getState().errorState?.errorMessages ?: emptySet<String>() shouldContain previousError
            })
        })
    })
})

