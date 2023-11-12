package com.example.moviemood.ui.find

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviemood.adapter.CommonMoviesAdapter
import com.example.moviemood.databinding.FragmentFindMovieBinding
import com.example.moviemood.utils.Token
import com.example.moviemood.utils.initRecycler
import com.example.moviemood.utils.showInvisible
import com.example.moviemood.viewmodel.ApiViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FindMovieFragment : Fragment() {
    private var _binding: FragmentFindMovieBinding? = null
    private val binding get() = _binding!!
    private val apiViewModel: ApiViewModel by viewModels()

    @Inject
    lateinit var commonMoviesAdapter:CommonMoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding= FragmentFindMovieBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            binding.findRecycler.initRecycler(
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false),
                commonMoviesAdapter
            )
            findRecycler.setHasFixedSize(true)

            editTextSearch.addTextChangedListener {
                var query=it.toString()
                if(query.isNotEmpty()){
                    apiViewModel.getSearch(query,"Bearer ${Token}")
                }
                else{
                    commonMoviesAdapter.bind(emptyList())
                    commonMoviesAdapter.notifyDataSetChanged()
                }
            }
            apiViewModel.searchList.observe(viewLifecycleOwner, Observer {
                commonMoviesAdapter.bind(it.results)
                commonMoviesAdapter.notifyDataSetChanged()
            })

            apiViewModel.loading.observe(viewLifecycleOwner) {
                findRecycler.showInvisible(!it)
            }

            apiViewModel.emptyList.observe(viewLifecycleOwner) {
                if (it) {
                    emptyItemsLay.showInvisible(true)
                    findRecycler.showInvisible(false)
                } else {
                    emptyItemsLay.showInvisible(false)
                    findRecycler.showInvisible(true)
                }
            }

            commonMoviesAdapter.setonItemClickListener {
                val action=FindMovieFragmentDirections.actionFindMovieFragmentToMovieDetailsFragment(it.id)
                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}