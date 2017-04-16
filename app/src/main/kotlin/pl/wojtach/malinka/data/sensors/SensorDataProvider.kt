package pl.wojtach.malinka.data.sensors

import pl.wojtach.malinka.entities.Sensor
import retrofit2.http.GET
import retrofit2.http.Query
import io.reactivex.Observable

/**
 * Created by Lukasz on 08.01.2017.
 */
interface SensorDataProvider {

    @GET("lastStatus")
    fun getSensors(): Observable<List<Sensor>>

    @GET("setDeviceStatus")
    fun setDeviceStatus(@Query("sensor") macAddres: String,
                        @Query("type") type: Int,
                        @Query("status") isActive: Int): Observable<Void>
}