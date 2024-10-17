package com.compose.experiment

import com.compose.experiment.utils.dispatcher.DispatchersHub
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        DispatchersHub.set(
            io = UnconfinedTestDispatcher()
        )
    }

    @After
    fun tearDown() {
        DispatchersHub.reset()
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


}