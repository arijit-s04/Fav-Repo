package com.example.arijit.githubbrowser.fragments.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.arijit.githubbrowser.R
import com.example.arijit.githubbrowser.adapters.DetailViewPagerAdapter
import com.example.arijit.githubbrowser.adapters.TAB_TITLES
import com.example.arijit.githubbrowser.databinding.FragmentRepoDetailBinding
import com.example.arijit.githubbrowser.models.GithubRepoData
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class RepoDetailFragment : Fragment() {

    private var _binding: FragmentRepoDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var repo: GithubRepoData

    private val viewModel: DetailViewModel by activityViewModels {
        val activity = requireNotNull(this.activity)
        DetailViewModel.Factory(activity.application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    viewModel.setCurrentRepo(null)
                }
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        repo = RepoDetailFragmentArgs.fromBundle(requireArguments()).repo
        setHasOptionsMenu(true)
        _binding = FragmentRepoDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            val pagerAdapter = DetailViewPagerAdapter(
                childFragmentManager
            )
            detailViewPager.adapter = pagerAdapter
            detailTabLayout.setupWithViewPager(detailViewPager)
        }

        viewModel.currentRepo.observe(viewLifecycleOwner) {
            if(it != null){
                binding.apply {
                    tvRepoName.text = repo.name
                    tvRepoDesc.text = repo.description
                }
            }
            else
                findNavController().navigateUp()
        }

        viewModel.message.observe(viewLifecycleOwner) {
            if(it != null) {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
            }
        }

        viewModel.issueList.observe(viewLifecycleOwner) {
            if(it != null) {
                binding.detailTabLayout
                    .getTabAt(1)?.text = "${TAB_TITLES[1]}(${it.size})"
            }
        }

        viewModel.setCurrentRepo(repo)

    }

    private fun viewInBrowser() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = (Uri.parse(viewModel.currentRepo.value?.url))
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_details_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_delete -> {
                viewModel.deleteRepo()
                true
            }
            R.id.action_view -> {
                viewInBrowser()
                true
            }
            else -> false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}