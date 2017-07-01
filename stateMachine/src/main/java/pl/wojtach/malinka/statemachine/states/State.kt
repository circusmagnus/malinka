package pl.wojtach.malinka.statemachine.states

/**
 * Created by lukaszwojtach on 16.04.2017.
 */
data class State(
        val errorState: ErrorState?,
        val loginState: LoginState,
        val sensorState: SensorState

)

//Parcelable impl below
    : android.os.Parcelable {
    companion object {
        @JvmField val CREATOR: android.os.Parcelable.Creator<State> = object : android.os.Parcelable.Creator<State> {
            override fun createFromParcel(source: android.os.Parcel): State = State(source)
            override fun newArray(size: Int): Array<State?> = arrayOfNulls(size)
        }

        const val BUNDLE_KEY = "STATE"
    }

    constructor(source: android.os.Parcel) : this(
            source.readParcelable<ErrorState>(ErrorState::class.java.classLoader),
            source.readParcelable<LoginState>(LoginState::class.java.classLoader),
            source.readParcelable<SensorState>(SensorState::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: android.os.Parcel, flags: Int) {
        dest.writeParcelable(errorState, 0)
        dest.writeParcelable(loginState, 0)
        dest.writeParcelable(sensorState, 0)
    }
}