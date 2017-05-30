package pl.wojtach.malinka.data

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import pl.wojtach.malinka.data.sensors.SensorRepository
import pl.wojtach.malinka.entities.Sensor


/**
 * Created by Lukasz on 29.05.2017.
 */
interface DataFetcher {
    fun fetchData(): Single<List<Sensor>>
}

class DataFetcherRetrofit(val repository: SensorRepository) : DataFetcher {

    override fun fetchData() = repository.getInfoFromSensors()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

}