package com.example.moviemood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviemood.databinding.HomeGenreItemBinding
import com.example.moviemood.model.GenreListResponse
import javax.inject.Inject

class GenresAdapter @Inject constructor():RecyclerView.Adapter<GenresAdapter.ViewHolder>() {
    private lateinit var binding: HomeGenreItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresAdapter.ViewHolder {
        binding= HomeGenreItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int =differ.currentList.size

    inner class ViewHolder():RecyclerView.ViewHolder(binding.root){
        fun setData(item : GenreListResponse.Genre){
            binding.apply {
                nameTxt.text=item.name

                root.setOnClickListener {
                    onItemClickListener?.let {
                        it(item)
                    }
                }
            }
        }
    }

    private var onItemClickListener : ((GenreListResponse.Genre) -> Unit)? = null
    fun setonItemClickListener(listener: (GenreListResponse.Genre) -> Unit) {
        onItemClickListener=listener
    }

    private val diffCallback = object : DiffUtil.ItemCallback<GenreListResponse.Genre>(){
        override fun areItemsTheSame(oldItem: GenreListResponse.Genre, newItem: GenreListResponse.Genre): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GenreListResponse.Genre, newItem: GenreListResponse.Genre): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,diffCallback)

}