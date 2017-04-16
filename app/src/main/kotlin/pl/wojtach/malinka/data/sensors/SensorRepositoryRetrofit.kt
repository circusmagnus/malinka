package pl.wojtach.malinka.data.sensors

import io.reactivex.Observable
import pl.wojtach.malinka.data.RetrofitProvider
import pl.wojtach.malinka.entities.Sensor
import pl.wojtach.malinka.entities.SensorLocation
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Lukasz on 08.01.2017.
 */
class SensorRepositoryRetrofit(val sensorDataProvider: RetrofitProvider) : SensorRepository {

    override fun setSensorStatus(sensor: Sensor): Observable<Void> {
        fun getStatus() = if (sensor.isActive) 1 else 0
        return sensorDataProvider.dataProvider.setDeviceStatus(sensor.mac, sensor.type, getStatus())
    }

    override fun getInfoFromSensors(location: SensorLocation): Observable<List<Sensor>> {
        return sensorDataProvider.dataProvider.getSensors()
    }
}

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