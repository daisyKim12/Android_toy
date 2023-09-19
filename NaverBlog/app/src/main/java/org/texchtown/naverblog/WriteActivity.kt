package org.texchtown.naverblog

import android.content.Intent
import android.os.Bundle
import android.telecom.Call
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.texchtown.naverblog.databinding.ActivityWriteBinding


class WriteActivity : AppCompatActivity() {
    
    val TAG = "WRITE"
    private lateinit var binding: ActivityWriteBinding
    val TITLE = "title"
    val DETAIL = "detail"
    val SAVED_NUM = "current_page_num"
    val COUNT = "count"
    val AUTO = "auto_save"
    private var title: String? = null
    private var detail: String? = null
    private var current = 0
    private var AutoSaveFlag = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "onCreate: ")

        AutoSaveFlag = intent.getBooleanExtra(AUTO,false)
        if(AutoSaveFlag){
            uploadAutoSave()
        }

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
        current = getCount()
        updateCount(current)
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")

        //취소 버튼
        binding.textView.setOnClickListener {
            //TODO Dialog

            // dialog에서 임시저장을 누르면
            // saveData(current)
            // current = current + 1
            // saveCount(current)
        }

        binding.editText.setOnFocusChangeListener { view, hasFocus ->
            if(hasFocus)
                binding.imageViewAddPhoto.visibility = View.VISIBLE
            else
                binding.imageViewAddPhoto.visibility = View.INVISIBLE
        }

        binding.textView15.setOnClickListener {
            saveData(current)
            current = current + 1
            saveCount(current)
            updateCount(current)
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        //at onStop temporary getText and at onDestroy save
        title=binding.editText.getText().toString()
        detail=binding.editTextDetail.getText().toString()
        Log.d(TAG, "onStop: ")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
        if(title != null && detail != null){
            autoSave()
            Log.d(TAG, "onDestroy: saved")
        } else {
            //deleteAutoSave()
        }
    }

    fun autoSave(){
        val sharedPreferences = getSharedPreferences(AUTO, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(TITLE, binding.editText.getText().toString())
        editor.putString(DETAIL, binding.editTextDetail.getText().toString())
        editor.apply()
    }

    fun uploadAutoSave(){
        val sharedPreferences = getSharedPreferences(AUTO, MODE_PRIVATE)
        title = sharedPreferences.getString(TITLE, "")
        detail = sharedPreferences.getString(DETAIL, "")
        binding.editText.setText(title)
        binding.editTextDetail.setText(detail)
    }

    fun deleteAutoSave(){

    }

    private fun saveCount(num: Int){
        val sharedPreferences = getSharedPreferences(SAVED_NUM, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(COUNT, num)
        editor.apply()
    }
    private fun getCount(): Int{
        val sharedPreferences = getSharedPreferences(SAVED_NUM, MODE_PRIVATE)
        val past = sharedPreferences.getInt(COUNT, -1)
        if(past == -1)
            return 0
        return past
    }

    private fun saveData(pageNum: Int) {
        val sharedPreferences = getSharedPreferences(pageNum.toString(), MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(TITLE, binding.editText.getText().toString())
        editor.putString(DETAIL, binding.editTextDetail.getText().toString())
        editor.apply()
        savingDialogAndToast()
    }

    private fun getSavedWriting(pageNum: Int) {
        val sharedPreferences = getSharedPreferences(pageNum.toString(), MODE_PRIVATE)
        title = sharedPreferences.getString(TITLE, "error")
        detail = sharedPreferences.getString(DETAIL, "error")
    }

    private fun updateCount(num: Int) {
        binding.textView17.text = num.toString()
    }

    private fun updateViews(pageNum: Int) {
        if(title != null) {
            binding.editText.setText(title)
        }
        if(detail != null) {
            binding.editTextDetail.setText(detail)
        }
    }

    private fun savingDialogAndToast(){
        //TODO dialog

        Toast.makeText(this, "저장이 완료되었습니다", Toast.LENGTH_SHORT).show()

    }




}
