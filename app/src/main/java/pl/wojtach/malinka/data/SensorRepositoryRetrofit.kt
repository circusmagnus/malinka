package pl.wojtach.malinka.data

import pl.wojtach.malinka.logic.Sensor
import pl.wojtach.malinka.logic.SensorLocation
import pl.wojtach.malinka.logic.SensorRepository
import rx.Observable

/**
 * Created by Lukasz on 08.01.2017.
 */
class SensorRepositoryRetrofit(val sensorDataProvider: RetrofitProvider) : SensorRepository {

    override fun getInfoFromSensors(location: SensorLocation): Observable<List<Sensor>> {
        return sensorDataProvider.dataProvider.getSensors()
    }
}