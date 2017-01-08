package pl.wojtach.malinka.ui

import android.app.Activity
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import pl.wojtach.malinka.R
import pl.wojtach.malinka.data.RetrofitProvider
import pl.wojtach.malinka.data.SensorRepositoryRetrofit
import pl.wojtach.malinka.databinding.ActivityMainBinding

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val layoutManager = LinearLayoutManager(this)
        binding.sensorList.setLayoutManager(layoutManager)

        val sensors = SensorRepositoryRetrofit(RetrofitProvider)
                .getInfoFromSensors()
                .toList()

        //val sensorAdapter = SensorAdapter()
    }
}

