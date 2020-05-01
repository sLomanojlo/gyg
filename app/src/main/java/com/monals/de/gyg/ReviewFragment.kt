package com.monals.de.gyg

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.monals.de.gyg.databinding.FragmentReviewBinding
import com.monals.de.gyg.models.ReviewAdapter

enum class reviewStatus { LOADING, ERROR, DONE }

class ReviewFragment : Fragment() {

    private  val viewModel: ReviewViewModel by lazy {
        ViewModelProviders.of(this).get(ReviewViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val binding = FragmentReviewBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.reviewsGrid.adapter = ReviewAdapter(ReviewAdapter.OnClickListenerReview{})

        return binding.root
    }

}