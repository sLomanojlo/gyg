package com.monals.de.gyg.models

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.monals.de.gyg.databinding.ReviewItemBinding

class ReviewAdapter (val onClickListener: OnClickListenerReview) :
        ListAdapter<Review, ReviewAdapter.ReviewViewHolder>(DiffCallback) {

    class ReviewViewHolder(private var binding: ReviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(review: Review) {
            binding.review = review
            binding.executePendingBindings()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewAdapter.ReviewViewHolder {
        return ReviewViewHolder(
            ReviewItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: ReviewAdapter.ReviewViewHolder, position: Int) {
        val review = getItem(position)
        holder.itemView.setOnClickListener{
            onClickListener.onClick(review)
        }

        holder.bind(review)
    }


    companion object DiffCallback : DiffUtil.ItemCallback<Review>() {

        override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class OnClickListenerReview(val clickListener: (review: Review) -> Unit){
        fun onClick(review: Review) = clickListener(review)
    }
}