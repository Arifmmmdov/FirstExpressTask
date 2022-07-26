package com.kaplan57.additemstorecyclerview.datamodel

class TextModel {
    companion object{
        private var instances:TextModel? = null
        private var textList:ArrayList<String>? = null
        private var subText:ArrayList<String>? =  null

        fun getTextInstance():TextModel{
            if(instances == null)
                instances = TextModel()

            return instances as TextModel
        }
    }


    fun getTextList():ArrayList<String> {
        if(textList == null)
            textList = ArrayList<String>()

        return textList as ArrayList<String>
    }

    fun getSubTextList():ArrayList<String> {
        if(subText == null)
            subText = ArrayList<String>()

        return subText as ArrayList<String>
    }

    fun addSubText(text:String){
        subText!!.add(text)
    }

    fun addText(text:String){
        textList!!.add(text)
    }
}