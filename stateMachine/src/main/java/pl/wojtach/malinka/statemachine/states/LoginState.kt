package pl.wojtach.malinka.statemachine.states

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by lukaszwojtach on 16.04.2017.
 */
data class LoginState(
        val phaseOfLogging: PHASE,
        val currentUser: String,
        val currentPassword: String,
        val currentBaseUrl: String
) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<LoginState> = object : Parcelable.Creator<LoginState> {
            override fun createFromParcel(source: Parcel): LoginState = LoginState(source)
            override fun newArray(size: Int): Array<LoginState?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            PHASE.values()[source.readInt()],
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(phaseOfLogging.ordinal)
        dest.writeString(currentUser)
        dest.writeString(currentPassword)
        dest.writeString(currentBaseUrl)
    }
}