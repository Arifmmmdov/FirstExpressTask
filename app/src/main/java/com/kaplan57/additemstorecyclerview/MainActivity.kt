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
import com.kaplan57.additemstorecyclerview.dialog.AddTextDialog
import com.kaplan57.additemstorecyclerview.eventbus.OnItemAddedEvent
import com.kaplan57.additemstorecyclerview.eventbus.OnRecycItemClickedEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private val textModelInstance: TextModel by lazy {
        TextModel.getTextInstance()
    }
    private lateinit var binding: ActivityMainBinding
    private  lateinit var adapter: MainRecyclerAdapter
    private var isRemoveSection: Boolean = true


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
        adapter = MainRecyclerAdapter(textModelInstance.getTextList(),false)

        setUpRecyclerView()
        setListeners()
    }

    private fun addItemsToTheList() {
        val textList = textModelInstance.getTextList()
        textList.add("Text1")
        textList.add("Text2")
        textList.add("Text3")
        textList.add("Text4")

        val subTextList = textModelInstance.getSubTextList()
        subTextList.add("SubText1")
        subTextList.add("SubText2")
        subTextList.add("SubText3")
        subTextList.add("SubText4")
    }

    private fun setUpRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun setListeners() {

        binding.btnLeft.setOnClickListener {
            startActivity()
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

    private fun startActivity() {
        val intent = Intent(this,NoteActivity::class.java)
        startActivity(intent)
    }


//    private fun showDialog() {
//        AddTextDialog().show(supportFragmentManager,"alertDialog")
//    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    fun onItemAddedEvent(event:OnItemAddedEvent){
        adapter.addItem(event.text,event.subText)
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    fun onMoveToNoteActivity(event:OnRecycItemClickedEvent){
        val intent = Intent(this,NoteActivity::class.java)
        intent.putExtra("position",event.position)
        startActivityForResult(intent,12)
    }

}