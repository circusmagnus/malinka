package pl.wojtach.malinka.statemachine.entities

import com.google.gson.annotations.SerializedName

/**
 * Created by Lukasz on 07.01.2017.
 */
data class Sensor(
        var mac: String,
        @SerializedName("description") var name: String,
        var type: Int,
        @SerializedName("status") var isActive: Boolean,
        @SerializedName("lastvalue") var lastValue: String,
        @SerializedName("lastdate") var lastDate: String
) {
}