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
import com.example.moviemood.db.MoviesEntity
import com.example.moviemood.utils.POSTER_BASE_URL
import javax.inject.Inject

class MyListAdapter @Inject constructor():RecyclerView.Adapter<MyListAdapter.ViewHolder>() {
    private lateinit var binding: CommonItemBinding
    private var moviesList = emptyList<MoviesEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyListAdapter.ViewHolder {
        binding = CommonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: MyListAdapter.ViewHolder, position: Int) {
        holder.setData(moviesList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int =moviesList.size



    inner class ViewHolder() : RecyclerView.ViewHolder(binding.root) {
        fun setData(item: MoviesEntity) {
            binding.apply {
                movieName.text = item.title
                rate.text = item.rate
                realeseDate.text = "Release Date: ${item.year}"
                val moviePosterURL = POSTER_BASE_URL + item.poster
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

    private var onItemClickListener : ((MoviesEntity) -> Unit)? = null
    fun setonItemClickListener(listener: (MoviesEntity) -> Unit) {
        onItemClickListener=listener
    }


    fun bind(data:List<MoviesEntity >){
        val moviesDiffUtils = MoviesDiffUtils(moviesList,data)
        val diffUtils = DiffUtil.calculateDiff(moviesDiffUtils)
        moviesList=data
        diffUtils.dispatchUpdatesTo(this)
    }

    //callback
    class MoviesDiffUtils(private val oldItem:List<MoviesEntity>, private val newItem:List<MoviesEntity>) : DiffUtil.Callback(){
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