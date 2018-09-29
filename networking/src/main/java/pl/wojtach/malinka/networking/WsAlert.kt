package pl.wojtach.malinka.networking

import com.google.gson.annotations.SerializedName

/**
 * Created by Lukasz on 16.12.2017.
 */
data class WsAlert(
        @SerializedName("sensormac") var mac: String?,
        @SerializedName("sensortype") var type: Int?,
        var date: String?
)