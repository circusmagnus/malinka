package pl.wojtach.malinka.data.sensors

import pl.wojtach.malinka.entities.Sensor
import pl.wojtach.malinka.entities.SensorLocation
import pl.wojtach.malinka.data.sensors.SensorRepository
import io.reactivex.Observable
import pl.wojtach.malinka.data.RetrofitProvider

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