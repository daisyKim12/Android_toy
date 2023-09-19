package org.texchtown.done

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class TodayAdapter(
    //var
    private val context: Context,
    private val habit_name: ArrayList<String>,
    private val habit_detail: ArrayList<String>,
    private val habit_count: ArrayList<Int>,
    private val habit_color: ArrayList<Int>
) : RecyclerView.Adapter<TodayAdapter.TodayViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.cardview_habit, parent, false)
        return TodayViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodayViewHolder, position: Int) {
        val name = habit_name[position]
        val detail = habit_detail[position]
        val count = habit_count[position]
        val color = habit_color[position]
        val mainActivity = context as MainActivity
        val habitDatabaseHelper = HabitDatabaseHelper(context)
        val checkedHabit = habitDatabaseHelper.checkTodayHabit()
        if (checkedHabit.contains(name)) {
            holder.cardView_bg.setBackgroundColor(color)
        } else {
            holder.cardView_bg.setBackgroundColor(Color.parseColor("#808080"))
        }
        holder.tv_name.text = name
        holder.tv_detail.text = detail
        holder.tv_count.text = Integer.toString(count)
        holder.cardView_bg.setOnClickListener {
            if (checkedHabit.contains(name)) {
                //change color and check today status to true
                holder.cardView_bg.setBackgroundColor(Color.parseColor("#808080"))
                habitDatabaseHelper.updateCount(name, count - 1)
                habitDatabaseHelper.updateTodayHabit(name, false)
                mainActivity.onFragmentChanged(0)
            } else {
                holder.cardView_bg.setBackgroundColor(color)
                habitDatabaseHelper.updateCount(name, count + 1)
                habitDatabaseHelper.updateTodayHabit(name, true)
                mainActivity.onFragmentChanged(0)
            }
        }
    }

    override fun getItemCount(): Int {
        return habit_name.size
    }

    inner class TodayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cardView_bg: ConstraintLayout
        var tv_name: TextView
        var tv_detail: TextView
        var tv_count: TextView

        init {
            cardView_bg = itemView.findViewById(R.id.habit_layout)
            tv_name = itemView.findViewById(R.id.habit_name)
            tv_detail = itemView.findViewById(R.id.habit_detail)
            tv_count = itemView.findViewById(R.id.habit_count)
        }
    }

    companion object {
        //TAG
        private const val TAG = "TodayAdapter"
    }
}