package ru.netology.albumplayer

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.netology.albumplayer.adapter.TrackAdapter
import ru.netology.albumplayer.databinding.MainActivityBinding
import ru.netology.albumplayer.model.Album
import ru.netology.albumplayer.viewmodel.PlayerViewModel
import ru.netology.albumplayer.viewmodel.Status
import ru.netology.albumplayer.viewmodel.TrackUIModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val playerViewModel: PlayerViewModel by viewModels()


        val adapter = TrackAdapter {
            playerViewModel.play(it.file)
        }


        binding.content.adapter = adapter

        binding.retry.setOnClickListener {
            playerViewModel.load()
        }

        binding.play.setOnClickListener {
            val firstTrackFile = playerViewModel.state.value.tracks.first().file
            playerViewModel.play(firstTrackFile)
        }

        playerViewModel.state.flowWithLifecycle(lifecycle)
            .onEach {
                adapter.submitList(it.tracks)

                when (it.status) {
                    Status.IDLE -> {
                        with(binding) {
                            progress.isGone = true
                            errorGroup.isGone = true
                            album.isVisible = true
                            play.isVisible = true
                            title.text = it.albumTitle ?: ""
                            artist.text = it.albumArtist ?: ""
                            genre.text = it.albumGenre ?: ""
                            published.text = it.albumPublished ?: ""

                        }
                    }

                    Status.ERROR -> {
                        binding.progress.isGone = true
                        binding.errorGroup.isVisible = true
                        binding.album.isGone = true
                    }

                    Status.LOADING -> {
                        binding.progress.isVisible = true
                        binding.errorGroup.isGone = true
                        binding.album.isGone = true
                    }
                }
            }
            .launchIn(lifecycleScope)
    }
}