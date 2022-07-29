package com.kaplan57.additemstorecyclerview.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaplan57.additemstorecyclerview.R
import com.kaplan57.additemstorecyclerview.adapter.CallOnClicked
import com.kaplan57.additemstorecyclerview.adapter.MainRecyclerAdapter
import com.kaplan57.additemstorecyclerview.databinding.ActivityMainBinding
import com.kaplan57.additemstorecyclerview.eventbus.OnItemAddedEvent
import com.kaplan57.additemstorecyclerview.eventbus.OnItemUpdatedEvent
import com.kaplan57.additemstorecyclerview.room.entity.databasebuilder.NoteDatabaseBuilder
import com.kaplan57.additemstorecyclerview.room.entity.entity.NoteEntity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : AppCompatActivity(),CallOnClicked {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var adapter:MainRecyclerAdapter
//    private  val adapter by lazy {
//        MainRecyclerAdapter(textModelInstance.getTextList(),false,this)
//    }
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
        getDataFromTheRoom()
        setListeners()
    }

    private fun addItemsToTheList() {
        NoteDatabaseBuilder.getDatabaseBuilder(this).userDao().add(listOf(
            NoteEntity(0,"Text1",""),
            NoteEntity(0,"Text2","Note2"),
            NoteEntity(0,"Text3","Note3"),
            NoteEntity(0,"Text4","Note4"),
            NoteEntity(0,"Text5","Note5"),
            NoteEntity(0,"Text6","Note6"),
            NoteEntity(0,"Text7","Note7"),
        )
        )
//        TextModel.getTextInstance().getTextList() as List<NoteEntity> = listOf(
//            NoteEntity("Text1",""),
//            NoteEntity("Text2","Note2"),
//            NoteEntity("Text3","Note3"),
//            NoteEntity("Text4","Note4"),
//            NoteEntity("Text5","Note5")
//        )
//        initializeTextLists(instance)
//        Log.d(TAG, "addItemsToTheList: ")
//
//        instance.addText()
//        instance.addText()
//        instance.addText()
//        instance.addText()
//        instance.addText()
    }


    private fun getDataFromTheRoom() {

        var list = NoteDatabaseBuilder.getDatabaseBuilder(this).userDao().getNotes()
        setUpRecyclerView(list)
    }

    private fun setUpRecyclerView(list: List<NoteEntity>) {
        adapter = MainRecyclerAdapter(list,false,this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun setListeners() {

        binding.btnLeft.setOnClickListener {
            moveToAnotherActivity()
        }

        binding.btnRight.setOnClickListener {
            if(isRemoveSection) {
                binding.btnRight.background = ResourcesCompat.getDrawable(resources, R.drawable.ic_check,null)
                isRemoveSection = false
            }
            else{
                binding.btnRight.background = ResourcesCompat.getDrawable(resources, R.drawable.ic_remove,null)
                adapter.deleteSelectedItems()
                isRemoveSection = true
            }
            adapter.changeCheckBoxVisibility()
        }
    }

    private fun moveToAnotherActivity() {
        val intent = Intent(this, NoteActivity::class.java)
        startActivity(intent)
    }


//    private fun showDialog() {
//        AddTextDialog().show(supportFragmentManager,"alertDialog")
//    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    fun onItemAddedEvent(event:OnItemAddedEvent){
        adapter.addItem(textModelInstance.getTextList())
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onItemUpdatedEvent(event:OnItemUpdatedEvent){
        adapter.updateAdapterVisibility(event.title,event.position)
    }

    override fun onItemClicked(position:Int) {
        val intent = Intent(this, NoteActivity::class.java)
        intent.putExtra("position",position)
        startActivity(intent)
    }

}