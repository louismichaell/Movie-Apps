package com.dicoding.favorite

import androidx.fragment.app.Fragment
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.core.domain.model.Movie
import com.dicoding.core.presentation.MovieAdapter
import com.dicoding.favorite.databinding.FragmentFavoriteBinding
import com.dicoding.favorite.di.DaggerFavoriteComponent
import com.dicoding.favorite.viewmodel.FavoriteMovieViewModel
import com.dicoding.favorite.viewmodel.FavoriteMovieViewModelFactory
import com.dicoding.movieexpert.di.FavoriteModuleDependency

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var factory: FavoriteMovieViewModelFactory
    private val favoriteViewModel: FavoriteMovieViewModel by viewModels {
        factory
    }

    override fun onAttach(context: Context) {
        DaggerFavoriteComponent.builder()
            .context(requireActivity())
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    requireActivity().applicationContext,
                    FavoriteModuleDependency::class.java
                )
            )
            .build()
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvMovie.layoutManager = layoutManager
        binding.rvMovie.setHasFixedSize(true)

        favoriteViewModel.getFavoriteUpComingMovie().observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                hideFavoriteList(true)
            } else {
                hideFavoriteList(false)
                setAdapter(it)
            }
        }
    }

    private fun hideFavoriteList(isShow: Boolean) {
        binding.apply {
            rvMovie.isVisible = !isShow
            ivEmpty.isVisible = isShow
            tvEmpty.isVisible = isShow
        }
    }

    private fun setAdapter(it: List<Movie>) {
        val adapter = MovieAdapter(it)
        adapter.setOnItemClickCallback(object : MovieAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Movie) {
                val action = FavoriteFragmentDirections.actionNavigationFavoriteToDetailActivity(
                    data.movieId
                )
                findNavController().navigate(action)
            }
        })
        binding.rvMovie.adapter = adapter
    }
}