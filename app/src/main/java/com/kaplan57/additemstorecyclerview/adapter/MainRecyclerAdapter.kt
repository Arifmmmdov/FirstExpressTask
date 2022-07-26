package com.kaplan57.additemstorecyclerview.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView
import com.kaplan57.additemstorecyclerview.R
import com.kaplan57.additemstorecyclerview.datamodel.RemovableElements
import com.kaplan57.additemstorecyclerview.datamodel.TextModel
import com.kaplan57.additemstorecyclerview.eventbus.OnItemAddedEvent
import com.kaplan57.additemstorecyclerview.eventbus.OnRecycItemClickedEvent
import org.greenrobot.eventbus.EventBus

class MainRecyclerAdapter(
    private val textList:ArrayList<String>, private var show:Boolean) : RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder>() {

    private val TAG = "MyTagHere"

    fun changeCheckBoxVisibility(){
        show = !show
        notifyItemRangeChanged(0,textList.size)
    }

    fun addItem(item:String,subItem:String){
        val instance = TextModel.getTextInstance()
        instance.addText(item)
        instance.addSubText(subItem)
        textList.add(item)
        notifyItemInserted(textList.size-1)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val itemText:TextView = itemView.findViewById(R.id.itemText)
        private val checkBox:CheckBox = itemView.findViewById(R.id.checkbox)
        private val recyclerItem: LinearLayoutCompat = itemView.findViewById(R.id.itemText)


        fun setText(textList: ArrayList<String>,position: Int){
            itemText.text= textList[position]
        }

        fun checkBoxVisibility(show: Boolean){
            checkBox.isChecked = false
            if(!show)
                checkBox.visibility = View.GONE
            else
                checkBox.visibility = View.VISIBLE
        }

        fun setListeners(position: Int) {


            checkBox.setOnCheckedChangeListener { _, _ ->
                declareDeletedItems(position)
            }

            recyclerItem.setOnClickListener{
                EventBus.getDefault().postSticky(OnRecycItemClickedEvent(position))
            }
        }

        private fun declareDeletedItems(position: Int) {
            val instances = RemovableElements.myInstance()
            val map = instances.myMap()

            if(!checkBox.isSelected && map[position] == null){
                instances.put(position,true)
            }
            else if(checkBox.isSelected){
                instances.removeAt(position)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recyc_item,parent,false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setText(textList,position)
        holder.checkBoxVisibility(show)
        holder.setListeners(position)

    }

    override fun getItemCount(): Int = textList.size

    fun deleteSelectedItems() {
        val instance = RemovableElements.myInstance()
        val selectedItems = instance.myMap()
        val reverseOrder = selectedItems.toSortedMap(reverseOrder())
        Log.d(TAG, "deleteSelectedItems: $reverseOrder")
        
        reverseOrder.forEach{
            Log.d(TAG, "deleteSelectedItems: ${it.key}")
            textList.removeAt(it.key)
            instance.removeAt(it.key)
            notifyItemRemoved(it.key)
        }

    }

}