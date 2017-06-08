package pl.wojtach.malinka.statemachine.entities

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Lukasz on 07.01.2017.
 */
data class Sensor(
        val mac: String,
        val name: String,
        val type: Int,
        val isActive: Boolean,
        val lastValue: String,
        val lastDate: String,
        val hasError: Boolean,
        val switchTo: SWITCH_TO
) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Sensor> = object : Parcelable.Creator<Sensor> {
            override fun createFromParcel(source: Parcel): Sensor = Sensor(source)
            override fun newArray(size: Int): Array<Sensor?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readInt(),
            1 == source.readInt(),
            source.readString(),
            source.readString(),
            1 == source.readInt(),
            SWITCH_TO.values()[source.readInt()]
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(mac)
        dest.writeString(name)
        dest.writeInt(type)
        dest.writeInt((if (isActive) 1 else 0))
        dest.writeString(lastValue)
        dest.writeString(lastDate)
        dest.writeInt((if (hasError) 1 else 0))
        dest.writeInt(switchTo.ordinal)
    }
}

enum class SWITCH_TO {
    ENABLED, DISABLED, NO_CHANGE
}
