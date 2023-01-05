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

    // Creating a test dispatcher
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        // Assigning the test dispatcher
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun apiSearch() = runTest {
        // Defining count down latch for waiting the async task
        val latch = CountDownLatch(1)
        val username = "gamer"

        println(String.format("Searching user by name (%s)", username))
        // Search a user with the api
        RestApi.search(username) { data ->
            assertNotNull(data)
            assertNotNull(data[0])

            println(String.format("User found", username))

            // Releasing the latch
            latch.countDown()
        }

        // Wait before the test can exit
        latch.await(10000, TimeUnit.MILLISECONDS)
    }

    @After
    fun tearDown() {
        // Reset the test dispatcher
        Dispatchers.resetMain()
    }
}