package com.monals.de.gyg.util

import android.view.View
import com.monals.de.gyg.R
import kotlinx.android.synthetic.main.item_review.view.*

fun setUpRatingImages(itemView: View, rating: Int) {
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