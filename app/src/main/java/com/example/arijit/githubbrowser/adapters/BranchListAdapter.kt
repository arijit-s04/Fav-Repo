package com.example.arijit.githubbrowser.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.arijit.githubbrowser.databinding.LayoutBranchListBinding
import com.example.arijit.githubbrowser.models.BranchData

class BranchListAdapter(private val onCLick: (branch: String) -> Unit): ListAdapter<BranchData, BranchListAdapter.BranchViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchViewHolder {
        return BranchViewHolder(
            LayoutBranchListBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: BranchViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
        holder.itemView.setOnClickListener{ onCLick(data.branchName) }
    }

    class BranchViewHolder(val binding: LayoutBranchListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: BranchData) {
            binding.tcBranchName.text = data.branchName
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<BranchData>() {
        override fun areItemsTheSame(oldItem: BranchData, newItem: BranchData): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: BranchData, newItem: BranchData): Boolean {
            return oldItem.branchName == newItem.branchName
        }
    }

}