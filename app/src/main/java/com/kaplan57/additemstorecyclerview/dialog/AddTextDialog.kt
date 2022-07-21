package com.kaplan57.additemstorecyclerview.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.kaplan57.additemstorecyclerview.R
import com.kaplan57.additemstorecyclerview.databinding.AlertDialogBinding
import com.kaplan57.additemstorecyclerview.eventbus.OnTextAdded
import org.greenrobot.eventbus.EventBus

class AddTextDialog() : DialogFragment() {
    lateinit var binding:AlertDialogBinding


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = AlertDialogBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(activity).setMessage("Write text to add to the list!")
            .setView(binding.root)
            .setIcon(R.drawable.ic_add).setPositiveButton("Add", DialogInterface.OnClickListener { dialog, id->
                EventBus.getDefault().postSticky(OnTextAdded(binding.textInputEditText.text.toString()))
            })
            .setNegativeButton("Cancel",DialogInterface.OnClickListener{ dialog, id->
                dialog.dismiss()
            })
        return dialog.create()
    }
}
