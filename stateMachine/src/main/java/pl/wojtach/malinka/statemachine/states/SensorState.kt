package pl.wojtach.malinka.statemachine.states

import pl.wojtach.malinka.statemachine.entities.Sensor

/**
 * Created by lukaszwojtach on 30.05.2017.
 */
data class SensorState(val phase: PHASE, val sensors: List<Sensor>) : android.os.Parcelable {

    companion object {
        @JvmField val CREATOR: android.os.Parcelable.Creator<SensorState> = object : android.os.Parcelable.Creator<SensorState> {
            override fun createFromParcel(source: android.os.Parcel): pl.wojtach.malinka.statemachine.states.SensorState = pl.wojtach.malinka.statemachine.states.SensorState(source)
            override fun newArray(size: Int): Array<pl.wojtach.malinka.statemachine.states.SensorState?> = arrayOfNulls(size)
        }
    }

    constructor(source: android.os.Parcel) : this(
            PHASE.values()[source.readInt()],
            ArrayList<Sensor>().apply { source.readList(this, Sensor::class.java.classLoader) }
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: android.os.Parcel, flags: Int) {
        dest.writeInt(phase.ordinal)
        dest.writeList(sensors)
    }
}