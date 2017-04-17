package pl.wojtach.malinka.core

import pl.wojtach.malinka.state.LoginState
import pl.wojtach.malinka.state.PHASE
import pl.wojtach.malinka.state.State

/**
 * Created by lukaszwojtach on 17.04.2017.
 */

fun createInitialState() = State(
        errorState = null,
        loginState = LoginState(
                phaseOfLogging = PHASE.NOT_STARTED,
                currentPassword = "",
                currentUser = ""
        ))
