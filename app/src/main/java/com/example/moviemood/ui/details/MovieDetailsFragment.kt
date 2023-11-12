package com.example.moviemood.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.moviemood.R
import com.example.moviemood.adapter.ImageAdapter
import com.example.moviemood.databinding.FragmentMovieDetailsBinding
import com.example.moviemood.db.MoviesEntity
import com.example.moviemood.utils.POSTER_BASE_URL
import com.example.moviemood.utils.Token
import com.example.moviemood.utils.initRecycler
import com.example.moviemood.utils.roundToTwoDecimals
import com.example.moviemood.utils.showInvisible
import com.example.moviemood.viewmodel.DetailsMovieModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!
    private var movieId = 0
    private val detailsViewModel: DetailsMovieModel by viewModels()
    private val args: MovieDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var entity: MoviesEntity

    @Inject
    lateinit var imagesAdapter: ImageAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentMovieDetailsBinding.inflate(inflater,container,false)
        movieId = args.id

        if (movieId > 0) {
            detailsViewModel.loadDetailsMovie(movieId,"Bearer $Token")
            detailsViewModel.loadCreditsMovie(movieId,"Bearer $Token")
            detailsViewModel.loadMovieVideo(movieId,"Bearer $Token")
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleScope.launch {
                detailsViewModel.detailsMovie.observe(viewLifecycleOwner, Observer {response->
                    val moviePosterURL = POSTER_BASE_URL + response.posterPath

                    Glide.with(this@MovieDetailsFragment)
                        .load(moviePosterURL
                        )
                        .fitCenter()
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(movieImageDetail)



                    movieNametxt.text = response.title
                    avergeRate.text = response.voteAverage.roundToTwoDecimals()
                    overview.text = response.overview

                    binding.addFav.setOnClickListener {
                        entity.id = movieId
                        entity.poster = response.posterPath
                        entity.lang = response.originalLanguage
                        entity.title = response.title
                        entity.rate = response.voteAverage.toString()
                        entity.year = response.releaseDate
                        detailsViewModel.favoriteMovie(movieId, entity)
                    }


                })

                detailsViewModel.creditsMovie.observe(viewLifecycleOwner, Observer {
                    imagesAdapter.differ.submitList(it.cast)
                    creditsRecycler.initRecycler(
                        LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false), imagesAdapter
                    )
                })


                detailsViewModel.loading.observe(viewLifecycleOwner) {
                    if (it) {
                        detailLoading.showInvisible(true)
                        detailScrollView.showInvisible(false)

                    } else {
                        detailLoading.showInvisible(false)
                        detailScrollView.showInvisible(true)
                    }
                }

                lifecycleScope.launchWhenCreated {
                    if (detailsViewModel.existMovie(movieId)) {
                        addFav.setColorFilter(ContextCompat.getColor(requireContext(), R.color.scarlet))
                    } else {
                        addFav.setColorFilter(ContextCompat.getColor(requireContext(), R.color.philippineSilver))
                    }
                }

                detailsViewModel.isFavorite.observe(viewLifecycleOwner) {
                    if (it) {
                        addFav.setColorFilter(ContextCompat.getColor(requireContext(), R.color.scarlet))
                    } else {
                        addFav.setColorFilter(ContextCompat.getColor(requireContext(), R.color.philippineSilver))
                    }
                }
            }
        }



        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().navigate(R.id.homeFragment)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}