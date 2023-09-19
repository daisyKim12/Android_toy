package org.texchtown.cgv.adapter

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.texchtown.cgv.R
import org.texchtown.cgv.databinding.RvMovieItemBinding
import org.texchtown.cgv.data.boxoffice.WeeklyBoxOffice

class MovieAdapter(private val context: Context,
                   private val listener: OnItemClickListener,
                   private val weeklyBoxOfficeList: List<WeeklyBoxOffice>,
                   private val posterUrlList: List<String>)
    : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(){

    inner class MovieViewHolder(private val binding: RvMovieItemBinding)
        : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        fun bindItem(weeklyBoxOffice: WeeklyBoxOffice, posterUrl: String) {
            binding.txtBoxOffice.text = weeklyBoxOffice.rank
            binding.txtTitle.text = weeklyBoxOffice.movieNm
            binding.txtAudience.text = "누적관객 "+ (weeklyBoxOffice.audiCnt.toInt() % 10000).toString()

//            binding.txtAgeLimit.text = "all"
//            binding.imgPoster.setImageResource(R.drawable.ic_launcher_background)
            Glide.with(context).load(posterUrl).into(binding.imgPoster)
            binding.txtRating.text = "9.1"
            binding.imgPoster.setOnClickListener(this)
            binding.btnFastTicketing.setOnClickListener(this)

            Log.d(TAG, "bindItem: "+ weeklyBoxOffice.movieNm)
        }

        override fun onClick(view: View?) {
            val position: Int = adapterPosition
            val movieName: String = weeklyBoxOfficeList.elementAt(position).movieNm
            val movieCd: String = weeklyBoxOfficeList.elementAt(position).movieCd
            when(view?.id){
                R.id.img_poster -> {
                    if(position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(movieName, movieCd, position)
                    }
                }
                R.id.btn_fast_ticketing -> {
                    if(position != RecyclerView.NO_POSITION) {
                        listener.onFastTicketingClick(position)
                    }
                }
            }

        }
    }

    interface OnItemClickListener {
        fun onItemClick(movieName: String, movieCd: String, rating: Int)
        fun onFastTicketingClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {

        return MovieViewHolder(
            RvMovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindItem(weeklyBoxOfficeList[position], posterUrlList[position])

    }

    override fun getItemCount(): Int {
        return weeklyBoxOfficeList.size
    }
}