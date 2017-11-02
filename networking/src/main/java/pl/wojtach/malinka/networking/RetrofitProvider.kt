package pl.wojtach.malinka.networking

import android.util.Base64
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

internal object RetrofitProvider {

    val retrofit = provideRetrofit(provideClient(), provideGson())

    fun getPasswordedRetrofit(user: String, password: String, baseUrl: String): Retrofit =
            retrofit
                    .newBuilder()
                    .baseUrl(baseUrl)
                    .client(provideClient(LoginData(user, password)))
                    .build()

    private fun provideRetrofit(client: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
                .baseUrl("http://smarthomeproject.mybluemix.net/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
    }

    private fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    private fun provideClient(loginData: LoginData = LoginData("", "")): OkHttpClient {
        return OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(createLoggingInterceptor())
                .addInterceptor(createPasswordInterceptor(loginData))
                .build()
    }

    private fun createLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private fun createPasswordInterceptor(loginData: LoginData) = Interceptor { chain ->
        val request = chain.request().newBuilder().header(
                "Authorization", String.format("Basic %s", Base64.encodeToString(
                String.format("%s:%s", loginData.user, loginData.password).toByteArray(), Base64.NO_WRAP)
        )
        )
                .build()
        chain.proceed(request)
    }
}

data class LoginData(val user: String, val password: String)