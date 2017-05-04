package pl.wojtach.malinka.core

import io.reactivex.observers.TestObserver
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import pl.wojtach.malinka.state.State

/**
 * Created by lukaszwojtach on 17.04.2017.
 */
class StateMachineImplTest {

    val testAction = object : Action<State> {
        override fun transformState(oldState: State): State =
                oldState.createTestCase()

    }

    private fun State.createTestCase() = this.copy(loginState = loginState.copy(currentUser = "testowy"))

    @Test
    fun after_dispatch_action_can_get_correct_state() {
        //given
        val stateMachine: StateMachine<State> = StateMachineImpl(createInitialState())

        //when
        stateMachine.dispatch(testAction)

        //then
        assertThat(stateMachine.getState().loginState.currentUser).matches("testowy")
    }

    @Test
    fun after_dispatching_new_state_does_notify() {
        //given
        val stateMachine = StateMachineImpl(createInitialState())

        val testObserver = TestObserver.create<State>()

        //when
        stateMachine.getPublisher().subscribe(testObserver)
        stateMachine.dispatch(testAction)

        //then
        testObserver.assertValue { newState -> newState == createInitialState().createTestCase() }
    }
}