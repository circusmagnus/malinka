//package pl.wojtach.malinka.actions
//
//import org.junit.Assert.assertEquals
//import org.junit.Ignore
//import org.junit.Test
//import pl.wojtach.malinka.actions.InitStateAction
//import pl.wojtach.malinka.state.*
//
///**
// * Created by lukaszwojtach on 17.04.2017.
// */
//
//class InitStateActionTest {
//
//    @org.junit.Test
//    @Ignore
//    fun newState() {
//        //given
//        val expectedState = State(
//                errorState = null,
//                loginState = pl.wojtach.malinka.state.LoginState(
//                        phaseOfLogging = PHASE.NOT_STARTED,
//                        currentUser = "",
//                        currentPassword = ""
//                ),
//                sensorState = SensorState(phase = PHASE.NOT_STARTED, sensors = emptyList())
//        )
//
//        val oldState = pl.wojtach.malinka.state.createInitialState().copy(errorState = ErrorState("test error"))
//
//        //when
//        val state = pl.wojtach.malinka.actions.InitStateAction().transformState(oldState)
//
//        //then
//        assertEquals(state, expectedState)
//    }
//
//}