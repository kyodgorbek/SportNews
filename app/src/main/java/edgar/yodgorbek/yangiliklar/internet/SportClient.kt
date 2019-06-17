package edgar.yodgorbek.yangiliklar.internet

import dagger.Module
import dagger.Provides
import edgar.yodgorbek.yangiliklar.model.SportInterface
import edgar.yodgorbek.yangiliklar.scopes.ApplicationScope
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class SportClient {

    internal val httpLoggingInterceptor: HttpLoggingInterceptor
        @Provides
        @ApplicationScope
        get() {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            return httpLoggingInterceptor
        }

    @Provides
    @ApplicationScope
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
            @Provides
            @ApplicationScope
            get() = retrofitInstance.create(SportInterface::class.java)
    }

}
