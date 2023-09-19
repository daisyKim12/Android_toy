package org.texchtown.cgv.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import org.texchtown.cgv.data.youtube.Item
import org.texchtown.cgv.databinding.RvTrailerItemBinding

class TrailerAdapter(private val items: List<Item>)
    : RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder>() {

    inner class TrailerViewHolder (private val binding: RvTrailerItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(item: Item) {
            val youTubePlayerView: YouTubePlayerView = binding.youtubePlayerView
            //lifecycle.addObserver(youTubePlayerView)

            youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    val videoId = item.id.videoId
                    youTubePlayer.loadVideo(videoId, 100.toFloat())
                }
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerViewHolder {
        return TrailerViewHolder(
            RvTrailerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TrailerViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}