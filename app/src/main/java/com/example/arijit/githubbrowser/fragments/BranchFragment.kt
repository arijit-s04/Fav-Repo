package com.example.arijit.githubbrowser.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.arijit.githubbrowser.adapters.BranchListAdapter
import com.example.arijit.githubbrowser.databinding.FragmentBranchListBinding
import com.example.arijit.githubbrowser.fragments.detail.DetailViewModel
import com.example.arijit.githubbrowser.fragments.detail.RepoDetailFragmentDirections

class BranchFragment: Fragment() {

    private var _binding: FragmentBranchListBinding? = null
    private val binding: FragmentBranchListBinding get() = _binding!!

    private val viewModel: DetailViewModel by activityViewModels {
        val activity = requireNotNull(this.activity)
        DetailViewModel.Factory(activity.application)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBranchListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = BranchListAdapter {
            findNavController().navigate(
                RepoDetailFragmentDirections.actionSecondFragmentToCommitFragment(it)
            )
            viewModel.navigateToCommit(it)
        }
        binding.recyclerView.adapter = adapter
        viewModel.branchList.observe(viewLifecycleOwner) {
            if(it != null) {
                (binding.recyclerView.adapter as BranchListAdapter).submitList(it)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}