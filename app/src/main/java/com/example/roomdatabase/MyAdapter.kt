package com.example.roomdatabase

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.roomdatabase.databinding.ItemBinding

class MyAdapter(private val list: MutableList<PersonEntity>, val events: UserEvents) :
    RecyclerView.Adapter<MyAdapter.MyHolder>() {
    inner class MyHolder(private var mBinding: ItemBinding) :
        RecyclerView.ViewHolder(mBinding.root) {
        fun bind(personEntity: PersonEntity) {
            mBinding.personNameTv.text = personEntity.name
            mBinding.personAgeTv.text = personEntity.age.toString()
            mBinding.personCityTv.text = personEntity.city
            mBinding.editBtn.setOnClickListener {
                events.edit(personEntity)
            }
            mBinding.deleteBtn.setOnClickListener {
                events.delete(personEntity)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(list[position])
    }

    fun updateList(newList: MutableList<PersonEntity>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    interface UserEvents {
        fun edit(personEntity: PersonEntity)
        fun delete(personEntity: PersonEntity)
    }
}