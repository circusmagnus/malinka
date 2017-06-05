package pl.wojtach.malinka.data

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import pl.wojtach.malinka.statemachine.entities.Sensor
import retrofit2.http.GET


/**
 * Created by Lukasz on 29.05.2017.
 */
interface SensorDataFetcher {
    fun fetchData(): Single<List<Sensor>>

    companion object {
        fun withRetrofit() = SensorDataFetcherRetrofit()
    }
}

class SensorDataFetcherRetrofit : SensorDataFetcher {

    override fun fetchData() = RetrofitProvider
            .retrofit
            .create(DataProvider::class.java)
            .getSensors()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

}

interface DataProvider {

    @GET("lastStatus")
    fun getSensors(): Single<List<Sensor>>

}