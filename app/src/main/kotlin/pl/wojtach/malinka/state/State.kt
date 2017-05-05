package pl.wojtach.malinka.state

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by lukaszwojtach on 16.04.2017.
 */
data class State(
        val errorState: ErrorState?,
        val loginState: LoginState
        //val mainScreenState: MainScreenState,
        //val enviromentState: EnviromentState
)

//Parcelable impl below
    : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<State> = object : Parcelable.Creator<State> {
            override fun createFromParcel(source: Parcel): State = State(source)
            override fun newArray(size: Int): Array<State?> = arrayOfNulls(size)
        }

        const val BUNDLE_KEY = "STATE"
    }

    constructor(source: Parcel) : this(
            source.readParcelable<ErrorState>(ErrorState::class.java.classLoader),
            source.readParcelable<LoginState>(LoginState::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeParcelable(errorState, 0)
        dest.writeParcelable(loginState, 0)
    }
}