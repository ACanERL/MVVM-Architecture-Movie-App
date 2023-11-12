package com.example.moviemood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.moviemood.databinding.CommonItemBinding
import com.example.moviemood.model.CommonMoviesListResponse
import com.example.moviemood.utils.POSTER_BASE_URL
import com.example.moviemood.utils.roundToTwoDecimals
import javax.inject.Inject

class CommonMoviesAdapter @Inject constructor():RecyclerView.Adapter<CommonMoviesAdapter.ViewHolder>() {
    private lateinit var binding: CommonItemBinding
    private var moviesList = emptyList<CommonMoviesListResponse.Result>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommonMoviesAdapter.ViewHolder {
        binding = CommonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: CommonMoviesAdapter.ViewHolder, position: Int) {
        holder.setData(moviesList[position])
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

    override fun getItemCount(): Int{
        return if(showAll){
            moviesList.size
        }else{
            minOf(moviesList.size, 5)
        }
    }

    inner  class ViewHolder():RecyclerView.ViewHolder(binding.root){
        fun setData(item: CommonMoviesListResponse.Result) {
            binding.apply {
                val moviePosterURL = POSTER_BASE_URL + item?.posterPath
                movieName.text=item.originalTitle
                rate.text=item.voteAverage.roundToTwoDecimals()
                realeseDate.text=item.releaseDate
                Glide.with(itemView)
                    .load(moviePosterURL
                    )
                    .fitCenter()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(commonImage)

                root.setOnClickListener {
                    onItemClickListener?.let {
                        it(item)
                    }
                }
            }
        }
    }

    private var onItemClickListener : ((CommonMoviesListResponse.Result) -> Unit)? = null
    fun setonItemClickListener(listener: (CommonMoviesListResponse.Result) -> Unit) {
        onItemClickListener=listener
    }

    fun bind(data:List<CommonMoviesListResponse.Result>){
        val moviesDiffUtils = MoviesDiffUtils(moviesList,data)
        val diffUtils = DiffUtil.calculateDiff(moviesDiffUtils)
        moviesList=data
        diffUtils.dispatchUpdatesTo(this)
    }


    //callback
    class MoviesDiffUtils(private val oldItem:List<CommonMoviesListResponse.Result>, private val newItem:List<CommonMoviesListResponse.Result>) : DiffUtil.Callback(){
        override fun getOldListSize(): Int {
            return oldItem.size
        }

        override fun getNewListSize(): Int {
            return newItem.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            // === data type is compred here
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }

    }
}