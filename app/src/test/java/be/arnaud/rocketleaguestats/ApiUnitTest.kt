package be.arnaud.rocketleaguestats

import be.arnaud.rocketleaguestats.api.RestApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ApiUnitTest {

    val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun apiSearch() = runTest {
        val latch = CountDownLatch(1)
        val username = "gamer"

        println(String.format("Searching user by name (%s)", username))
        RestApi.search(username) { data ->
            assertNotNull(data)

            val firstEntry = data[0]

            assertNotNull(firstEntry)

            println(String.format("User found", username))

            latch.countDown()
        }

        latch.await(10000, TimeUnit.MILLISECONDS)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}