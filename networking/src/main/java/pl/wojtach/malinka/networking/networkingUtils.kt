package pl.wojtach.malinka.networking

import pl.wojtach.malinka.statemachine.StateMachine
import pl.wojtach.malinka.statemachine.entities.Sensor
import pl.wojtach.malinka.statemachine.states.State

/**
 * Created by lukaszwojtach on 08.06.2017.
 */
internal fun StateMachine<State>.getPassword() = getState().loginState.currentPassword

internal fun StateMachine<State>.getUser() = getState().loginState.currentUser

internal fun StateMachine<State>.getBaseUrl() = getState().loginState.currentBaseUrl

fun List<WsSensor>.convertToEntity() = this.map {
    Sensor(
            mac = it.mac,
            name = it.name,
            type = it.type,
            isActive = it.isActive,
            lastDate = it.lastDate,
            lastValue = it.lastValue,
            hasError = false,
            shouldSync = false)
}