package com.example.moviemood.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.moviemood.databinding.ImageDetailBinding
import com.example.moviemood.model.CreditsLisResponse
import com.example.moviemood.utils.POSTER_BASE_URL
import javax.inject.Inject

class ImageAdapter @Inject constructor() : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
    private lateinit var binding: ImageDetailBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageAdapter.ViewHolder {
        binding = ImageDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ImageAdapter.ViewHolder, position: Int) {
        holder.setData(differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int=differ.currentList.size

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun setData(item: CreditsLisResponse.Cast) {
            binding.apply {
                tvName.text=item.name
                val moviePosterURL = POSTER_BASE_URL + item?.profilePath
                Glide.with(itemView)
                    .load(moviePosterURL
                    )
                    .fitCenter()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(itemImages)
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<CreditsLisResponse.Cast>() {
        override fun areItemsTheSame(oldItem: CreditsLisResponse.Cast, newItem: CreditsLisResponse.Cast): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CreditsLisResponse.Cast, newItem: CreditsLisResponse.Cast): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}