package pl.wojtach.malinka.logic

import rx.Observable

/**
 * Created by Lukasz on 08.01.2017.
 */
interface SensorRepository {
    fun getInfoFromSensors(location: SensorLocation = SensorLocation.KOSZALIN): Observable<List<Sensor>>

    fun setSensorStatus(sensor: Sensor): Observable<Void>
}
