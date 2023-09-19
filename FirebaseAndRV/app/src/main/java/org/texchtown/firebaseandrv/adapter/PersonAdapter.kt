package org.texchtown.firebaseandrv.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.texchtown.firebaseandrv.data.Person
import org.texchtown.firebaseandrv.databinding.RvUserListItemBinding

class PersonAdapter(private val personList: List<Person>)
    : RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {

    inner class PersonViewHolder(private val binding: RvUserListItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

            fun bindItem(info: Person) {
                binding.edtAge.text = info.age.toString()
                binding.edtFirstName.text = info.first
                binding.edtLastName.text = info.last
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        return PersonViewHolder(
            RvUserListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bindItem(personList[position])
    }

    override fun getItemCount(): Int {
        return personList.size
    }
}