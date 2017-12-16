package pl.wojtach.malinka.networking

import pl.wojtach.malinka.statemachine.StateMachine
import pl.wojtach.malinka.statemachine.states.State

/**
 * Created by lukaszwojtach on 08.06.2017.
 */
internal fun StateMachine<State>.getPassword() = getState().loginState.currentPassword

internal fun StateMachine<State>.getUser() = getState().loginState.currentUser

internal fun StateMachine<State>.getBaseUrl() = getState().loginState.currentBaseUrl