package com.kaplan57.additemstorecyclerview.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.DialogFragment
import com.kaplan57.additemstorecyclerview.R
import com.kaplan57.additemstorecyclerview.databinding.AlertDialogBinding
import com.kaplan57.additemstorecyclerview.eventbus.OnItemAddedEvent
import org.greenrobot.eventbus.EventBus

class AddTextDialog() : DialogFragment() {
    lateinit var binding:AlertDialogBinding


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = AlertDialogBinding.inflate(layoutInflater)
        setFocus()
        val dialog = setUpDialogBuilder()

        setListeners()
        return dialog.create()
    }

    private fun setUpDialogBuilder(): AlertDialog.Builder {
        return AlertDialog.Builder(activity).setMessage("Write text to add to the list!")
            .setView(binding.root)
            .setIcon(R.drawable.ic_add).setPositiveButton("Add", DialogInterface.OnClickListener { dialog, id->
                addTextToTheList()
            })
            .setNegativeButton("Cancel",DialogInterface.OnClickListener{ dialog, id->
                dismissTheDialog()
            })

    }

    private fun setFocus() {
        binding.editTextTitle.requestFocus()
    }

    private fun dismissTheDialog() {
        dialog!!.dismiss()
    }

    private fun addTextToTheList() {
//        EventBus.getDefault().postSticky(OnItemAddedEvent(binding.editTextTitle.text.toString()))
        dismissTheDialog()
    }

    private fun setListeners() {
        binding.editTextTitle.setOnKeyListener(this::onEnterKeyPressed)
    }

    private fun onEnterKeyPressed(view: View,i: Int, keyEvent: KeyEvent?): Boolean {
        if(keyEvent!!.keyCode == KeyEvent.KEYCODE_ENTER){
            binding.editTextNote.requestFocus()
        }
        return true
    }


}

