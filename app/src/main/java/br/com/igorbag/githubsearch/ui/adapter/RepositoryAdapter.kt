package br.com.igorbag.githubsearch.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.igorbag.githubsearch.databinding.RepositoryItemBinding
import br.com.igorbag.githubsearch.domain.RepositoryItem

class RepositoryAdapter(
    private val openLink: (url: String) -> Unit,
    private val shareLink: (url: String) -> Unit
) : ListAdapter<RepositoryItem, RepositoryAdapter.ViewHolder>(RepositoryAdapter) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RepositoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, openLink, shareLink)
    }

    companion object : DiffUtil.ItemCallback<RepositoryItem>() {

        override fun areItemsTheSame(oldItem: RepositoryItem, newItem: RepositoryItem): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: RepositoryItem, newItem: RepositoryItem): Boolean {
            return oldItem.name == newItem.name && oldItem.htmlUrl == newItem.htmlUrl
        }

    }

    inner class ViewHolder(private val binding: RepositoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: RepositoryItem,
            openLink: (url: String) -> Unit,
            shareLink: (url: String) -> Unit
        ) {
            binding.tvName.text = item.name

            binding.cvCar.setOnClickListener {
                openLink.invoke(item.htmlUrl)
            }

            binding.ivFavorite.setOnClickListener {
                shareLink.invoke(item.htmlUrl)
            }
        }
    }
}
