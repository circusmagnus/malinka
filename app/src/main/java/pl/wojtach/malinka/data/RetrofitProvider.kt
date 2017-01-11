package pl.wojtach.malinka.data

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitProvider {

    val dataProvider = provideSensorDataProviderRetrofit(
            provideRetrofit(
                    provideClient(), provideGson()
            )
    )

    private fun provideSensorDataProviderRetrofit(retrofit: Retrofit): SensorDataProvider {
        return retrofit.create(SensorDataProvider::class.java)
    }

    private fun provideRetrofit(client: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
                .baseUrl("http://smarthomeproject.mybluemix.net/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build()
    }

    private fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    private fun provideClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(90, TimeUnit.SECONDS)
                .build()
    }
}