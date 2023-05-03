package com.example.silencio_app.data.di

import android.app.Application
import com.example.silencio_app.BuildConfig
import com.example.silencio_app.data.network.AuthApi
import com.example.silencio_app.data.source.AuthDataSource
import com.example.silencio_app.data.source.WalletDataSource
import com.example.silencio_app.ditailsfragment.wallet.tab.WalletApi
import com.example.silencio_app.util.PrefManagers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModel {

    val baseUrl = "https://zignuts.dev/silencio/api/"

//val interceptor = SupportInterceptor()
//    val logging = HttpLoggingInterceptor().apply {
//        level = HttpLoggingInterceptor.Level.BODY
//    }
    class SupportInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            var request = chain.request()

            if (PrefManagers.getString(PrefManagers.ACCESS_TOKEN).isNotEmpty()) {
                request = request.newBuilder()
                    .header(
                        "x-auth",
                        PrefManagers.getString(PrefManagers.ACCESS_TOKEN)
                    )
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .build()
                return chain.proceed(request)
            }
//                .header(
//                    "Currency",
//                    "${PrefManager.getString(PrefManager.CURRENCY_CODE)}"
//                )
            request = request.newBuilder()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build()
            return chain.proceed(request)
        }
    }



    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        val interceptor = SupportInterceptor()
        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addInterceptor(logging)
            .build()
    }
    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }



    @Singleton
    @Provides
    fun provideRetrofitInstance(

        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(
                baseUrl
            )
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }



    @Singleton
    @Provides
    fun provideStarterTemplateApiService(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Singleton
    @Provides
    fun provideDashboardDataSource(application: Application,dashboardApi: AuthApi): AuthDataSource {
        return AuthDataSource(application,dashboardApi)
    }

    @Singleton
    @Provides
    fun TemplateApiServiceTabWallet(retrofit: Retrofit): WalletApi {
        return retrofit.create(WalletApi::class.java)
    }

    @Singleton
    @Provides
    fun TabWalletDataSource(walletdashboardApi: WalletApi): WalletDataSource {
        return WalletDataSource(walletdashboardApi)
    }

}