package pl.wojtach.malinka.statemachine.states

/**
 * Created by lukaszwojtach on 16.04.2017.
 */
data class ErrorState(val errorMessages: Set<String>) : android.os.Parcelable {
    companion object {
        @JvmField val CREATOR: android.os.Parcelable.Creator<ErrorState> = object : android.os.Parcelable.Creator<ErrorState> {
            override fun createFromParcel(source: android.os.Parcel): pl.wojtach.malinka.statemachine.states.ErrorState = pl.wojtach.malinka.statemachine.states.ErrorState(source)
            override fun newArray(size: Int): Array<pl.wojtach.malinka.statemachine.states.ErrorState?> = arrayOfNulls(size)
        }
    }

    constructor(source: android.os.Parcel) : this(
            java.util.ArrayList<String>().apply { source.readList(this, String::class.java.classLoader) }.toSet()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: android.os.Parcel, flags: Int) {
        dest.writeList(errorMessages.toList())
    }
}