package com.example.busstation.network

import android.os.Build
import android.util.Log
import com.example.busstation.constants.ApiAddress
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.security.KeyStore
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

object ApiClient {
    private var retrofit: Retrofit? = null
    private const val REQUEST_TIMEOUT = 30

    /**
     * Retrofit client setting
     */
    fun getClient(): Retrofit {

        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(ApiAddress.BASE_URL)
                .client(initOkHttp())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(TikXmlConverterFactory.create())
                .build()
        }
        return retrofit!!
    }

    /**
     * OkHttp settings
     */
    private fun initOkHttp(): OkHttpClient {
        return enableTls12OnPreLollipop(OkHttpClient().newBuilder()
            .connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS))
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    /**
     * Pre Lollipop SSL exception settings
     * @param client: OkHttpClient builder
     */
    private fun enableTls12OnPreLollipop(client: OkHttpClient.Builder): OkHttpClient.Builder {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1) {
            try {
                val sc = SSLContext.getInstance("TLSv1.2")
                sc.init(null, null, null)
                val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
                trustManagerFactory.init(null as KeyStore?)
                val trustManagers = trustManagerFactory.trustManagers
                if (trustManagers.size != 1 || trustManagers[0] !is X509TrustManager) {
                    throw IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers))
                }
                client.sslSocketFactory(Tls12SocketFactory(sc.socketFactory), trustManagers[0] as X509TrustManager)

                val cs = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                    .tlsVersions(TlsVersion.TLS_1_2)
                    .build()

                val specs = ArrayList<ConnectionSpec>()
                specs.add(cs)
                specs.add(ConnectionSpec.COMPATIBLE_TLS)
                specs.add(ConnectionSpec.CLEARTEXT)

                client.connectionSpecs(specs)
            } catch (exc: Exception) {
                Log.e("OkHttpTLSCompat", "Error while setting TLS 1.2", exc)
            }

        }

        return client
    }
}