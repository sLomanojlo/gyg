package com.monals.de.gyg.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.monals.de.gyg.R
import com.monals.de.gyg.models.Review
import kotlinx.android.synthetic.main.item_review.view.*

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
