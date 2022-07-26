package com.kaplan57.additemstorecyclerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import com.kaplan57.additemstorecyclerview.databinding.ActivityNoteBinding
import com.kaplan57.additemstorecyclerview.datamodel.TextModel
import com.kaplan57.additemstorecyclerview.eventbus.OnItemAddedEvent
import org.greenrobot.eventbus.EventBus

class NoteActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivityNoteBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getResult()
        setContentView(binding.root)
        setListeners()

    }

    private fun getResult() {
        val intent:Intent = intent
        val position:Int = intent.getIntExtra("position",0)
        getReadyTexts(position)
    }

    private fun getReadyTexts(position: Int) {
        val instance = TextModel.getTextInstance()
        binding.editTextTitle.setText(instance.getTextList()[position])
        binding.editTextNote.setText( instance.getSubTextList()[position])

    }

    private fun setListeners() {
        binding.editTextTitle.setOnKeyListener(this::onEnterKeyPressed)
        binding.add.setOnClickListener(this::addTextToTheList)
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

    private fun addTextToTheList(view: View) {
        if(binding.editTextTitle.text.toString() != "") {
            EventBus.getDefault()
                .postSticky(OnItemAddedEvent(binding.editTextTitle.text.toString(),binding.editTextNote.text.toString()))
            this.onBackPressed()
        }else
            Toast.makeText(this,"Title section can't be empty!",Toast.LENGTH_LONG).show()
    }
}