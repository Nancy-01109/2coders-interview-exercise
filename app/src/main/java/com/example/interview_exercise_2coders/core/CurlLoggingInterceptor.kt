package com.example.interview_exercise_2coders.core

import okhttp3.Interceptor
import okhttp3.Response

// For debugging the API url
class CurlLoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val curlCommand = buildString {
            append("curl -X ${request.method} '${request.url}'")
            request.headers.forEach { header ->
                append(" -H '${header.first}: ${header.second}'")
            }
            request.body?.let {
                append(" -d '${it}'")
            }
        }

        // Print the curl command
        println("CURL: $curlCommand")

        return chain.proceed(request)
    }
}
