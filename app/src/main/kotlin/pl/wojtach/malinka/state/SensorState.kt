package pl.wojtach.malinka.state

import android.os.Parcel
import android.os.Parcelable
import pl.wojtach.malinka.entities.Sensor

/**
 * Created by lukaszwojtach on 30.05.2017.
 */
data class SensorState(val phase: PHASE, val sensors: List<Sensor>) : Parcelable {

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<SensorState> = object : Parcelable.Creator<SensorState> {
            override fun createFromParcel(source: Parcel): SensorState = SensorState(source)
            override fun newArray(size: Int): Array<SensorState?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            PHASE.values()[source.readInt()],
            ArrayList<Sensor>().apply { source.readList(this, Sensor::class.java.classLoader) }
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(phase.ordinal)
        dest.writeList(sensors)
    }
}