package com.example.arijit.githubbrowser.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.arijit.githubbrowser.adapters.CommitListAdapter
import com.example.arijit.githubbrowser.databinding.FragmentCommitBinding
import com.example.arijit.githubbrowser.fragments.detail.DetailViewModel
import com.google.android.material.snackbar.Snackbar

class CommitFragment : Fragment() {

    private lateinit var binding: FragmentCommitBinding
    private lateinit var branch: String

    private val viewModel: DetailViewModel by activityViewModels {
        val activity = requireNotNull(this.activity)
        DetailViewModel.Factory(activity.application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    viewModel.doneNavigateToCommit()
                    findNavController().navigateUp()
                }
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        branch = CommitFragmentArgs.fromBundle(requireArguments()).branchName
        // Inflate the layout for this fragment
        binding = FragmentCommitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.subtitle = branch
        binding.recyclerView.adapter = CommitListAdapter()
        viewModel.commitList.observe(viewLifecycleOwner) {
            if(it != null)
                (binding.recyclerView.adapter as CommitListAdapter).submitList(it)
        }

        viewModel.message.observe(viewLifecycleOwner) {
            if(it != null) {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        (requireActivity() as AppCompatActivity).supportActionBar?.subtitle = ""
    }
}
