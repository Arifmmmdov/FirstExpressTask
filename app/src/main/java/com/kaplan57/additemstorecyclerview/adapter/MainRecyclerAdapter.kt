package com.kaplan57.additemstorecyclerview.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kaplan57.additemstorecyclerview.R
import com.kaplan57.additemstorecyclerview.eventbus.OnItemRemovedEvent
import org.greenrobot.eventbus.EventBus

class MainRecyclerAdapter(
    val textList:ArrayList<String>, var show:Boolean) : RecyclerView.Adapter<MainRecyclerAdapter.MyViewHolder>() {


    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val checkBox = itemView.findViewById<CheckBox>(R.id.checkbox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recyc_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.itemText).text = textList[position]
        Log.d("MyTagHere1", "onBindViewHolder: "+textList[position])

        if(show)
            holder.checkBox.visibility = View.VISIBLE
        else
            holder.checkBox.visibility = View.GONE


        holder.checkBox.setOnClickListener {
            EventBus.getDefault().postSticky(OnItemRemovedEvent(position))
        }
    }

    override fun getItemCount(): Int {
        return textList.size
    }
}