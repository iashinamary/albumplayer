package ru.netology.albumplayer.viewmodel

import android.media.MediaPlayer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.netology.albumplayer.api.Api
import ru.netology.albumplayer.api.BASE_URL

class PlayerViewModel : ViewModel() {

    private val api = Api.ApiService.api
    private val _state = MutableStateFlow(PlayerState())
    val state = _state.asStateFlow()
    private val mediaPlayer = MediaPlayer()

    init {
        load()

        mediaPlayer.setOnCompletionListener {
            val tracks = _state.value.tracks
            val played = tracks.indexOfFirst { it.playing }

            val nextIndex = played + 1
            val safeIndex = if (nextIndex > tracks.size) {
                0
            } else {
                nextIndex
            }

            play(tracks[safeIndex].file)
        }
    }


    fun load() {
        _state.update {
            it.copy(status = Status.LOADING)
        }

        viewModelScope.launch {
            runCatching {
                api.getAll()
            }
                .onFailure {
                    _state.update {
                        it.copy(status = Status.ERROR)
                    }
                }
                .onSuccess { album ->
                    _state.update {
                        it.copy(
                            status = Status.IDLE,
                            tracks = album.tracks.map(TrackUIModel::fromModel),
                            albumTitle = album.title,
                            albumArtist = album.artist,
                            albumGenre = album.genre,
                            albumPublished = album.published
                        )
                    }
                }
        }
    }

    fun play(file: String) {
        _state.update { state ->
            state.copy(
                tracks = state.tracks.map {
                    if (it.file == file) {
                        val playing = it.playing
                        if (playing) {
                            mediaPlayer.stop()
                        } else {
                            mediaPlayer.reset()
                            mediaPlayer.setDataSource("${BASE_URL}$file")
                            mediaPlayer.setOnPreparedListener {
                                it.start()
                            }
                            mediaPlayer.prepareAsync()
                        }
                        it.copy(playing = !it.playing)
                    } else {
                        it.copy(playing = false)
                    }
                }
            )
        }
    }


}