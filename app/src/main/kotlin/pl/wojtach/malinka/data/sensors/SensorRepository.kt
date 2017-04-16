package pl.wojtach.malinka.data.sensors

import io.reactivex.Observable
import pl.wojtach.malinka.entities.Sensor
import pl.wojtach.malinka.entities.SensorLocation

/**
 * Created by Lukasz on 08.01.2017.
 */
interface SensorRepository {
    fun getInfoFromSensors(location: SensorLocation = SensorLocation.KOSZALIN): Observable<List<Sensor>>

    fun setSensorStatus(sensor: Sensor): Observable<Void>
}
