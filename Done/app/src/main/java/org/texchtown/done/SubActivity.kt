package org.texchtown.done

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import org.texchtown.done.databinding.ActivitySubBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*


class SubActivity : AppCompatActivity() {
    //widget
    private lateinit var binding: ActivitySubBinding
    var ll_all: LinearLayout? = null
    var ll_bottom: LinearLayout? = null
    var toolbar: Toolbar? = null
    var actionBar: ActionBar? = null
    var appBarLayout: AppBarLayout? = null
    var tv_name: TextView? = null
    var tv_goal: TextView? = null
    var tv_count: TextView? = null
    var tv_strike: TextView? = null
    var tv_month_year: TextView? = null
    var btn_prv: Button? = null
    var btn_nxt: Button? = null
    var rv_calendar: RecyclerView? = null

    //var
    var selectedDate: LocalDate? = null
    private lateinit var todayYearMonth: String
    private lateinit var name: String
    var color = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub)
        transparentStatusBar()
        val intent = intent
        name = intent.getStringExtra("swiped element").toString()
        initWidget()
        //set toolbar
        toolbar = findViewById(R.id.toolbar_sub)
        setSupportActionBar(toolbar)
        actionBar = supportActionBar

        //set up button
        actionBar!!.setDisplayHomeAsUpEnabled(true)

        //set habit details
        setWidgetData(name)

        //set on click listener
        btn_prv!!.setOnClickListener(listener)
        btn_nxt!!.setOnClickListener(listener)

        //set calendar
        selectedDate = LocalDate.now()
        val currentDate = Calendar.getInstance().time
        val yearMonthFormat = SimpleDateFormat("yyyyMM", Locale.getDefault())
        todayYearMonth = yearMonthFormat.format(currentDate)
        setCalendar()
    }

    private fun setCalendar() {

        tv_month_year!!.text = monthYearFromDate(selectedDate)
        val dayInMonth = daysInMonthArray(selectedDate)

        val calendarAdapter = CalendarAdapter(
            this, dayInMonth, todayYearMonth, color, name
        )
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(applicationContext, 7)
        rv_calendar!!.adapter = calendarAdapter
        rv_calendar!!.layoutManager = layoutManager
    }

    private fun daysInMonthArray(date: LocalDate?): ArrayList<String> {
        val daysInMonthArray = ArrayList<String>()
        val yearMonth = YearMonth.from(date)
        val daysInMonth = yearMonth.lengthOfMonth()
        val firstOfMonth = selectedDate!!.withDayOfMonth(1)
        val dayOfWeek = firstOfMonth.dayOfWeek.value
        for (i in 1..42) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add("")
            } else {
                daysInMonthArray.add((i - dayOfWeek).toString())
            }
        }
        return daysInMonthArray
    }

    private fun monthYearFromDate(date: LocalDate?): String {
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return date!!.format(formatter)
    }

    private fun setWidgetData(name: String?) {
        val habitDatabaseHelper = HabitDatabaseHelper(this)
        val cursor = habitDatabaseHelper.readHabitDetailByName(name!!)
        if (cursor.count == 0) {
            Log.d(TAG, "setWidgetData: cursor with no data")
        } else {
            while (cursor.moveToNext()) {
                tv_name!!.text = name.uppercase()
                tv_goal!!.text = "GOAL: " + Integer.toString(cursor.getInt(0))
                tv_count!!.text = "TODAY: " + Integer.toString(cursor.getInt(1))
                tv_strike!!.text = "STRIKE: " + Integer.toString(cursor.getInt(2))
                color = cursor.getInt(3)
                appBarLayout!!.setBackgroundColor(color)
                toolbar!!.setBackgroundColor(color)
                ll_all!!.setBackgroundColor(color)
            }
        }
    }

    fun transparentStatusBar() {
        val window = this.window
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
    }

    override fun onBackPressed() {
        val intent = Intent(this@SubActivity, MainActivity::class.java)
        startActivity(intent)
    }

    var listener = View.OnClickListener { view ->
        when (view.id) {
            R.id.btn_previous_month -> {
                selectedDate = selectedDate!!.minusMonths(1)
                setCalendar()
            }
            R.id.btn_next_month -> {
                selectedDate = selectedDate!!.plusMonths(1)
                setCalendar()
            }
        }
    }

    fun initWidget() {
        ll_all = findViewById(R.id.linear_layout)
        ll_bottom = findViewById(R.id.ll_calendar)
        appBarLayout = findViewById(R.id.appbar_layout)
        tv_name = findViewById(R.id.tv_name)
        tv_goal = findViewById(R.id.tv_goal)
        tv_count = findViewById(R.id.tv_count)
        tv_strike = findViewById(R.id.tv_strike)
        btn_prv = findViewById(R.id.btn_previous_month)
        btn_nxt = findViewById(R.id.btn_next_month)
        tv_month_year = findViewById(R.id.tv_month_year)
        rv_calendar = findViewById(R.id.rv_calendar)
    }

    companion object {
        //TAG
        private const val TAG = "SubActivity"
    }
}