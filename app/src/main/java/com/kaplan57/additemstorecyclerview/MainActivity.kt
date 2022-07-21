package com.kaplan57.additemstorecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaplan57.additemstorecyclerview.adapter.MainRecyclerAdapter
import com.kaplan57.additemstorecyclerview.databinding.ActivityMainBinding
import com.kaplan57.additemstorecyclerview.dialog.AddTextDialog
import com.kaplan57.additemstorecyclerview.eventbus.OnItemRemovedEvent
import com.kaplan57.additemstorecyclerview.eventbus.OnTextAdded
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val textList = ArrayList<String>()
    lateinit var adapter: MainRecyclerAdapter
    val TAG = "MyTagHere"

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        addItemsToTheList()
        Log.d(TAG, "onCreate: ${textList}")
        adapter = MainRecyclerAdapter(textList,false)

        setUpRecyclerView()
        setListeners()
    }

    private fun addItemsToTheList() {
        textList.add("Text1")
        textList.add("Text2")
        textList.add("Text3")
        textList.add("Text4")
    }

    private fun setUpRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun setListeners() {
        Log.d(TAG, "setListeners: RecyclerView clicked")

        binding.btnAdd.setOnClickListener {
            AddTextDialog().show(supportFragmentManager,"alertDialog")
        }

        binding.btnRemove.setOnClickListener {
            adapter.show = !adapter.show
            adapter.notifyItemRangeChanged(0,textList.size)
        }
    }


    @Subscribe
    fun onItemRemovedEvent(event: OnItemRemovedEvent){
        textList.removeAt(event.position)
        adapter.notifyItemRemoved(event.position)
    }

    @Subscribe
    fun onTextAddedEvent(textAddedEvent: OnTextAdded){
        textList.add(textAddedEvent.text)
        adapter.notifyItemInserted(textList.size-1)
    }
}