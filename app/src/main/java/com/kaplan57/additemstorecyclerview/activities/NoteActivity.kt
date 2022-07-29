package com.kaplan57.additemstorecyclerview.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import com.kaplan57.additemstorecyclerview.databinding.ActivityNoteBinding
import com.kaplan57.additemstorecyclerview.datamodel.TextModel
import com.kaplan57.additemstorecyclerview.eventbus.OnItemAddedEvent
import com.kaplan57.additemstorecyclerview.eventbus.OnItemUpdatedEvent
import org.greenrobot.eventbus.EventBus

class NoteActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivityNoteBinding.inflate(layoutInflater)
    }
    private val position by lazy {
        intent.getIntExtra("position",57)
    }

    private val instance by lazy {
        TextModel.getTextInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setListeners()
        getReadyTexts(position)
    }

    private fun getReadyTexts(position: Int) {
        if(position != 57) {
            val instance = TextModel.getTextInstance()
            binding.add.text = "Update"
            binding.editTextTitle.setText(instance.getTextList()[position])
            binding.editTextNote.setText(instance.getSubTextList()[position])
        }
    }

    private fun setListeners() {
        binding.editTextTitle.setOnKeyListener(this::onEnterKeyPressed)
        binding.add.setOnClickListener(this::addOrUpdateTheList)
        binding.cancel.setOnClickListener(this::onCancelClicked)
    }

    private fun onCancelClicked(view: View) {
        this.onBackPressed()
    }

    private fun onEnterKeyPressed(view: View, i: Int, keyEvent: KeyEvent): Boolean {
        if(keyEvent.keyCode == KeyEvent.KEYCODE_ENTER){
            binding.editTextNote.requestFocus()
        }
        return true
    }

    private fun addOrUpdateTheList(view: View) {
        if(binding.editTextTitle.text.toString() != "") {
            if(position != 57){
                Log.d("MyTagHere", "addOrUpdateTheList: $position")
                callEventBusUpdatedMethod()
                updateTextList()
            }else{
                callEventBusAddedMethod()
                addToTheTextList()
                }


            this.onBackPressed()
        }else
            Toast.makeText(this,"Title section can't be empty!",Toast.LENGTH_LONG).show()
    }

    private fun addToTheTextList() {
        instance.addText(binding.editTextTitle.text.toString(),binding.editTextNote.text.toString())
    }

    private fun callEventBusUpdatedMethod() {
        EventBus.getDefault().postSticky(OnItemUpdatedEvent(position))
        Log.d("MyTagHere", "callEventBusUpdatedMethod: $position")
    }

    private fun updateTextList() {
        instance.updateItems(position,binding.editTextTitle.text.toString(),binding.editTextNote.text.toString())
        Log.d("MyTagHere", "addOrUpdateTheList: $position")
    }

    private fun callEventBusAddedMethod() {
        EventBus.getDefault()
            .postSticky(OnItemAddedEvent(binding.editTextTitle.text.toString(),binding.editTextNote.text.toString()))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("MyTagHere", "onActivityResult: ")
    }

    fun CallIntentMethod(){
        //Todo: how to call another activity without EventBus
    }

}