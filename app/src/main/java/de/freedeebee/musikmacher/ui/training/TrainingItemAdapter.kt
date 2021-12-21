package de.freedeebee.musikmacher.ui.training

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.freedeebee.musikmacher.data.model.TrainingSession
import de.freedeebee.musikmacher.databinding.TrainingItemBinding

class TrainingItemAdapter(val onClick: (sessionId: Long) -> Unit): ListAdapter<TrainingSession, TrainingItemAdapter.TrainingItemViewHolder>(TrainingItemDiffCallback()) {

    class TrainingItemViewHolder(val binding: TrainingItemBinding): RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun inflateFrom(parent: ViewGroup): TrainingItemViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = TrainingItemBinding.inflate(
                    inflater,
                    parent,
                    false
                )
                return TrainingItemViewHolder(binding)
            }
        }

        fun bind(item: TrainingSession, onClick: (sessionId: Long) -> Unit) {
            binding.session = TrainingItem(item)
            binding.root.setOnClickListener { onClick(item.id) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingItemViewHolder {
        return TrainingItemViewHolder.inflateFrom(parent)
    }

    override fun onBindViewHolder(holder: TrainingItemViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }
}

class TrainingItemDiffCallback: DiffUtil.ItemCallback<TrainingSession>() {
    override fun areItemsTheSame(oldItem: TrainingSession, newItem: TrainingSession): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TrainingSession, newItem: TrainingSession): Boolean {
        return oldItem == newItem
    }
}