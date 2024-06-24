package ru.netology.albumplayer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.albumplayer.R
import ru.netology.albumplayer.databinding.ItemTrackBinding
import ru.netology.albumplayer.utils.TrackDiffItemCallback
import ru.netology.albumplayer.viewmodel.TrackUIModel

class TrackAdapter(
    private val onPlayClickListener: (TrackUIModel) -> Unit,
) : ListAdapter<TrackUIModel, TrackAdapter.TrackViewHolder>(TrackDiffItemCallback) {

    class TrackViewHolder(
        private val onPlayClickListener: (TrackUIModel) -> Unit,
        private val binding: ItemTrackBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(trackUIModel: TrackUIModel) {
            binding.play.setOnClickListener {
                onPlayClickListener(trackUIModel)
            }
            binding.trackName.text = trackUIModel.file
            binding.play.setIconResource(
                if (trackUIModel.playing) {
                    R.drawable.pause_24
                } else {
                    R.drawable.play_24
                }
            )
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        return TrackViewHolder(
            onPlayClickListener,
            ItemTrackBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
        )
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}