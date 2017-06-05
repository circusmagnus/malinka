package pl.wojtach.malinka.networking

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import pl.wojtach.malinka.statemachine.entities.Sensor
import retrofit2.http.GET


/**
 * Created by Lukasz on 29.05.2017.
 */
interface SensorDataFetcher {
    fun fetchData(): Single<List<Sensor>>

    companion object {
        fun withRetrofit(): SensorDataFetcher = SensorDataFetcherRetrofit()
    }
}

internal class SensorDataFetcherRetrofit : SensorDataFetcher {

    override fun fetchData(): Single<List<Sensor>> = RetrofitProvider
            .retrofit
            .create(DataProvider::class.java)
            .getSensors()
            .subscribeOn(Schedulers.io())
    //.observeOn(AndroidSchedulers.mainThread())

}

interface DataProvider {

    @GET("lastStatus")
    fun getSensors(): Single<List<Sensor>>

}