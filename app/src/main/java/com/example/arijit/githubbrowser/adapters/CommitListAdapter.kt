package com.example.arijit.githubbrowser.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.arijit.githubbrowser.R
import com.example.arijit.githubbrowser.databinding.LayoutCommitListBinding
import com.example.arijit.githubbrowser.models.CommitData

class CommitListAdapter: ListAdapter<CommitData, CommitListAdapter.CommitViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommitViewHolder {
        return CommitViewHolder(
            LayoutCommitListBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: CommitViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener{}
    }

    class CommitViewHolder(private val binding: LayoutCommitListBinding):
            RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CommitData) {
            binding.apply {
                tvCommit.text = data.commit.message
                tvDate.text = data.commit.author.date
                tvSha.text = data.sha.substring(0, 7)
                tvUser.text = data.commit.author.name
                if (data.author?.dpUrl != null){
                    val context = binding.root.context
                    Glide.with(context)
                        .load(data.author.dpUrl)
                        .error(R.drawable.ic_baseline_error_24)
                        .placeholder(R.drawable.ic_baseline_account_circle_24)
                        .override(100, 100)
                        .apply(RequestOptions.circleCropTransform())
                        .into(userAvatar)
                }
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<CommitData>() {
        override fun areItemsTheSame(oldItem: CommitData, newItem: CommitData): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: CommitData, newItem: CommitData): Boolean {
            return oldItem.sha == newItem.sha
        }
    }


}