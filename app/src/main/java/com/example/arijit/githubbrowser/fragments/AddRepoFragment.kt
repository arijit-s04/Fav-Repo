package com.example.arijit.githubbrowser.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.arijit.githubbrowser.databinding.FragmentAddRepoBinding
import com.example.arijit.githubbrowser.fragments.repolist.RepoListViewModel
import com.google.android.material.snackbar.Snackbar

class AddRepoFragment : Fragment() {

    private lateinit var binding: FragmentAddRepoBinding

    private val viewModel: RepoListViewModel by activityViewModels {
        val activity = requireNotNull(this.activity)
        RepoListViewModel.Factory(activity.application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddRepoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAdd.setOnClickListener {
            val owner = binding.etOwner.text.toString()
            val repoName = binding.etRepo.text.toString()
            if(viewModel.checkOkForm(
                owner, repoName
            )){
		        binding.progressBar.visibility = View.VISIBLE
                viewModel.addNewRepo(owner, repoName)
		    }
        }
        viewModel.message.observe(viewLifecycleOwner) {
            if(it != null) {
                binding.progressBar.visibility = View.GONE
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
            }
        }
        viewModel.doneAdding.observe(viewLifecycleOwner) {
            if(it != null && it )
                findNavController().navigateUp()
        }
    }

}
