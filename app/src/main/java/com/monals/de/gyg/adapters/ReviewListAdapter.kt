package com.monals.de.gyg.adapters

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.monals.de.gyg.viewmodel.ReviewApiStatus
import com.monals.de.gyg.models.Review

private const val DATA_VIEW_TYPE = 1
private const val FOOTER_VIEW_TYPE = 2

class ReviewListAdapter(private val retry: () -> Unit) :
    PagedListAdapter<Review, RecyclerView.ViewHolder>(ReviewDiffCallback) {

    private var status = ReviewApiStatus.LOADING


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == DATA_VIEW_TYPE) ReviewViewHolder.create(
            parent
        ) else ListFooterViewHolder.create(retry, parent)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA_VIEW_TYPE) {
            (holder as ReviewViewHolder).bind(getItem(position))
        } else (holder as ListFooterViewHolder).bind(status)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
    }


    companion object {
        val ReviewDiffCallback = object : DiffUtil.ItemCallback<Review>() {
            override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
                return oldItem == newItem
            }
        }
    }


    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter()) 1 else 0
    }

    private fun hasFooter(): Boolean {
        return super.getItemCount() != 0 && (status == ReviewApiStatus.LOADING || status == ReviewApiStatus.ERROR)
    }

    fun setState(status: ReviewApiStatus) {
        this.status = status
        notifyItemChanged(super.getItemCount())
    }

}