package org.texchtown.starbucks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var extendedFloatingActionButton: ExtendedFloatingActionButton
    private lateinit var nestedScrollView: NestedScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        extendedFloatingActionButton = findViewById(R.id.ext_fab)
        nestedScrollView = findViewById(R.id.nested_sv)

        nestedScrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if(scrollY >0) {
                extendedFloatingActionButton.shrink()
            }
            else {
                extendedFloatingActionButton.extend()
            }
        }

        val window = this.window
        window.statusBarColor = ContextCompat.getColor(this, R.color.toolbar_color)
    }
}