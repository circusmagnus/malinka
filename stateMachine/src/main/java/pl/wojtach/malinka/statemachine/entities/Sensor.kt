package pl.wojtach.malinka.statemachine.entities

/**
 * Created by Lukasz on 07.01.2017.
 */
data class Sensor(
        val mac: String,
        val name: String,
        val type: Int,
        val isActive: Boolean,
        val lastValue: String,
        val lastDate: String,
        val hasError: Boolean,
        val switchTo: SWITCH_TO
) {
}

enum class SWITCH_TO {
    ENABLED, DISABLED, NO_CHANGE
}
