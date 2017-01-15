package pl.wojtach.malinka.ui

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import pl.wojtach.malinka.data.RetrofitProvider
import pl.wojtach.malinka.data.SensorRepositoryRetrofit
import pl.wojtach.malinka.databinding.ActivityMainBinding
import pl.wojtach.malinka.logic.Sensor
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by Lukasz on 14.01.2017.
 */
class ActivityMainViewModel(val boundView: ActivityMainBinding) : SwipeRefreshLayout.OnRefreshListener {

    val TAG = ActivityMainViewModel::class.java.simpleName

    val sensors: MutableList<Sensor> = mutableListOf()

    fun onCreate() {
        val layoutManager = LinearLayoutManager(boundView.activityMain.context)
        boundView.sensorList.setLayoutManager(layoutManager)
        boundView.sensorList.adapter = SensorAdapter(sensors)
        boundView.swipeRefreshLayout.setOnRefreshListener(this)
    }

    fun refreshSensorList() {
        SensorRepositoryRetrofit(RetrofitProvider)
                .getInfoFromSensors()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    sensors.clear()
                    sensors.addAll(it)
                    boundView.sensorList.adapter.notifyDataSetChanged()
                    boundView.swipeRefreshLayout.isRefreshing = false
                }, { error ->
                    boundView.swipeRefreshLayout.isRefreshing = false
                    Log.d(TAG, "can`t load data: " + error.message)
                })
    }

    override fun onRefresh() {
        refreshSensorList()
    }
}