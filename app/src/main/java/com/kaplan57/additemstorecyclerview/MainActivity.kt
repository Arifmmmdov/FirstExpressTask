package com.kaplan57.additemstorecyclerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaplan57.additemstorecyclerview.adapter.MainRecyclerAdapter
import com.kaplan57.additemstorecyclerview.databinding.ActivityMainBinding
import com.kaplan57.additemstorecyclerview.datamodel.TextModel
import com.kaplan57.additemstorecyclerview.eventbus.OnItemAddedEvent
import com.kaplan57.additemstorecyclerview.eventbus.OnItemUpdatedEvent
import com.kaplan57.additemstorecyclerview.eventbus.OnRecycItemClickedEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : AppCompatActivity() {
    private val textModelInstance: TextModel by lazy {
        TextModel.getTextInstance()
    }
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private  val adapter by lazy {
        MainRecyclerAdapter(textModelInstance.getTextList(),false)
    }
    private var isRemoveSection: Boolean = true
    private val TAG = "MyTagHere"


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
        setContentView(binding.root)


        addItemsToTheList()
        setUpRecyclerView()
        setListeners()
    }

    private fun addItemsToTheList() {
        val instance = TextModel.getTextInstance()
        initializeTextLists(instance)
        Log.d(TAG, "addItemsToTheList: ")

        instance.addText("Text1")
        instance.addText("Text2","Note2")
        instance.addText("Text3","Note3")
        instance.addText("Text4","Note4")
        instance.addText("Text5","Note5")
    }

    private fun initializeTextLists(instance: TextModel) {
        instance.getSubTextList()
        instance.getTextList()
    }

    private fun setUpRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun setListeners() {

        binding.btnLeft.setOnClickListener {
            moveToAnotherActivity()
        }

        binding.btnRight.setOnClickListener {
            if(isRemoveSection) {
                binding.btnRight.background = ResourcesCompat.getDrawable(resources,R.drawable.ic_check,null)
                isRemoveSection = false
            }
            else{
                binding.btnRight.background = ResourcesCompat.getDrawable(resources,R.drawable.ic_remove,null)
                adapter.deleteSelectedItems()
                isRemoveSection = true
            }
            adapter.changeCheckBoxVisibility()
        }
    }

    private fun moveToAnotherActivity() {
        val intent = Intent(this,NoteActivity::class.java)
        startActivity(intent)
    }


//    private fun showDialog() {
//        AddTextDialog().show(supportFragmentManager,"alertDialog")
//    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    fun onItemAddedEvent(event:OnItemAddedEvent){
        adapter.addItem(textModelInstance.getTextList())
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = false)
    fun onMoveToNoteActivity(event:OnRecycItemClickedEvent){
        val intent = Intent(this,NoteActivity::class.java)
        Log.d(TAG, "onMoveToNoteActivity: ${event.position}")
        intent.putExtra("position",event.position)
        startActivityForResult(intent,12)
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onItemUpdatedEvent(event:OnItemUpdatedEvent){
        Log.d(TAG, "onItemUpdatedEvent: ${event.position}")
        adapter.updateAdapterVisibility(textModelInstance.getTextList(),event.position)
    }

}