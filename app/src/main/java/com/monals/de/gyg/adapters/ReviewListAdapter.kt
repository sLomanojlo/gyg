package com.monals.de.gyg.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.monals.de.gyg.R
import com.monals.de.gyg.viewmodel.ReviewApiStatus
import com.monals.de.gyg.models.Review
import kotlinx.android.synthetic.main.item_list_footer.view.*
import kotlinx.android.synthetic.main.item_review.view.*

private const val DATA_VIEW_TYPE = 1
private const val FOOTER_VIEW_TYPE = 2

class ReviewListAdapter(private val retry: () -> Unit) :
    PagedListAdapter<Review, RecyclerView.ViewHolder>(ReviewDiffCallback) {

    private var status = ReviewApiStatus.LOADING

    /**ReviewViewHolder*/
    class ReviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(review: Review?) {
            if (review != null) {
                itemView.twAuthor.text = review.author.fullName
                itemView.twMessage.text = review.message
                itemView.twId.text = review.id.toString()

                setUpImages(itemView, review.rating)
            }
        }


        companion object {
            fun create(parent: ViewGroup): ReviewViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_review, parent, false)
                return ReviewViewHolder(view)
            }
        }
    }

    /**ListFooterViewHolder*/
    class ListFooterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(status: ReviewApiStatus?) {
            itemView.progress_bar.visibility = if (status == ReviewApiStatus.LOADING) View.VISIBLE else View.INVISIBLE
            itemView.txt_error.visibility = if (status == ReviewApiStatus.ERROR) View.VISIBLE else View.INVISIBLE
        }

        companion object {
            fun create(retry: () -> Unit, parent: ViewGroup): ListFooterViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_list_footer, parent, false)
                view.txt_error.setOnClickListener { retry() }
                return ListFooterViewHolder(view)
            }
        }
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == DATA_VIEW_TYPE) ReviewViewHolder.create(parent)
        else ListFooterViewHolder.create(retry, parent)
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

private fun setUpImages(itemView: View, rating: Int) {
    arrayListOf(
        itemView.iwStar1,
        itemView.iwStar2,
        itemView.iwStar3,
        itemView.iwStar4,
        itemView.iwStar5
    ).forEach{ imgView ->
        imgView.setImageResource(if (imgView.tag.toString().toInt() <= rating)
            R.drawable.ic_star_black else R.drawable.ic_star_border_black
        )
    }
}