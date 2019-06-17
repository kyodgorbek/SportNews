package edgar.yodgorbek.yangiliklar.internet


import edgar.yodgorbek.yangiliklar.model.SportInterface

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SportClient {

    internal val httpLoggingInterceptor: HttpLoggingInterceptor

        get() {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            return httpLoggingInterceptor
        }


    internal fun getOkHttpCleint(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build()
    }

    companion object {

        private val ROOT_URL = "https://newsapi.org"

        /**
         * Get Retrofit Instance
         */
        private val retrofitInstance: Retrofit
            get() = Retrofit.Builder()
                    .baseUrl(ROOT_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

        /**
         * Get API Service
         *
         * @return API Service
         */
        val apiService: SportInterface

            get() = retrofitInstance.create(SportInterface::class.java)
    }

}
