package pl.wojtach.malinka.networking

import com.google.gson.annotations.SerializedName

/**
 * Created by lukaszwojtach on 07.06.2017.
 */
data class WsSensor(var mac: String,
                    @SerializedName("description") var name: String,
                    var type: Int,
                    @SerializedName("status") var isActive: Boolean,
                    @SerializedName("lastvalue") var lastValue: String,
                    @SerializedName("lastdate") var lastDate: String
)