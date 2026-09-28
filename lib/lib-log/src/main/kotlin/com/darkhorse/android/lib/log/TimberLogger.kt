package com.darkhorse.android.lib.log

import com.darkhorse.android.core.log.ILogger
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Timber 日志实现
 */
@Singleton
class TimberLogger @Inject constructor() : ILogger {

    override fun v(tag: String, message: String, throwable: Throwable?) {
        Timber.tag(tag).v(throwable, message)
    }

    override fun d(tag: String, message: String, throwable: Throwable?) {
        Timber.tag(tag).d(throwable, message)
    }

    override fun i(tag: String, message: String, throwable: Throwable?) {
        Timber.tag(tag).i(throwable, message)
    }

    override fun w(tag: String, message: String, throwable: Throwable?) {
        Timber.tag(tag).w(throwable, message)
    }

    override fun e(tag: String, message: String, throwable: Throwable?) {
        Timber.tag(tag).e(throwable, message)
    }

    override fun crash(throwable: Throwable, tag: String?) {
        Timber.tag(tag ?: "Crash").wtf(throwable)
    }
}
