package com.dicoding.core.networktest

import com.dicoding.core.dummy.DataDummy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RemoteDataSourceTest {

    private val apiService = FakeApiService()

    @Test
    fun `when Get Upcoming Movies Should Not Null and Return Data`() = runTest {
        val expectedValue = DataDummy.generateDummyUpcomingMovieResponse()

        val actualValue = apiService.getUpComingMovies("")

        Assert.assertNotNull(actualValue)
        Assert.assertEquals(expectedValue.results?.size, actualValue.results?.size)
    }

    @Test
    fun `when Get Upcoming Movies Should Return Correct Data`() = runTest {
        val expectedValue = DataDummy.generateDummyUpcomingMovieResponse()

        val actualValue = apiService.getUpComingMovies("")

        Assert.assertEquals(expectedValue.results?.first()?.title, actualValue.results?.first()?.title)
    }

    @Test
    fun `when Get Upcoming Movies Empty Should Return No Data`() = runTest {
        apiService.shouldReturnEmptyList = true

        val actualValue = apiService.getUpComingMovies("")

        Assert.assertEquals(0, actualValue.results?.size)
    }

    @Test
    fun `when Get Detail Movie Should Return Correct Movie`() = runTest {
        val expectedValue = DataDummy.generateDummyUpcomingMovieResponse().results?.firstOrNull()
        Assert.assertNotNull(expectedValue)

        val actualValue = apiService.getDetailMovie(expectedValue?.id ?: 0, "")

        Assert.assertNotNull(actualValue)
        Assert.assertEquals(expectedValue?.title, actualValue.title)
    }

    @Test
    fun `when Get Detail Movie with Invalid ID Should Return Default Data`() = runTest {
        val actualValue = apiService.getDetailMovie(-1, "")

        Assert.assertEquals("Unknown", actualValue.title)
        Assert.assertEquals("No description available", actualValue.overview)
    }
}
