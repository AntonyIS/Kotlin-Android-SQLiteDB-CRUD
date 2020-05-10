package com.example.savingdatasqlite

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

data class UserModel(val name:String, val profession:String, val residence :String, val password:String) {
    final var thename:String
        get() :String{
            return name
        }

    init {
        this.thename = name
    }


}
fun show_message(title: String, message:String, context:Context){
    val alertDialog: AlertDialog.Builder = AlertDialog.Builder(context)
    alertDialog.setCancelable(false)
    alertDialog.setTitle(title)
    alertDialog.setMessage(message)
    alertDialog.setPositiveButton("Ok", DialogInterface.OnClickListener{ dialog, which -> dialog.dismiss() })
    alertDialog.create().show()
}