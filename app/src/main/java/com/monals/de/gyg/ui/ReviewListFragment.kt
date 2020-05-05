package com.monals.de.gyg.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.monals.de.gyg.adapters.ReviewListAdapter
import com.monals.de.gyg.databinding.FragmentReviewListBinding
import com.monals.de.gyg.viewmodel.ReviewApiStatus
import com.monals.de.gyg.viewmodel.ReviewViewModel


class ReviewListFragment : Fragment() {
    private lateinit var viewModel: ReviewViewModel
    private lateinit var reviewListAdapter: ReviewListAdapter



    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        viewModel =  ViewModelProvider(this).get(ReviewViewModel::class.java)


        val binding = FragmentReviewListBinding.inflate(inflater)

        /** Allows Data Binding to Observe LiveData with the lifecycle of this Fragment */
        binding.lifecycleOwner = this

        /** Giving the binding access to the OverviewViewModel */
        binding.viewModel = viewModel

        reviewListAdapter = ReviewListAdapter { viewModel.retry() }
        binding.recyclerView.adapter = reviewListAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        binding.clError.setOnClickListener { viewModel.retry() }


        viewModel.getState().observe(viewLifecycleOwner, androidx.lifecycle.Observer { state ->
            if (!viewModel.isListEmpty()) {
                reviewListAdapter.setState(state ?: ReviewApiStatus.DONE)
            }
        })


        return binding.root

    }

}