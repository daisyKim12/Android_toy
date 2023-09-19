package org.texchtown.naverblog

import android.R
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.texchtown.naverblog.databinding.ActivityMainBinding
import org.texchtown.naverblog.databinding.CustomDialogBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var dialogBinding: CustomDialogBinding

    val TITLE = "title"
    val DETAIL = "detail"
    val SAVED_NUM = "current_page_num"
    val COUNT = "count"
    val AUTO = "auto_save"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.roundBtn5.setOnClickListener {
            //TODO dialog
            val intent = Intent(this, WriteActivity::class.java)
            if(detectAutoSave()){
                showCustomDialog()
            } else {
                startActivity(intent)
                finish()
            }
        }
    }

    private fun detectAutoSave() : Boolean{
        val sharedPreferences = getSharedPreferences(AUTO, MODE_PRIVATE)
        val title = sharedPreferences.getString(TITLE, "error")
        val detail = sharedPreferences.getString(DETAIL, "error")
        if(title == "error" && detail == "error"){
            return false
        }
        return true
    }


    //Function to display the custom dialog.
    private fun showCustomDialog() {
        val dialog = Dialog(this)
        //We have added a title in the custom layout. So let's disable the default title.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
        dialog.setCancelable(true)
        //Mention the name of the layout of your custom dialog.
        dialogBinding = CustomDialogBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.btn2.setOnClickListener {
            val intent = Intent(this, WriteActivity::class.java)

            intent.putExtra(AUTO, true)
            startActivity(intent)
            dialog.dismiss()
            finish()
        }
        dialogBinding.btn1.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

}