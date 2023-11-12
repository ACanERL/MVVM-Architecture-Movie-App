package com.example.moviemood.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviemood.adapter.CommonMoviesAdapter
import com.example.moviemood.adapter.GenresAdapter
import com.example.moviemood.adapter.TrendingAdapter
import com.example.moviemood.databinding.FragmentHomeBinding
import com.example.moviemood.utils.Token
import com.example.moviemood.utils.initRecycler
import com.example.moviemood.utils.showInvisible
import com.example.moviemood.viewmodel.ApiViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val apiViewModel:ApiViewModel by viewModels()
    @Inject
    lateinit var trendingAdapter: TrendingAdapter
    @Inject
    lateinit var genreMoviesAdapter: GenresAdapter
    @Inject
    lateinit var commonMoviesAdapter: CommonMoviesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentHomeBinding.inflate(inflater,container,false)
        apiViewModel.loadGenreMoviesList("action","Bearer ${Token}")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleScope.launch {
                trendingList()
                genresList()


                seeAllTrend.setOnClickListener {
                    if(trendingAdapter.isShowAll()){
                        seeAllTrend.text="See All"
                        trendingAdapter.reset()
                    }else{
                        seeAllTrend.text="Close"
                        trendingAdapter.showAll()
                    }
                }

                seeAllMovies.setOnClickListener {
                    if (commonMoviesAdapter.isShowAll()) {
                        seeAllMovies.text="See All"
                        commonMoviesAdapter.reset()
                    } else {
                        seeAllMovies.text="Close"
                        commonMoviesAdapter.showAll()
                    }
                }
                genreMoviesAdapter.setonItemClickListener {
                    apiViewModel.loadGenreMoviesList(it.id.toString(),"Bearer ${Token}")
                    genresMovieList()
                }

                apiViewModel.loading.observe(viewLifecycleOwner, Observer {
                    if (it) {
                        animationLoading.showInvisible(true)
                        animationLoading.playAnimation()


                    } else {

                        animationLoading.showInvisible(false)
                        animationLoading.pauseAnimation()
                    }
                })

                findMovieBtn.setOnClickListener {
                    val action=HomeFragmentDirections.actionHomeFragmentToFindMovieFragment()
                    findNavController().navigate(action)
                }

                commonMoviesAdapter.setonItemClickListener {
                    val action=HomeFragmentDirections.actionHomeFragmentToMovieDetailsFragment(it.id!!)
                    findNavController().navigate(action)
                }
                trendingAdapter.setonItemClickListener {
                    val action=HomeFragmentDirections.actionHomeFragmentToMovieDetailsFragment(it.id!!)
                    findNavController().navigate(action)
                }

            }
        }
    }

    private fun trendingList(){
      apiViewModel.getTrending("day","Bearer ${Token}")
        apiViewModel.trendingList.observe(viewLifecycleOwner, Observer {
            trendingAdapter.differ.submitList(it.results)
            binding.trendingRecycler.initRecycler(
                LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false), trendingAdapter
            )
        })
    }
    private fun genresList(){
        apiViewModel.loadGenreList("Bearer ${Token}")
        apiViewModel.genreList.observe(viewLifecycleOwner, Observer {
            genreMoviesAdapter.differ.submitList(it.genres)
            binding!!.genresRecycler.initRecycler(
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false), genreMoviesAdapter
            )
        })
    }
    private fun genresMovieList(){
        apiViewModel.genreMoviesList.observe(viewLifecycleOwner, Observer {
            commonMoviesAdapter.bind(it.results)
            binding!!.genresMovieRecycler.initRecycler(
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false
                    ), commonMoviesAdapter
            )
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun onResume() {
        super.onResume()
        genresList()
        genresMovieList()
        trendingList()
    }
}