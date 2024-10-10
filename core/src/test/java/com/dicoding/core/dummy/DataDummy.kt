package com.dicoding.core.dummy

import com.dicoding.core.data.source.remote.response.ListUpComingMovieResponse
import com.dicoding.core.data.source.remote.response.ResultsItem

object DataDummy {

    fun generateDummyUpcomingMovieResponse(): ListUpComingMovieResponse {
        return ListUpComingMovieResponse(
            page = 1,
            totalPages = 10,
            results = generateDummyResultsItems(),
            totalResults = 100
        )
    }

    private fun generateDummyResultsItems(): List<ResultsItem> {
        return listOf(
            ResultsItem(
                overview = "An action-packed movie about heroes saving the world.",
                originalLanguage = "en",
                originalTitle = "Heroic Adventure",
                video = false,
                title = "Heroic Adventure",
                genreIds = null,
                posterPath = "/path/to/poster1.jpg",
                backdropPath = "/path/to/backdrop1.jpg",
                releaseDate = "2024-10-10",
                popularity = 123.45,
                voteAverage = 8.7,
                id = 1,
                adult = false,
                voteCount = 500
            ),
            ResultsItem(
                overview = "A romantic drama set in a dystopian future.",
                originalLanguage = "en",
                originalTitle = "Love in the Future",
                video = false,
                title = "Love in the Future",
                genreIds = listOf(18, 10749),
                posterPath = "/path/to/poster2.jpg",
                backdropPath = "/path/to/backdrop2.jpg",
                releaseDate = "2024-09-15",
                popularity = 98.76,
                voteAverage = 7.5,
                id = 2,
                adult = false,
                voteCount = 350
            ),
            ResultsItem(
                overview = "An animated tale about talking animals embarking on a journey.",
                originalLanguage = "en",
                originalTitle = "Animal Odyssey",
                video = false,
                title = "Animal Odyssey",
                genreIds = listOf(16, 10751),
                posterPath = "/path/to/poster3.jpg",
                backdropPath = "/path/to/backdrop3.jpg",
                releaseDate = "2024-11-20",
                popularity = 75.32,
                voteAverage = 8.0,
                id = 3,
                adult = false,
                voteCount = 200
            )
        )
    }
}
