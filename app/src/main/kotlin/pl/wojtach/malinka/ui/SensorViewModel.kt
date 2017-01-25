package pl.wojtach.malinka.ui

import android.databinding.ObservableBoolean
import android.util.Log
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
    val isActiveObservable = ObservableBoolean(sensor.isActive)
    val errorOccuredObservable = ObservableBoolean(false)

    fun getName() = sensor.name
    fun getValue() = sensor.lastValue
    fun getDate() = sensor.lastDate

    fun setNewStatus() {
        val newSensorStatus = sensor.copy(isActive = isActiveObservable.get())
        SensorRepositoryRetrofit(RetrofitProvider)
                .setSensorStatus(newSensorStatus)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { success ->
                            sensor.apply { isActive = newSensorStatus.isActive }
                            isActiveObservable.set(sensor.isActive)
                            errorOccuredObservable.set(false)
                        },
                        { error ->
                            Log.d(TAG, error.message)
                            isActiveObservable.set(sensor.isActive)
                            errorOccuredObservable.set(true)
                        })
    }
}
