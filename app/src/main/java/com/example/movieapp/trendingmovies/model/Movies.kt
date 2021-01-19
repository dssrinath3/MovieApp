package com.example.movieapp.trendingmovies.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Movies(
    val page: Long,
    @Json(name = "total_pages")
    val totalPage: Long,
    @Json(name = "total_results")
    val totalResults: Long,
    val results: List<Movie>
)


@JsonClass(generateAdapter = true)
data class Movie(
    @Json(name = "vote_average")
    val voteAverage: Double?,
    @Json(name = "title")
    val title: String?,
    @Json(name = "overview")
    val overview: String?,
    @Json(name = "release_date")
    val releaseDate: String?,
    @Json(name = "adult")
    val adult: Boolean?,
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "genre_ids")
    val genreIds: List<Int>? = null,
    @Json(name = "vote_count")
    val voteCount: Int?,
    @Json(name = "original_language")
    val originalLanguage: String?,
    @Json(name = "original_title")
    val originalTitle: String?,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "id")
    val id: Long?,
    @Json(name = "video")
    val video: Boolean?,
    @Json(name = "popularity")
    val popularity: Double?,
    @Json(name = "media_type")
    val mediaType: String?,
    @Json(name = "first_air_date")
    val firstAirDate: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "original_name")
    val originalName: String?,
    val genres: List<Genres>?,
    val runtime : Long?

)

@JsonClass(generateAdapter = true)
data class Genres(
    val id: Long?,
    val name: String?

)