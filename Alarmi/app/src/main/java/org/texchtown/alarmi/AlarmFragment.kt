package org.texchtown.alarmi

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import org.texchtown.alarmi.databinding.FabAlarmDialogBinding
import org.texchtown.alarmi.databinding.FabWeekDialogBinding
import org.texchtown.alarmi.databinding.FragmentAlarmBinding

class AlarmFragment : Fragment() {
    //tag
    private val TAG = "AlarmFragment"
    //binding
    private lateinit var binding: FragmentAlarmBinding
    private lateinit var fabAlarmDialogBinding: FabAlarmDialogBinding
    private lateinit var fabWeekDialogBinding: FabWeekDialogBinding
    //fab actions
    private val rotateOpen: Animation by lazy {AnimationUtils.loadAnimation(activity, R.anim.rotate_open_anim)}
    private val rotateClose: Animation by lazy {AnimationUtils.loadAnimation(activity, R.anim.rotate_close_anim)}
    private val fromBottom: Animation by lazy {AnimationUtils.loadAnimation(activity, R.anim.from_bottom_anim)}
    private val toBottom: Animation by lazy {AnimationUtils.loadAnimation(activity, R.anim.to_bottom_anim)}
    private var clicked = false
    //Sqlite
    private lateinit var sqliteHelper: MyDatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlarmBinding.inflate(inflater, container, false)
        //sqlite initialize
        sqliteHelper = MyDatabaseHelper(requireContext())
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.fabMain.setOnClickListener {
            onAddButtonClicked()
        }
        binding.fabAlarm.setOnClickListener {
            popUpAlarmDialog()
            onAddButtonClicked()

        }
        binding.fabQuick.setOnClickListener {
            popUpQuickAlarmDialog()
            onAddButtonClicked()

        }
    }

    private fun onAddButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        setClickable(clicked)
        clicked = !clicked
    }

    private fun setVisibility(clicked : Boolean) {
        if(!clicked){
            binding.fabAlarm.visibility = View.VISIBLE
            binding.fabQuick.visibility = View.VISIBLE
            binding.fabAlarmTxt.visibility = View.VISIBLE
            binding.fabQuickTxt.visibility = View.VISIBLE
            binding.fabGradientBg.visibility = View.VISIBLE

        }
        else {
            binding.fabAlarm.visibility = View.INVISIBLE
            binding.fabQuick.visibility = View.INVISIBLE
            binding.fabAlarmTxt.visibility = View.INVISIBLE
            binding.fabQuickTxt.visibility = View.INVISIBLE
            binding.fabGradientBg.visibility = View.INVISIBLE
        }

    }
    private fun setClickable(clicked : Boolean){
        if(!clicked){
            binding.fabAlarm.isClickable = true
            binding.fabQuick.isClickable = true
        }
        else {
            binding.fabAlarm.isClickable = false
            binding.fabQuick.isClickable = false
        }

    }

    private fun setAnimation(clicked : Boolean) {
        if(!clicked){
            binding.fabAlarm.startAnimation(fromBottom)
            binding.fabQuick.startAnimation(fromBottom)
            binding.fabAlarmTxt.startAnimation(fromBottom)
            binding.fabQuickTxt.startAnimation(fromBottom)
            binding.fabMain.startAnimation(rotateOpen)
        }
        else {
            binding.fabAlarm.startAnimation(toBottom)
            binding.fabQuick.startAnimation(toBottom)
            binding.fabAlarmTxt.startAnimation(toBottom)
            binding.fabQuickTxt.startAnimation(toBottom)
            binding.fabMain.startAnimation(rotateClose)
        }
    }


    private fun popUpQuickAlarmDialog() {
        TODO("Not yet implemented")
    }

    private fun popUpAlarmDialog() {
        val dialog = Dialog(requireContext())
        //We have added a title in the custom layout. So let's disable the default title.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
        dialog.setCancelable(true)
        //Mention the name of the layout of your custom dialog.
        fabAlarmDialogBinding = FabAlarmDialogBinding.inflate(layoutInflater)
        dialog.setContentView(fabAlarmDialogBinding.root)

        fabAlarmDialogBinding.cl3.setOnClickListener {
            popUpWeekDialog()
        }

        fabAlarmDialogBinding.dialogClear.setOnClickListener {
            dialog.dismiss()
        }
        fabAlarmDialogBinding.dialogSave.setOnClickListener {
            addAlarmInSql()
            dialog.dismiss()
        }



        dialog.show()
        val window: Window? = dialog.window
        if(window != null){
            window.setBackgroundDrawableResource(android.R.color.transparent)
            window.setLayout(binding.root.width, binding.root.height)
        }
    }

    private fun popUpWeekDialog() {
        val weekDialog = Dialog(requireContext())
        //We have added a title in the custom layout. So let's disable the default title.
        weekDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
        weekDialog.setCancelable(true)
        //Mention the name of the layout of your custom dialog.
        fabWeekDialogBinding = FabWeekDialogBinding.inflate(layoutInflater)
        weekDialog.setContentView(fabWeekDialogBinding.root)


        fabWeekDialogBinding.cancel.setOnClickListener {
            weekDialog.dismiss()
        }
        fabWeekDialogBinding.save.setOnClickListener {
            updateRepeat()
            weekDialog.dismiss()
        }

        weekDialog.show()
    }

    private fun updateRepeat() {
        val repeat_temp = StringBuilder()

        if(fabWeekDialogBinding.sunCheckbox.isChecked)
            repeat_temp.append("일")
        if(fabWeekDialogBinding.monCheckbox.isChecked)
            repeat_temp.append("월")
        if(fabWeekDialogBinding.tueCheckbox.isChecked)
            repeat_temp.append("화")
        if(fabWeekDialogBinding.wedCheckbox.isChecked)
            repeat_temp.append("수")
        if(fabWeekDialogBinding.thuCheckbox.isChecked)
            repeat_temp.append("목")
        if(fabWeekDialogBinding.friCheckbox.isChecked)
            repeat_temp.append("금")
        if(fabWeekDialogBinding.satCheckbox.isChecked)
            repeat_temp.append("토")

        fabAlarmDialogBinding.repeatTxt2.text = repeat_temp.toString()
        fabAlarmDialogBinding.repeatTxt2.setTextColor(getResources().getColor(R.color.dialog_blue))

    }

    private fun addAlarmInSql() {
        val hour: Int = fabAlarmDialogBinding.timepicker.hour
        val minute: Int = fabAlarmDialogBinding.timepicker.minute
        val enable: Boolean = true
        val repeat: String = fabAlarmDialogBinding.repeatTxt2.text.toString()
        //Log.d(TAG, "addAlarmInSql: " + hour.toString())
    }

}