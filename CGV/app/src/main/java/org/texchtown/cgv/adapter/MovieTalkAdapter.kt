package org.texchtown.cgv.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import org.texchtown.cgv.data.Talk
import org.texchtown.cgv.data.youtube.Item
import org.texchtown.cgv.databinding.RvMovieTalkItemBinding


class MovieTalkAdapter (private val context: Context, private val items: List<Item>)
    : RecyclerView.Adapter<MovieTalkAdapter.TalkViewHolder>(){

    inner class TalkViewHolder(private val binding: RvMovieTalkItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(item: Item) {

            Glide.with(context).load(item.snippet.thumbnails.default.url).into(binding.imgThumbnail)
            binding.txtTitle.text = item.snippet.title
            binding.txtDetail.text = item.snippet.description

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TalkViewHolder {
        return TalkViewHolder(
            RvMovieTalkItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: TalkViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}