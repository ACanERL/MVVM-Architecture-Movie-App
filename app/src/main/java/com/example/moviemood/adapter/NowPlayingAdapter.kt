package com.example.moviemood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.example.moviemood.databinding.CommonItemBinding
import com.example.moviemood.databinding.NowPlayingItemBinding
import com.example.moviemood.model.CommonMoviesListResponse
import com.example.moviemood.model.GenreListResponse
import com.example.moviemood.model.MovieResponse
import com.example.moviemood.model.Result
import com.example.moviemood.utils.POSTER_BASE_URL
import javax.inject.Inject

class NowPlayingAdapter @Inject constructor():RecyclerView.Adapter<NowPlayingAdapter.ViewHolder>() {
    private lateinit var binding: NowPlayingItemBinding
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NowPlayingAdapter.ViewHolder {
        binding = NowPlayingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: NowPlayingAdapter.ViewHolder, position: Int) {
       holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int=differ.currentList.size

    inner  class ViewHolder():RecyclerView.ViewHolder(binding.root){
        fun bind(item:Result){
            val moviePosterURL = POSTER_BASE_URL + item?.poster_path
           binding.nowPlayinImage.load(moviePosterURL) {
                crossfade(true)
                crossfade(800)
                scale(Scale.FIT)
            }
            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(item)
                }
            }
        }

    }


    private var onItemClickListener : ((Result) -> Unit)? = null
    fun setonItemClickListener(listener: (Result) -> Unit) {
        onItemClickListener=listener
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem:Result, newItem: Result): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,diffCallback)
}