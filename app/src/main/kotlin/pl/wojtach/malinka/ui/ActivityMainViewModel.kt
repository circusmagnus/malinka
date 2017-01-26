package pl.wojtach.malinka.ui

import android.databinding.ObservableBoolean
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import pl.wojtach.malinka.data.RetrofitProvider
import pl.wojtach.malinka.data.SensorRepositoryRetrofit
import pl.wojtach.malinka.logic.SensorRepository
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by Lukasz on 14.01.2017.
 */
class ActivityMainViewModel(
        val sensorListAdapter: SensorAdapter,
        val repository: SensorRepository = SensorRepositoryRetrofit(RetrofitProvider)
) : SwipeRefreshLayout.OnRefreshListener {

    val TAG = ActivityMainViewModel::class.java.simpleName
    var isRefreshing = ObservableBoolean(false)

    fun refreshSensorList() {
        isRefreshing.set(true)
        repository
                .getInfoFromSensors()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    sensorListAdapter.sensors.clear()
                    sensorListAdapter.sensors.addAll(it)
                    sensorListAdapter.notifyDataSetChanged()
                    isRefreshing.set(false)
                }, { error ->
                    Log.d(TAG, "can`t load data: " + error.message)
                    isRefreshing.set(false)
                })
    }

    override fun onRefresh() {
        refreshSensorList()
    }
}