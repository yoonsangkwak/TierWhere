package site.yoonsang.tierwhere.config

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import site.yoonsang.tierwhere.BuildConfig
import java.io.IOException

class XRiotTokenInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        val riotToken = BuildConfig.RIOT_API_KEY
        builder.addHeader(ApplicationClass.X_RIOT_TOKEN, riotToken)
        return chain.proceed(builder.build())
    }
}