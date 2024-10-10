package com.dicoding.movieexpert.ui.detail

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.dicoding.core.data.source.Resource
import com.dicoding.core.domain.model.Movie
import com.dicoding.movieexpert.R
import com.dicoding.movieexpert.databinding.ActivityDetailMovieBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailMovieBinding
    private val args: DetailMovieActivityArgs by navArgs()
    private val detailMovieViewModel: DetailMovieViewModel by viewModels()
    private var statusFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val movieId: Int = args.movieId

        detailMovieViewModel.getDetailPopularMovie(movieId).observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBarDetail.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    binding.progressBarDetail.visibility = View.GONE
                    resource.data?.let { setData(it) }
                }

                is Resource.Error -> TODO()
            }
        }

        binding.iconBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setData(movie: Movie) {
        binding.apply {
            ivBackgroundDetail.loadImage(this@DetailMovieActivity, movie.fullDropPath)
            ivPosterDetail.loadImage(this@DetailMovieActivity, movie.fullPosterPath)
            tvTitleDetail.text = movie.title
            tvOverviewDetail.text = movie.overview
            tvReleaseDateDetail.text = movie.releaseDate
            tvLanguageDetail.text = movie.originalLanguage
            tvPopularityDetail.text = String.format("%s",movie.popularity.toString())
            tvVoteAverage.text = String.format("%s", movie.voteAverage.toString())

            detailMovieViewModel.getPopularMovieById(movie.movieId)
                .observe(this@DetailMovieActivity) { movieEntity ->
                    statusFavorite = movieEntity.isFavorite
                    setStatusFavorite(statusFavorite)
                }

            fabFavorite.setOnClickListener {
                statusFavorite = !statusFavorite
                detailMovieViewModel.setFavoriteMovie(movie, statusFavorite)
                setStatusFavorite(statusFavorite)
            }
        }
    }

    private fun ImageView.loadImage(context: Context, url: String) {
        Glide.with(context)
            .load(url)
            .into(this)
    }

    private fun setStatusFavorite(isFavorite: Boolean) {
        val favoriteIcon = if (isFavorite) {
            R.drawable.ic_favorite
        } else {
            R.drawable.ic_favoriteborder_black
        }
        binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(this, favoriteIcon))
        binding.fabFavorite.imageTintList =
            ContextCompat.getColorStateList(this, android.R.color.holo_red_dark)
    }
}


