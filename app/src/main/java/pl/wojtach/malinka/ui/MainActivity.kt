package pl.wojtach.malinka.ui

import android.app.Activity
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import pl.wojtach.malinka.R
import pl.wojtach.malinka.data.RetrofitProvider
import pl.wojtach.malinka.data.SensorRepositoryRetrofit
import pl.wojtach.malinka.databinding.ActivityMainBinding
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainActivity : Activity() {

    /*val sensorList = emptyList<Sensor>()
    val sensorAdapter: SensorAdapter = SensorAdapter(sensorList)*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val layoutManager = LinearLayoutManager(this)
        binding.sensorList.setLayoutManager(layoutManager)

        SensorRepositoryRetrofit(RetrofitProvider)
                .getInfoFromSensors()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { binding.sensorList.adapter = SensorAdapter(it) }

    }
}

