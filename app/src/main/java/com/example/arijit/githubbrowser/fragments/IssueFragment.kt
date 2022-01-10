package com.example.arijit.githubbrowser.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.arijit.githubbrowser.adapters.IssueListAdapter
import com.example.arijit.githubbrowser.databinding.FragmentIssueBinding
import com.example.arijit.githubbrowser.fragments.detail.DetailViewModel


class IssueFragment : Fragment() {

    private var _binding: FragmentIssueBinding? = null
    private val binding: FragmentIssueBinding get() = _binding!!

    private val viewModel: DetailViewModel by activityViewModels {
        val activity = requireNotNull(this.activity)
        DetailViewModel.Factory(activity.application)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIssueBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = IssueListAdapter()
        viewModel.issueList.observe(viewLifecycleOwner) {
            if(it != null)
                (binding.recyclerView.adapter as IssueListAdapter).submitList(it)
        }
    }



}