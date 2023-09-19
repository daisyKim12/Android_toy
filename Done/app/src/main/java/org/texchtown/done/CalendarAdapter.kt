package org.texchtown.done

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView


class CalendarAdapter(
    //var
    var context: Context,
    var dayInMonth: ArrayList<String>,
    var yearMonth: String,
    var color: Int,
    var name: String,
) :


RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {
    var dayInMonthInteger: ArrayList<Int>
    var dateCheckedInteger: ArrayList<Int>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.cardview_calendar, parent, false)
        val layoutParams = view.layoutParams
        layoutParams.height = (parent.height * 0.166666666).toInt()
        return CalendarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.tv_day.text = dayInMonth[position]

        if (habitIsDone(position)) {
            holder.cl_calendar.setBackgroundColor(color)
            Log.d(TAG, "onBindViewHolder: background color set")
        }

    }

    private fun habitIsDone(position: Int): Boolean {
        return if (dateCheckedInteger.contains(dayInMonthInteger[position])) {
            true
        } else false
    }

    override fun getItemCount(): Int {
        return dayInMonth.size
    }

    inner class CalendarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cl_calendar: ConstraintLayout
        var tv_day: TextView

        //        ImageView iv_check;
        init {
            cl_calendar = itemView.findViewById(R.id.cl_calender)
            tv_day = itemView.findViewById(R.id.tv_day)
            //            iv_check = itemView.findViewById(R.id.iv_check);
        }
    }

    companion object {
        //TAG
        private const val TAG = "CalendarAdapter"
        private var test = 0
    }

    init {
        dayInMonthInteger = ArrayList()
        dateCheckedInteger = ArrayList()
        for (date in dayInMonth) {
            if (date === "") {
                dayInMonthInteger.add(-1)
            } else {
                dayInMonthInteger.add(date.toInt())
            }
        }
        val habitDatabaseHelper = HabitDatabaseHelper(context)
        val cursor = habitDatabaseHelper.readDateByName(name)
        if (cursor.count == 0) {
            Log.d(TAG, "habitIsDone: cursor with no data")
        } else {
            while (cursor.moveToNext()) {
                test = cursor.getString(0).substring(0, 6).toInt()
                if (cursor.getString(0).substring(0, 6).toInt() == yearMonth.toInt()) {
                    val date = cursor.getString(0).substring(6)
                    dateCheckedInteger.add(date.toInt())
                }
            }
        }
    }
}
