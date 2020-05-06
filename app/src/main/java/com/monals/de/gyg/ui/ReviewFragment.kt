package com.monals.de.gyg.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.monals.de.gyg.R
import com.monals.de.gyg.adapters.ReviewListAdapter
import com.monals.de.gyg.databinding.FragmentReviewListBinding
import com.monals.de.gyg.models.Review
import com.monals.de.gyg.viewmodel.ReviewApiStatus
import com.monals.de.gyg.viewmodel.ReviewViewModel
import com.monals.de.gyg.viewmodel.obtainViewModel

const val DEFAULT_SHARED_PREFS = "default_shared_prefs"
const val REVIEW = "review"

class ReviewFragment : Fragment() {
    private lateinit var viewModel: ReviewViewModel
    private lateinit var reviewListAdapter: ReviewListAdapter
    private lateinit var sharedPrefs: SharedPreferences




    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        viewModel = obtainViewModel(ReviewViewModel::class.java)


        val binding = FragmentReviewListBinding.inflate(inflater)

        /** Allows Data Binding to Observe LiveData with the lifecycle of this Fragment */
        binding.lifecycleOwner = this

        /** Giving the binding access to the OverviewViewModel */
        binding.viewModel = viewModel

        reviewListAdapter = ReviewListAdapter ( {viewModel.retry()},
            ReviewListAdapter.OnClickListenerReview{
                savetoSharedPrefs(it)
                binding.recyclerView.findNavController().navigate(R.id.action_reviewListFragment_to_detailsFragment)})
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

    private fun savetoSharedPrefs(review: Review) {
        sharedPrefs = activity!!.getSharedPreferences(DEFAULT_SHARED_PREFS, Context.MODE_PRIVATE)
        val jsonString = GsonBuilder().create().toJson(review)
        sharedPrefs.edit().putString(REVIEW, jsonString).apply()
    }

}