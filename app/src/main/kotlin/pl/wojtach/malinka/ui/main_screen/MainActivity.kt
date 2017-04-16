package pl.wojtach.malinka.ui.main_screen

import android.app.Activity
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import pl.wojtach.malinka.R
import pl.wojtach.malinka.data.RetrofitProvider
import pl.wojtach.malinka.data.sensors.SensorRepositoryRetrofit
import pl.wojtach.malinka.databinding.ActivityMainBinding

class MainActivity : Activity() {

    val viewModel = MainActivityViewModel(listAdapter = SensorAdapter(mutableListOf()),
            repository = SensorRepositoryRetrofit(RetrofitProvider),
            layoutManager = LinearLayoutManager(this))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.viewModel = viewModel
    }

    override fun onStart() {
        super.onStart()
        viewModel.refreshSensorList()
    }
}

