package com.example.arijit.githubbrowser.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.arijit.githubbrowser.R
import com.example.arijit.githubbrowser.databinding.LayoutIssueListBinding
import com.example.arijit.githubbrowser.models.IssueData

class IssueListAdapter: ListAdapter<IssueData, IssueListAdapter.IssueViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder {
        return IssueViewHolder(
            LayoutIssueListBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class IssueViewHolder(private val binding: LayoutIssueListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: IssueData) {
            binding.apply {
                tvIssue.text = data.title
                user.text = data.user?.login
                if(data.user?.dpUrl == null)
                    return@apply
                try {
                    val context = binding.root.context
                    Glide.with(context)
                        .load(data.user.dpUrl)
                        .error(R.drawable.ic_baseline_error_24)
                        .placeholder(R.drawable.ic_baseline_account_circle_24)
                        .override(100, 100)
                        .apply(RequestOptions.circleCropTransform())
                        .into(userAvatar)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<IssueData>() {
        override fun areItemsTheSame(oldItem: IssueData, newItem: IssueData): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: IssueData, newItem: IssueData): Boolean {
            return oldItem.id == newItem.id
        }
    }


}
