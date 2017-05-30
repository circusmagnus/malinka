package pl.wojtach.malinka.state

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by lukaszwojtach on 16.04.2017.
 */
data class ErrorState(val errorMessages: Set<String>) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<ErrorState> = object : Parcelable.Creator<ErrorState> {
            override fun createFromParcel(source: Parcel): ErrorState = ErrorState(source)
            override fun newArray(size: Int): Array<ErrorState?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            ArrayList<String>().apply { source.readList(this, String::class.java.classLoader) }.toSet()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeList(errorMessages.toList())
    }
}