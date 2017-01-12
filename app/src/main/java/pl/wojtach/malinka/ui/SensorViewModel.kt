package pl.wojtach.malinka.ui

import android.view.View
import android.widget.Switch
import pl.wojtach.malinka.data.RetrofitProvider
import pl.wojtach.malinka.data.SensorRepositoryRetrofit
import pl.wojtach.malinka.logic.Sensor

/**
 * Created by Lukasz on 07.01.2017.
 */

class SensorViewModel(val sensor: Sensor) {

    fun getName(): String {
        return sensor.name
    }

    fun getValue(): String {
        return sensor.lastValue
    }

    fun getDate(): String {
        return sensor.lastDate
    }

    fun getStatus(): Boolean {
        return sensor.isActive
    }

    fun setNewStatus(switch: View) {

        sensor.isActive = (switch as? Switch)?.isChecked ?: return
        SensorRepositoryRetrofit(RetrofitProvider).setSensorStatus(sensor)
    }
}
