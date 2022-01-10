package com.example.arijit.githubbrowser.fragments.repolist

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.arijit.githubbrowser.R
import com.example.arijit.githubbrowser.adapters.RepoListAdapter
import com.example.arijit.githubbrowser.databinding.FragmentRepoListBinding
import com.example.arijit.githubbrowser.models.GithubRepoData

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class RepoListFragment : Fragment() {

    private var _binding: FragmentRepoListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    private val viewModel: RepoListViewModel by activityViewModels {
        val activity = requireNotNull(this.activity)
        RepoListViewModel.Factory(activity.application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRepoListBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.apply {
            adapter = RepoListAdapter(RepoListAdapter.ClickListener {
                findNavController().navigate(
                    RepoListFragmentDirections.actionFirstFragmentToSecondFragment(it)
                )
            },
                RepoListAdapter.ClickListener {
                    shareRepoDetails(it)
                }
            )
        }

        binding.btnHint.setOnClickListener {
            addRepo()
        }

        viewModel.repoList.observe(viewLifecycleOwner) {
            if(it != null) {
                if(it.isNotEmpty())
                    binding.hintContainer.visibility = View.GONE
                else
                    binding.hintContainer.visibility = View.VISIBLE
                val recAda =  binding.recyclerView.adapter as RepoListAdapter
                recAda.submitList(it)
            }
        }
    }

    private fun createBody(repo: GithubRepoData): String {
        return requireContext().getString(
            R.string.share_bodY,
            repo.name, repo.description ?: "null", repo.url
        )
    }

    private fun shareRepoDetails(repo: GithubRepoData) {
        val intent = Intent(Intent.ACTION_SEND)
        val textBody = createBody(repo)
        val textTitle = requireContext().getString(R.string.share_title)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, textTitle)
        intent.putExtra(Intent.EXTRA_TEXT, textBody)
        startActivity(intent)
    }

    private fun addRepo() {
        findNavController().navigate(R.id.action_FirstFragment_to_addRepoFragment)
        viewModel.navigateToAddFrag()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_list_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_add -> {
                addRepo()
                true
            }
            else -> false
        }
    }

}