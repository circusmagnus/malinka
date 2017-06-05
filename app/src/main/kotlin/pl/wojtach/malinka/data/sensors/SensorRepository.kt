package pl.wojtach.malinka.data.sensors

import io.reactivex.Single
import pl.wojtach.malinka.data.RetrofitProvider
import pl.wojtach.malinka.statemachine.entities.Sensor
import pl.wojtach.malinka.statemachine.entities.SensorLocation

/**
 * Created by Lukasz on 08.01.2017.
 */
interface SensorRepository {
    fun getInfoFromSensors(location: SensorLocation = SensorLocation.KOSZALIN): Single<List<Sensor>>

    fun setSensorStatus(sensor: Sensor): Single<Void>

    companion object {
        fun provideSensorRepository(): SensorRepository = SensorRepositoryRetrofit(RetrofitProvider)
    }
}
