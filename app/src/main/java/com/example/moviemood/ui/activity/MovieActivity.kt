package com.example.moviemood.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.moviemood.R
import com.example.moviemood.adapter.CommonMoviesAdapter
import com.example.moviemood.adapter.GenresAdapter
import com.example.moviemood.adapter.NowPlayingAdapter
import com.example.moviemood.databinding.ActivityMovieBinding
import com.example.moviemood.viewmodel.ApiViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieActivity : AppCompatActivity() {
    private var _binding: ActivityMovieBinding? = null
    private val binding get() = _binding
    private val viewModel: ApiViewModel by viewModels()
    @Inject
    lateinit var genreMoviesAdapter: GenresAdapter
    @Inject
    lateinit var commonMoviesAdapter: CommonMoviesAdapter
    @Inject
    lateinit var nowPlayingAdapter: NowPlayingAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding.apply {
            val navView: BottomNavigationView = binding!!.navView
            val navController = findNavController(R.id.nav_host_fragment_activity_main)
            navView.setupWithNavController(navController)
        }

       /* binding.apply {
            binding!!.animationLoading.showInvisible(false)
            binding!!.animationLoading.pauseAnimation()

            lifecycleScope.launch {
                genresList()
                commonList()
                nowPlayingList()
                viewModel.loadGenreMoviesList("action","Bearer ${Token}")

                genreMoviesAdapter.setonItemClickListener {
                    viewModel.loadGenreMoviesList(it.id.toString(),"Bearer ${Token}")
                    commonList()
                }

                viewModel.loading.observe(this@MovieActivity, Observer {
                    if (it) {
                        binding!!.animationLoading.showInvisible(true)
                        binding!!.animationLoading.playAnimation()

                    } else {
                        binding!!.animationLoading.showInvisible(false)
                        binding!!.animationLoading.pauseAnimation()
                    }
                })
            }
        }*/
    }
   /* private fun commonList(){
        viewModel.genreMoviesList.observe(this@MovieActivity, Observer {
            commonMoviesAdapter.bind(it.results)
            binding!!.genreListMovieRecycler.initRecycler(
                LinearLayoutManager(this@MovieActivity,LinearLayoutManager.HORIZONTAL,false), commonMoviesAdapter
            )
        })
    }
    private fun nowPlayingList(){
        nowPlayingAdapter.notifyDataSetChanged()
        viewModel.getNowPlaying(1,"Bearer ${Token}")
        viewModel.nowPlayingList.observe(this@MovieActivity, Observer {
            nowPlayingAdapter.differ.submitList(it.results)
            binding!!.nowPlayRecycler.initRecycler(
                LinearLayoutManager(this@MovieActivity,LinearLayoutManager.HORIZONTAL,false), nowPlayingAdapter
            )
        })
    }

    private fun genresList(){
        viewModel.loadGenreList("Bearer ${Token}")
        viewModel.genreList.observe(this@MovieActivity, Observer {
            genreMoviesAdapter.differ.submitList(it.genres)
            binding!!.genresRecyclerview.initRecycler(
                LinearLayoutManager(this@MovieActivity, LinearLayoutManager.HORIZONTAL, false), genreMoviesAdapter
            )
        })
    }*/
}