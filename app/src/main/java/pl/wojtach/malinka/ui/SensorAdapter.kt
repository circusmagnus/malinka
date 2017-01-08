package pl.wojtach.malinka.ui

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import pl.wojtach.malinka.logic.Sensor

class SensorAdapter(val sensors: List<Sensor>) : RecyclerView.Adapter<SensorAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    class ViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}