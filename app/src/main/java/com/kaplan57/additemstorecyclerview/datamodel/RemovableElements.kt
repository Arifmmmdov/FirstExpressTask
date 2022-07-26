package com.kaplan57.additemstorecyclerview.datamodel

import android.util.Log

class RemovableElements {
    private var map:HashMap<Int,Boolean>? = null
    private val TAG = "MyTagHere"

    companion object{
        private var instance: RemovableElements? = null

        fun myInstance():RemovableElements{
            if(instance == null){
                instance = RemovableElements()
            }

            return instance as RemovableElements
        }
    }

    fun myMap():HashMap<Int,Boolean>{
        if(map == null){
            Log.d(TAG, "myMap: MyLog")
            map = HashMap<Int,Boolean>()
        }

        return map as HashMap<Int, Boolean>
    }

    fun put(position: Int,check:Boolean){
        map!![position] = check
    }

    fun removeAt(position: Int){
        map!!.remove(position)
    }
}