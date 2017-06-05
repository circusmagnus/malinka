package pl.wojtach.malinka.ui.main_screen

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import pl.wojtach.malinka.R
import pl.wojtach.malinka.databinding.SensorViewholderBinding
import pl.wojtach.malinka.statemachine.entities.Sensor

class SensorAdapter(val sensors: MutableList<Sensor>) : RecyclerView.Adapter<SensorAdapter.SensorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SensorViewHolder {
        return SensorViewHolder(
                DataBindingUtil.inflate<SensorViewholderBinding>(
                        LayoutInflater.from(parent?.context),
                        R.layout.sensor_viewholder,
                        parent,
                        false
                )
        )
    }

    override fun getItemCount(): Int {
        return sensors.size
    }

    override fun onBindViewHolder(holder: SensorViewHolder?, position: Int) {
        holder?.binding?.sensorViewModel = SensorViewModel(sensors[position])
    }


    class SensorViewHolder(val binding: SensorViewholderBinding) : RecyclerView.ViewHolder(binding.root)
}