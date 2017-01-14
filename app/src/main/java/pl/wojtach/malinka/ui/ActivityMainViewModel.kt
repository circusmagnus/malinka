package pl.wojtach.malinka.ui

import android.support.v7.widget.LinearLayoutManager
import pl.wojtach.malinka.data.RetrofitProvider
import pl.wojtach.malinka.data.SensorRepositoryRetrofit
import pl.wojtach.malinka.databinding.ActivityMainBinding
import pl.wojtach.malinka.logic.Sensor
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by Lukasz on 14.01.2017.
 */
class ActivityMainViewModel(val boundView: ActivityMainBinding) {

    val sensors: MutableList<Sensor> = mutableListOf()

    fun onCreate() {
        val layoutManager = LinearLayoutManager(boundView.activityMain.context)
        boundView.sensorList.setLayoutManager(layoutManager)
        boundView.sensorList.adapter = SensorAdapter(sensors)
    }

    fun refreshSensorList() {
        SensorRepositoryRetrofit(RetrofitProvider)
                .getInfoFromSensors()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    sensors.clear()
                    sensors.addAll(it)
                    boundView.sensorList.adapter.notifyDataSetChanged()
                }
    }
}