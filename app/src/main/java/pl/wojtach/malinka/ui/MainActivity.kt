package pl.wojtach.malinka.ui

import android.app.Activity
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import pl.wojtach.malinka.R
import pl.wojtach.malinka.databinding.ActivityMainBinding

class MainActivity : Activity() {

    lateinit var viewModel: ActivityMainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        val sensorListAdapter = SensorAdapter(mutableListOf())
        binding.sensorList.adapter = sensorListAdapter
        binding.sensorList.layoutManager = LinearLayoutManager(this)
        viewModel = ActivityMainViewModel(sensorListAdapter)
        binding.viewModel = viewModel
    }

    override fun onStart() {
        super.onStart()
        viewModel.refreshSensorList()
    }
}

