package ru.netology.albumplayer.viewmodel

import ru.netology.albumplayer.model.Track

data class TrackUIModel(
    val file: String,
    val playing: Boolean = false
){
    companion object {
        fun fromModel(track: Track) = TrackUIModel(
            file = track.file
        )
    }
}