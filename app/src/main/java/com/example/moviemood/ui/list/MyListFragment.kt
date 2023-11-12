package com.example.moviemood.ui.list

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviemood.adapter.MyListAdapter
import com.example.moviemood.databinding.FragmentMyListBinding
import com.example.moviemood.utils.initRecycler
import com.example.moviemood.utils.showInvisible
import com.example.moviemood.viewmodel.DatabaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyListFragment : Fragment() {
    private var _binding: FragmentMyListBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var mylistAdapter: MyListAdapter

    private val databaseViewModel: DatabaseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding= FragmentMyListBinding.inflate(inflater,container,false)
        return binding.root

    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            databaseViewModel.loadFavoriteMovieList()
            databaseViewModel.favoriteMovieList.observe(viewLifecycleOwner){
                mylistAdapter.bind(it)
                favoriteRecycler.initRecycler(LinearLayoutManager(requireContext()),mylistAdapter)
            }

            mylistAdapter.setonItemClickListener {
              val action=MyListFragmentDirections.actionMyListFragmentToMovieDetailsFragment(it.id)
                findNavController().navigate(action)
            }
            databaseViewModel.emptyList.observe(viewLifecycleOwner){
                if (it) {
                    emptyItemsLay.showInvisible(true)
                    favoriteRecycler.showInvisible(false)
                } else {
                    emptyItemsLay.showInvisible(false)
                    favoriteRecycler.showInvisible(true)
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}