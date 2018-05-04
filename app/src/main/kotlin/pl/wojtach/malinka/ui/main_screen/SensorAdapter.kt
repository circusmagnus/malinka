package pl.wojtach.malinka.ui.main_screen

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import pl.wojtach.camera.SeeCameraDispatcher
import pl.wojtach.malinka.R
import pl.wojtach.malinka.databinding.SensorViewholderBinding

class SensorAdapter(
        sensors: List<SensorViewModel>,
        private val sensorClicksDispatcher: SensorClicksDispatcher,
        private val seeCameraDispatcher: SeeCameraDispatcher) : RecyclerView.Adapter<SensorAdapter.SensorViewHolder>() {

    var sensors = sensors
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SensorViewHolder {
        return SensorViewHolder(
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.sensor_viewholder,
                        parent,
                        false
                )
        )
    }

    override fun getItemCount(): Int {
        return sensors.size
    }

    override fun onBindViewHolder(holder: SensorViewHolder, position: Int) {
        holder.binding.model = sensors[position]
        holder.binding.sensorClicksDispatcher = sensorClicksDispatcher
        holder.binding.seeCameraDispatcher = seeCameraDispatcher

    }


    class SensorViewHolder(val binding: SensorViewholderBinding) : RecyclerView.ViewHolder(binding.root)
}

