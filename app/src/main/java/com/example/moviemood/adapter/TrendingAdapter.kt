package com.example.moviemood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.DifferCallback
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.AsyncListUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.moviemood.databinding.NowPlayingItemBinding
import com.example.moviemood.databinding.TrendingItemBinding
import com.example.moviemood.model.CommonMoviesListResponse
import com.example.moviemood.model.Result
import com.example.moviemood.utils.POSTER_BASE_URL
import com.example.moviemood.utils.POSTER_BASE_URL_2
import javax.inject.Inject

class TrendingAdapter @Inject constructor():RecyclerView.Adapter<TrendingAdapter.ViewHolder>() {
    private lateinit var binding: TrendingItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingAdapter.ViewHolder {
        binding = TrendingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: TrendingAdapter.ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    fun isShowAll(): Boolean {
        return showAll
    }

    private var showAll = false

    fun showAll() {
        showAll = true
        notifyDataSetChanged()
    }

    fun reset() {
        showAll = false
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if(showAll){
            differ.currentList.size
        }else{
            minOf(differ.currentList.size,5)
        }
    }

    inner class ViewHolder():RecyclerView.ViewHolder(binding.root){
        fun bind(item:Result){
            binding.apply {
                val moviePosterURL = POSTER_BASE_URL_2 + item?.poster_path


                Glide.with(itemView)
                    .load(moviePosterURL
                    )
                    .fitCenter()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(trendingImage)

                root.setOnClickListener {
                    onItemClickListener?.let {
                        it(item)
                    }
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