package org.texchtown.done

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class TodayFragment : Fragment() {
    //var
    var habitDatabaseHelper: HabitDatabaseHelper? = null
    var habit_name: ArrayList<String>? = null
    var habit_detail: ArrayList<String>? = null
    var habit_count: ArrayList<Int>? = null
    var habit_color: ArrayList<Int>? = null
    var todayAdapter: TodayAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_today, container, false)
        var recyclerView: RecyclerView = view.findViewById(R.id.recycler)

        //getting habit data from db to bind it to a adapter
        habitDatabaseHelper = HabitDatabaseHelper(activity)
        habit_name = ArrayList()
        habit_detail = ArrayList()
        habit_count = ArrayList()
        habit_color = ArrayList()
        storeDataInArray()
        checkDataInArray()

        //write date db once a day;
        initializeDataDatabase()

        //initialize todayAdapter and set Adapter for recycler view
        todayAdapter = TodayAdapter(
            requireActivity(),
            habit_name!!, habit_detail!!, habit_count!!, habit_color!!
        )
        recyclerView.setAdapter(todayAdapter)
        recyclerView.setLayoutManager(LinearLayoutManager(activity))

        //set swipe action
        val itemTouchHelper = ItemTouchHelper(cardViewSwipeCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
        return view
    }

    fun storeDataInArray() {
        val cursor = habitDatabaseHelper!!.readTodayData()
        if (cursor!!.count == 0) {
            Log.d(TAG, "storeDataInArray: cursor with no data")
        } else {
            while (cursor.moveToNext()) {
                //add status, goal, h_group to make detail text
                var detail = ""
                detail = if (cursor.getInt(1) == 1) {
                    ("<" + cursor.getString(3) + "> lets start " + cursor.getString(0)
                            + " for " + cursor.getInt(2) + "days")
                } else {
                    ("<" + cursor.getString(3) + "> lets quit " + cursor.getString(0)
                            + " for " + cursor.getInt(2) + "days")
                }
                habit_name!!.add(cursor.getString(0))
                habit_detail!!.add(detail)
                habit_count!!.add(cursor.getInt(5))
                habit_color!!.add(cursor.getInt(4))
            }
        }
    }

    fun checkDataInArray() {
        for (name in habit_name!!) {
            Log.d(TAG, "storeDataInArray: $name")
        }
        for (detail in habit_detail!!) {
            Log.d(TAG, "checkDataInArray: $detail")
        }
        for (count in habit_count!!) {
            Log.d(TAG, "checkDataInArray: " + Integer.toString(count))
        }
        for (color in habit_color!!) {
            Log.d(TAG, "checkDataInArray: " + Integer.toString(color))
        }
    }

    fun initializeDataDatabase() {
        val calendar = Calendar.getInstance()
        val currentDay = calendar[Calendar.DAY_OF_MONTH]
        val setting = requireActivity().getSharedPreferences("PREFS", 0)
        val lastDay = setting.getInt("day", 0)
        if (lastDay != currentDay) {
            val editor = setting.edit()
            editor.putInt("day", currentDay)
            editor.commit()
            //write date data once a day
            for (habit in habit_name!!) {
                habitDatabaseHelper!!.addTodayHabit(habit)
            }
        }
    }

    var cardViewSwipeCallback: ItemTouchHelper.SimpleCallback =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (direction == ItemTouchHelper.LEFT) {
                    //get the row of element in a recycler view with is swiped
                    val position = viewHolder.adapterPosition
                    val name = habit_name!![position]
                    //go to the sub class with selected element data
                    val mainActivity = activity as MainActivity?
                    mainActivity!!.moveToHabitSpecific(name)
                } else {
                    Log.d(TAG, "onSwiped: swipe direction error")
                }
            }
        }

    companion object {
        //TAG
        private const val TAG = "TodayFragment"
    }
}