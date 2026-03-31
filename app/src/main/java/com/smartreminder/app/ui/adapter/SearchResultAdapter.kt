package com.smartreminder.app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.smartreminder.app.databinding.ItemSearchResultBinding
import com.smartreminder.app.network.NominatimService

class SearchResultAdapter(
    private val onSelect: (NominatimService.SearchResult) -> Unit
) : ListAdapter<NominatimService.SearchResult, SearchResultAdapter.SearchResultViewHolder>(
    DiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        return SearchResultViewHolder(
            ItemSearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SearchResultViewHolder(private val binding: ItemSearchResultBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: NominatimService.SearchResult) {
            val parts = result.displayName.split(",").map { it.trim() }
            binding.tvResultName.text = parts.take(2).joinToString(", ")
            binding.tvResultDetail.text = parts.drop(2).take(3).joinToString(", ")
            binding.root.setOnClickListener { onSelect(result) }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<NominatimService.SearchResult>() {
        override fun areItemsTheSame(
            oldItem: NominatimService.SearchResult,
            newItem: NominatimService.SearchResult
        ): Boolean = oldItem.displayName == newItem.displayName

        override fun areContentsTheSame(
            oldItem: NominatimService.SearchResult,
            newItem: NominatimService.SearchResult
        ): Boolean = oldItem == newItem
    }
}
