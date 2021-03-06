package com.monals.de.gyg.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.monals.de.gyg.R
import com.monals.de.gyg.models.Review
import com.monals.de.gyg.util.convertDate
import com.monals.de.gyg.util.setUpRatingImages
import com.monals.de.gyg.viewmodel.ReviewApiStatus
import kotlinx.android.synthetic.main.item_list_footer.view.*
import kotlinx.android.synthetic.main.item_review.view.*

private const val DATA_VIEW_TYPE = 1
private const val FOOTER_VIEW_TYPE = 2


/**RecyclerView adapter for populating the list of reviews.*/
class ReviewListAdapter(private val retry: () -> Unit, private val onClickListener: OnClickListenerReview) :
    PagedListAdapter<Review, RecyclerView.ViewHolder>(ReviewDiffCallback) {

    private var status = ReviewApiStatus.LOADING

    /**ReviewViewHolder in charge of binding and displaying Review data*/
    class ReviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(review: Review) {
                itemView.twMessage.text = review.message
                itemView.twAuthor.text = String.format("reviewed by\n${review.author.fullName}")
                itemView.twId.text = review.id.toString()

                Glide.with(itemView)
                    .load(review.author.photo ?: R.drawable.ic_face_black)
                    .apply(RequestOptions()
                        .circleCrop()
                        .placeholder(R.drawable.loading_img)
                        .error(R.drawable.ic_face_black))
                    .into(itemView.iwProfile)

                itemView.twTime.text = convertDate(review.created)
                setUpRatingImages(itemView, review.rating)
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

        fun bind(status: ReviewApiStatus) {
            itemView.progress_bar.visibility = if (status == ReviewApiStatus.LOADING) View.VISIBLE else View.INVISIBLE
            itemView.iwError.visibility = if (status == ReviewApiStatus.ERROR) View.VISIBLE else View.INVISIBLE
        }

        companion object {
            fun create(retry: () -> Unit, parent: ViewGroup): ListFooterViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_list_footer, parent, false)
                view.iwError.setOnClickListener { retry() }
                return ListFooterViewHolder(view)
            }
        }
    }

    /**In charge of dispatching the right ViewHolder type*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == DATA_VIEW_TYPE) ReviewViewHolder.create(parent)
        else ListFooterViewHolder.create(retry, parent)
    }

    /**Binding ViewHolders. Setting up onClickListener to the ReviewViewHolder.*/
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA_VIEW_TYPE) {
            getItem(position)?.let { (holder as ReviewViewHolder).bind(it) }
            holder.itemView.setOnClickListener{onClickListener.onClick(getItem(position)!!)}
        } else (holder as ListFooterViewHolder).bind(status)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
    }


    /**DiffUtil in charge of efficiently handling modifications in the list.*/
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

    /**Custom listener that handles clicks on [RecyclerView] items.  Passes the [Review]
     * associated with the current item to the [onClick] function.*/
    class OnClickListenerReview(val clickListener: (review: Review) -> Unit) {
        fun onClick(review: Review) = clickListener(review)
    }

}