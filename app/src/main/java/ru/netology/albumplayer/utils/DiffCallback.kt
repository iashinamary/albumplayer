package ru.netology.albumplayer.utils

import androidx.recyclerview.widget.DiffUtil
import ru.netology.albumplayer.viewmodel.TrackUIModel

object TrackDiffItemCallback : DiffUtil.ItemCallback<TrackUIModel>() {
    override fun areItemsTheSame(oldItem: TrackUIModel, newItem: TrackUIModel): Boolean =
        oldItem.file == newItem.file

    override fun areContentsTheSame(oldItem: TrackUIModel, newItem: TrackUIModel): Boolean =
        oldItem == newItem
}

