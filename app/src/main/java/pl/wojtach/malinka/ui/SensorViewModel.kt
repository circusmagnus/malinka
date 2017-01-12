package pl.wojtach.malinka.ui

import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.Switch
import pl.wojtach.malinka.data.RetrofitProvider
import pl.wojtach.malinka.data.SensorRepositoryRetrofit
import pl.wojtach.malinka.logic.Sensor
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by Lukasz on 07.01.2017.
 */

class SensorViewModel(val sensor: Sensor) {

    val TAG = SensorViewModel::class.java.simpleName

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

    fun setNewStatus(view: View) {
        val switch = view as Switch
        val newSensorStatus = sensor.copy(isActive = switch.isChecked)
        SensorRepositoryRetrofit(RetrofitProvider)
                .setSensorStatus(newSensorStatus)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { success ->
                            sensor.apply { isActive = newSensorStatus.isActive }
                            switch.setChecked(sensor.isActive)
                        },

                        { error ->
                            switch.setBackgroundColor(Color.RED)
                            switch.setChecked(sensor.isActive)
                            Log.d(TAG, error.message)
                        })
    }
}
