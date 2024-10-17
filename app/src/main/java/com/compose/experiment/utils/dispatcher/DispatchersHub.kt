package com.compose.experiment.utils.dispatcher

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Dispatchers that can be easily changed for testing.
 * It's similar to "RxJavaPlugins.set..."
 */
object DispatchersHub {

    var Main: CoroutineDispatcher = Dispatchers.Main
        private set

    var Default = Dispatchers.Default
        private set

    var IO = Dispatchers.IO
        private set

    var Unconfined = Dispatchers.Unconfined
        private set

    @VisibleForTesting
    fun set(
        main: CoroutineDispatcher = Main,
        default: CoroutineDispatcher = Default,
        io: CoroutineDispatcher = IO,
        unconfined: CoroutineDispatcher = Unconfined,
    ) {
        Main = main
        Default = default
        IO = io
        Unconfined = unconfined
    }

    @VisibleForTesting
    fun reset() {
        Main = Dispatchers.Main
        Default = Dispatchers.Default
        IO = Dispatchers.IO
        Unconfined = Dispatchers.Unconfined
    }
}