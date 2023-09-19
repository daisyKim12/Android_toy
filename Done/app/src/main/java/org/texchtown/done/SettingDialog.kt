package org.texchtown.done

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RadioGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.DialogFragment

class SettingDialog : DialogFragment() {
    //widget
    var rg_sort: RadioGroup? = null
    var swt_mode: SwitchCompat? = null
    var swt_ntf: SwitchCompat? = null

    //val
    var sharedPreferences: SharedPreferences? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var settingDialog: Dialog? = null
        settingDialog = Dialog(requireContext())
        settingDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        settingDialog!!.setContentView(R.layout.dialog_setting)
        rg_sort = settingDialog!!.findViewById(R.id.rg_sort)
        swt_mode = settingDialog!!.findViewById(R.id.swt_setting_bg)
        swt_ntf = settingDialog!!.findViewById(R.id.swt_setting_ntf)

        //show current setting data
        sharedPreferences = requireActivity().getSharedPreferences("SettingPrefs", Context.MODE_PRIVATE)
        //read data from shared preference and initialize widgets
        readSettingData()
        var btn_save: Button? = null
        btn_save = settingDialog!!.findViewById(R.id.setting_save_btn)
        btn_save.setOnClickListener(View.OnClickListener { //save setting change
            saveSettingData()
            settingDialog!!.dismiss()
        })
        return settingDialog
    }

    fun readSettingData() {
        val sort = sharedPreferences!!.getInt("sort", -1)
        val mode = sharedPreferences!!.getBoolean("mode", false)
        val notification = sharedPreferences!!.getBoolean("notification", false)
        rg_sort!!.check(sort)
        swt_mode!!.isChecked = mode
        swt_ntf!!.isChecked = notification
    }

    fun saveSettingData() {
        val editor = sharedPreferences!!.edit()
        val sort = rg_sort!!.checkedRadioButtonId
        val mode = swt_mode!!.isChecked
        val notification = swt_ntf!!.isChecked
        editor.putInt("sort", sort)
        editor.putBoolean("mode", mode)
        editor.putBoolean("notification", notification)
        editor.commit()
        checkSettingData(sort, mode, notification)
    }

    private fun checkSettingData(sort: Int, mode: Boolean, ntf: Boolean) {
        Log.d(TAG, "checkSettingData(sort): $sort")
        Log.d(TAG, "checkSettingData(mode): $mode")
        Log.d(TAG, "checkSettingData(ntf): $ntf")
    }

    companion object {
        //TAG
        private const val TAG = "SettingDialog"
    }
}