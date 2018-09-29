package pl.wojtach.malinka.networking

import pl.wojtach.malinka.statemachine.StateMachine
import pl.wojtach.malinka.statemachine.entities.Sensor
import pl.wojtach.malinka.statemachine.states.State
import java.io.IOException

/**
 * Created by lukaszwojtach on 08.06.2017.
 */
internal fun StateMachine<State>.getPassword() = getState().loginState.currentPassword

internal fun StateMachine<State>.getUser() = getState().loginState.currentUser

internal fun StateMachine<State>.getBaseUrl() = getState().loginState.currentBaseUrl

fun List<WsSensor>.convertToEntity() = this.map {
    Sensor(
            mac = it.mac ?: throw IOException("Critical field is null: Sensor.mac"),
            name = it.name ?: "",
            type = it.type ?: throw IOException("Critical field is null: Sensor.type"),
            isActive = it.isActive ?: throw IOException("Critical field is null: Sensor.isActive"),
            lastDate = it.lastDate ?: "",
            lastValue = it.lastValue ?: "",
            hasError = false,
            shouldSync = false,
            valueChanges = kotlin.collections.emptyList())
}