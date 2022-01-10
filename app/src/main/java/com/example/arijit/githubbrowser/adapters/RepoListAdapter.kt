package com.example.arijit.githubbrowser.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.arijit.githubbrowser.databinding.LayoutRepoListBinding
import com.example.arijit.githubbrowser.models.GithubRepoData

class RepoListAdapter(private val onClick: ClickListener,
    private val onShareClick: ClickListener): ListAdapter<GithubRepoData, RepoListAdapter.RepoModelHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoModelHolder {
        return RepoModelHolder(
            LayoutRepoListBinding.inflate(
                (LayoutInflater.from(parent.context))
            )
        )
    }

    override fun onBindViewHolder(holder: RepoModelHolder, position: Int) {
        val repoData = getItem(position)
        holder.bind(repoData, onShareClick)
        holder.itemView.setOnClickListener{ onClick.onClick(repoData) }
    }

    class RepoModelHolder(private val binding: LayoutRepoListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(repoData: GithubRepoData, onShareClick: ClickListener) {
            binding.apply {
                tvRepoName.text = repoData.name
                tvRepoDesc.text = repoData.description ?: ""
                ivSend.setOnClickListener {
                    onShareClick.onClick(repoData)
                }
            }
        }
    }

    class ClickListener(private val listener: (repoData: GithubRepoData) -> Unit) {
        fun onClick(repoData: GithubRepoData) = listener(repoData)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<GithubRepoData>() {
        override fun areItemsTheSame(oldItem: GithubRepoData, newItem: GithubRepoData): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: GithubRepoData, newItem: GithubRepoData): Boolean {
            return oldItem.id == newItem.id
        }
    }
}