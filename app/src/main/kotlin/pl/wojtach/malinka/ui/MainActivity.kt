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

    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        viewModel = MainActivityViewModel(listAdapter = SensorAdapter(mutableListOf()),
                repository = SensorRepositoryRetrofit(RetrofitProvider),
                layoutManager = LinearLayoutManager(this))
        binding.viewModel = viewModel
    }

    override fun onStart() {
        super.onStart()
        viewModel.refreshSensorList()
    }
}

