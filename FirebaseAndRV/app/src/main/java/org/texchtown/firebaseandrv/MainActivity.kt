package org.texchtown.firebaseandrv

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import org.texchtown.firebaseandrv.adapter.PersonAdapter
import org.texchtown.firebaseandrv.data.Person
import org.texchtown.firebaseandrv.databinding.ActivityMainBinding
import org.texchtown.firebaseandrv.databinding.RvUserListItemBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: DatabaseReference
    private lateinit var adapter: PersonAdapter
    private lateinit var personList: MutableList<Person>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get database ref
        database = FirebaseDatabase.getInstance().getReference("Users")

        binding.rvUserList.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvUserList.layoutManager = linearLayoutManager

        personList = mutableListOf<Person>()
        Log.d(TAG, "onCreate: " + personList)
        adapter = PersonAdapter(personList.toList())
        binding.rvUserList.adapter = adapter

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapShot in snapshot.children){
                    val person: Person? = dataSnapShot.getValue(Person::class.java)
                    //Log.d(TAG, "onDataChange: " + person?.first)
                    personList.add(person!!)
//                        Log.d(TAG, "onDataChange: " + personList

                    adapter.notifyDataSetChanged()

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


    }
}