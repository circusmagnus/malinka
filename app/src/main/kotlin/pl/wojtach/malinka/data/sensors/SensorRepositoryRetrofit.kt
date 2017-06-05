package pl.wojtach.malinka.data.sensors

import io.reactivex.Single
import pl.wojtach.malinka.data.RetrofitProvider
import pl.wojtach.malinka.statemachine.entities.Sensor
import pl.wojtach.malinka.statemachine.entities.SensorLocation
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Lukasz on 08.01.2017.
 */
class SensorRepositoryRetrofit(val retrofitProvider: RetrofitProvider) : SensorRepository {

    val sensorDataProvider = retrofitProvider.retrofit.create(SensorDataProvider::class.java)

    override fun setSensorStatus(sensor: Sensor) = sensorDataProvider.setDeviceStatus(sensor.mac, sensor.type, getStatus(sensor))

    fun getStatus(sensor: Sensor) = if (sensor.isActive) 1 else 0

    override fun getInfoFromSensors(location: SensorLocation) = sensorDataProvider.getSensors()
}

/**
 * Created by Lukasz on 08.01.2017.
 */
interface SensorDataProvider {

    @GET("lastStatus")
    fun getSensors(): Single<List<Sensor>>

    @GET("setDeviceStatus")
    fun setDeviceStatus(@Query("sensor") macAddres: String,
                        @Query("type") type: Int,
                        @Query("status") isActive: Int): Single<Void>
}