package ru.netology.albumplayer.viewmodel

data class PlayerState(
    val status: Status = Status.IDLE,
    val tracks: List<TrackUIModel> = emptyList(),
    val albumTitle: String? = null,
    val albumArtist: String? = null,
    val albumGenre: String? = null,
    val albumPublished: String? = null
)

enum class Status{
    IDLE,
    ERROR,
    LOADING
}
