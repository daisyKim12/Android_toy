package org.texchtown.done

import android.app.Dialog
import android.database.Cursor
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.DialogFragment
import java.util.*

class HabitDialog : DialogFragment() {
    //widget
    var et_name: EditText? = null
    var et_goal: EditText? = null
    var et_group: EditText? = null
    var tv_color: TextView? = null
    var rg_status: RadioGroup? = null
    var rg_color: RadioGroup? = null
    var rb_build: RadioButton? = null
    var rb_quit: RadioButton? = null
    var cb_mon: CheckBox? = null
    var cb_tue: CheckBox? = null
    var cb_wed: CheckBox? = null
    var cb_thu: CheckBox? = null
    var cb_fri: CheckBox? = null
    var cb_sat: CheckBox? = null
    var cb_sun: CheckBox? = null
    var rb_color_0: RadioButton? = null
    var rb_color_1: RadioButton? = null
    var rb_color_2: RadioButton? = null
    var rb_color_3: RadioButton? = null
    var rb_color_4: RadioButton? = null
    var rb_color_5: RadioButton? = null
    var rb_color_6: RadioButton? = null
    var rb_color_7: RadioButton? = null
    var swt_ntf: SwitchCompat? = null
    var btn_save: Button? = null
    var btn_cancel: Button? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var createDialog: Dialog? = null
        createDialog = Dialog(requireContext())
        createDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        createDialog!!.setContentView(R.layout.dialog_add_habit)

        //bind widget to view
        tv_color = createDialog!!.findViewById(R.id.tv_color)
        et_name = createDialog!!.findViewById(R.id.edit_name)
        et_goal = createDialog!!.findViewById(R.id.edit_goal)
        et_group = createDialog!!.findViewById(R.id.edit_group)
        rg_status = createDialog!!.findViewById(R.id.radio_group_status)
        rg_color = createDialog!!.findViewById(R.id.radio_group_color)
        rb_build = createDialog!!.findViewById(R.id.radio_build)
        rb_quit = createDialog!!.findViewById(R.id.radio_quit)
        cb_mon = createDialog!!.findViewById(R.id.check_mon)
        cb_tue = createDialog!!.findViewById(R.id.check_tue)
        cb_wed = createDialog!!.findViewById(R.id.check_wed)
        cb_thu = createDialog!!.findViewById(R.id.check_thu)
        cb_fri = createDialog!!.findViewById(R.id.check_fri)
        cb_sat = createDialog!!.findViewById(R.id.check_sat)
        cb_sun = createDialog!!.findViewById(R.id.check_sun)
        rb_color_0 = createDialog!!.findViewById(R.id.color_0)
        rb_color_1 = createDialog!!.findViewById(R.id.color_1)
        rb_color_2 = createDialog!!.findViewById(R.id.color_2)
        rb_color_3 = createDialog!!.findViewById(R.id.color_3)
        rb_color_4 = createDialog!!.findViewById(R.id.color_4)
        rb_color_5 = createDialog!!.findViewById(R.id.color_5)
        rb_color_6 = createDialog!!.findViewById(R.id.color_6)
        rb_color_7 = createDialog!!.findViewById(R.id.color_7)
        swt_ntf = createDialog!!.findViewById(R.id.swt_ntf)
        btn_save = createDialog!!.findViewById(R.id.create_save_btn)
        btn_cancel = createDialog!!.findViewById(R.id.create_cancel_btn)

        //add text watcher onto edit text and button
        et_name!!.addTextChangedListener(editTextWatcher)
        et_goal!!.addTextChangedListener(editTextWatcher)
        et_group!!.addTextChangedListener(editTextWatcher)
        rg_color!!.setOnCheckedChangeListener(radioButtonWatcher)
        rg_status!!.setOnCheckedChangeListener(radioButtonWatcher)
        checkboxListener()

        //set on click listener
        btn_save!!.setOnClickListener { //save data into Habit table
            saveNewHabitData()
            //save data into Date table
            updateCurrentHabit()

            //add habit to recycler view
            val mainActivity = activity as MainActivity?
            mainActivity!!.onFragmentChanged(0)
            //dismiss dialog
            createDialog!!.dismiss()
        }
        btn_cancel!!.setOnClickListener { createDialog!!.dismiss() }
        return createDialog
    }

    private val checkedDays: ArrayList<Int>
        private get() {
            val selectedDays = ArrayList<Int>()
            val days = arrayOf(cb_sun, cb_mon, cb_tue, cb_wed, cb_thu, cb_fri, cb_sat)
            for (i in 0..6) {
                if (days[i]!!.isChecked) {
                    selectedDays.add(i + 1)
                }
            }
            return selectedDays
        }

    private fun enableSaveButton() {
        //charSequence is text input by three editText view
        //so need to get input directly from the view
        val habitNameInput = et_name!!.text.toString().trim { it <= ' ' }
        val habitGoalInput = et_goal!!.text.toString().trim { it <= ' ' }
        val habitGroupInput = et_group!!.text.toString().trim { it <= ' ' }
        //find with button is checked
        val StatusId = rg_status!!.checkedRadioButtonId
        val ColorId = rg_color!!.checkedRadioButtonId
        val DaysId = checkedDays
        val editTextFilled: Boolean
        val buttonChecked: Boolean
        editTextFilled = (!habitNameInput.isEmpty() && !habitGoalInput.isEmpty()
                && !habitGroupInput.isEmpty())
        buttonChecked = if (disableColorButton()) {
            StatusId != -1 && !DaysId.isEmpty()
        } else {
            StatusId != -1 && ColorId != -1 && !DaysId.isEmpty()
        }
        btn_save!!.isEnabled = buttonChecked && editTextFilled
    }

    private fun saveNewHabitData() {
        val habitDatabaseHelper = HabitDatabaseHelper(activity)
        //get data from the view
        val name: String
        val group: String
        val goal: Int
        var color: Int
        var mon: Boolean?
        var tue: Boolean?
        var wed: Boolean?
        var thu: Boolean?
        var fri: Boolean?
        var sat: Boolean?
        var sun: Boolean
        val status: Boolean
        val notification: Boolean
        //get edit text data
        name = et_name!!.text.toString().trim { it <= ' ' }
        goal = et_goal!!.text.toString().trim { it <= ' ' }.toInt()
        group = et_group!!.text.toString().trim { it <= ' ' }
        //get build quit data (build == true, quit == false)
        status = if (rg_status!!.checkedRadioButtonId == R.id.radio_build) {
            true
        } else {
            false
        }

        //set group color if group color already exists
        color = -1
        if (disableColorButton()) {
            val cursor: Cursor = habitDatabaseHelper.readGroupDetailData(et_group!!.text.toString())
            while (cursor.moveToNext()) {
                color = cursor.getInt(3)
            }
        } else {
            //get color data
            when (rg_color!!.checkedRadioButtonId) {
                R.id.color_0 -> color = Color.parseColor("#F44336")
                R.id.color_1 -> color = Color.parseColor("#E91E63")
                R.id.color_2 -> color = Color.parseColor("#9C27B0")
                R.id.color_3 -> color = Color.parseColor("#3F51B5")
                R.id.color_4 -> color = Color.parseColor("#03A9F4")
                R.id.color_5 -> color = Color.parseColor("#009688")
                R.id.color_6 -> color = Color.parseColor("#8BC34A")
                R.id.color_7 -> color = Color.parseColor("#FFC107")
                else -> {
                    color = -1
                    Log.d(TAG, "saveNewHabitData: color data saving failed")
                }
            }
        }
        //get days data
        sun = false
        sat = sun
        fri = sat
        thu = fri
        wed = thu
        tue = wed
        mon = tue
        val DaysId = checkedDays
        for (chosenDay in DaysId) {
            when (chosenDay) {
                1 -> sun = true
                2 -> mon = true
                3 -> tue = true
                4 -> wed = true
                5 -> thu = true
                6 -> fri = true
                7 -> sat = true
            }
        }
        //get notification data
        notification = swt_ntf!!.isChecked

        //pass data to data base helper
        habitDatabaseHelper.addHabit(
            name, status, goal, mon, tue, wed, thu, fri, sat, sun, group, color, notification
        )
    }

    private fun updateCurrentHabit() {
        val habitDatabaseHelper = HabitDatabaseHelper(activity)
        val name = et_name!!.text.toString().trim { it <= ' ' }
        val calendar = Calendar.getInstance()
        val currentDay = calendar[Calendar.DAY_OF_WEEK]
        val DaysId = checkedDays
        if (DaysId.contains(currentDay)) {
            habitDatabaseHelper.addTodayHabit(name)
        }
    }

    private fun disableColorButton(): Boolean {
        val habitDatabaseHelper = HabitDatabaseHelper(activity)
        val currentGroups: ArrayList<String> = habitDatabaseHelper.readAllGroup()
        return if (currentGroups.contains(et_group!!.text.toString())) {
            tv_color!!.text = "group color exists"
            tv_color!!.setTextColor(Color.parseColor("red"))
            for (i in 0 until rg_color!!.childCount) {
                rg_color!!.getChildAt(i).isEnabled = false
            }
            true
        } else {
            tv_color!!.text = "group color"
            tv_color!!.setTextColor(Color.parseColor("#B1B1B1"))
            for (i in 0 until rg_color!!.childCount) {
                rg_color!!.getChildAt(i).isEnabled = true
            }
            false
        }
    }


    private val editTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            enableSaveButton()
        }

        override fun afterTextChanged(editable: Editable) {
            //disable color radio group if entered group already exists
            disableColorButton()
        }
    }
    private val checkboxWatcher = View.OnClickListener { enableSaveButton() }
    private val radioButtonWatcher =
        RadioGroup.OnCheckedChangeListener { radioGroup, i -> enableSaveButton() }

    private fun checkboxListener() {
        cb_mon!!.setOnClickListener(checkboxWatcher)
        cb_tue!!.setOnClickListener(checkboxWatcher)
        cb_wed!!.setOnClickListener(checkboxWatcher)
        cb_thu!!.setOnClickListener(checkboxWatcher)
        cb_fri!!.setOnClickListener(checkboxWatcher)
        cb_sat!!.setOnClickListener(checkboxWatcher)
        cb_sun!!.setOnClickListener(checkboxWatcher)
    }

    companion object {
        //TAG
        private const val TAG = "HabitDialog"
    }
}