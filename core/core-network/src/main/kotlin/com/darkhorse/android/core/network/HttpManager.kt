package com.darkhorse.android.core.network

import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException

object HttpManager {

    suspend inline fun <reified RESP> request(request: DhRequest<RESP>): DhResponse<RESP> {
        return DhResponse(null)
    }


}

