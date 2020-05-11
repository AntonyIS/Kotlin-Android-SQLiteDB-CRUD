package com.example.savingdatasqlite


import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.openOrCreateDatabase
import android.net.sip.SipSession
import android.util.Log
import kotlinx.android.synthetic.main.activity_data.*
import kotlinx.android.synthetic.main.alert_dialog_form.view.*


/**
 * Created by Belal on 6/19/2017.
 */

class CustomAdapter(var context: Context, var data: ArrayList<UserModel>): BaseAdapter() {
    internal var dbHelper = DatabaseHelper(context)
    private class ViewHolder(row:View?){
        var name:TextView
        var profession:TextView
        var residence:TextView
        var password:TextView
        var imgdelete:ImageView
        var imgupdate:ImageView


        init {

            this.name = row?.findViewById(R.id.tvname) as TextView
            this.profession = row?.findViewById(R.id.tvprofession) as TextView
            this.residence = row?.findViewById(R.id.tvresidence) as TextView
            this.password = row?.findViewById(R.id.tvpassword) as TextView
            this.imgdelete = row?.findViewById(R.id.imgdelete) as ImageView
            this.imgupdate = row?.findViewById(R.id.imgupdate) as ImageView


        }
    }



    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view:View?
        val viewHolder:ViewHolder
        if (convertView == null){
            val layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.list_layout,parent,false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }else{
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        val usermodel:UserModel = getItem(position) as UserModel
        viewHolder.name.text = usermodel.name
        viewHolder.profession.text = usermodel.profession
        viewHolder.residence.text = usermodel.residence
        viewHolder.password.text = usermodel.password
        val name = usermodel.name
        val profession = usermodel.profession
        val residence = usermodel.residence
        val password = usermodel.password
        val id:String

        viewHolder.imgupdate.setOnClickListener {
            var dialogBuilder = AlertDialog.Builder(context)
            var myInflater = LayoutInflater.from(context)
            var dialogview = myInflater.inflate(R.layout.alert_dialog_form,parent,false)
            dialogBuilder.setView(dialogview)

//            populate the edittexts with data

            dialogview.detname.setText(name)
            dialogview.detprofession.setText(profession)
            dialogview.detresidence.setText(residence)
            dialogview.detpassword.setText(password)

            dialogBuilder.setTitle("Edit")
            dialogBuilder.setMessage("Editing ${name}?")
            dialogBuilder.setPositiveButton("Edit", {dialog, which ->

                val updatedname = dialogview.detname.text.toString()
                val updatedprofession = dialogview.detprofession.text.toString()
                val updatedresidence = dialogview.detresidence.text.toString()
                val updateddetpassword = dialogview.detpassword.text.toString()

                val cursor = dbHelper.allData

                while (cursor.moveToNext()) {
                    if (name == cursor.getString(1)){
                        dbHelper.updateData(
                            cursor.getString(0),
                            updatedname,
                            updatedprofession,
                            updatedresidence,
                            updateddetpassword
                        )
                    }

                }
                this.notifyDataSetChanged()

                Toast.makeText(context, "${ name} Updated successfully", Toast.LENGTH_SHORT).show()

            })

            dialogBuilder.setNegativeButton("Cancel", {dialog, which -> dialog.dismiss() })
            dialogBuilder.create().show()

        }

        viewHolder.imgdelete.setOnClickListener {

            val dialogBuilder = AlertDialog.Builder(context)
            dialogBuilder.setTitle("Delete")
            dialogBuilder.setMessage("Confirm Delete item ${name}?")

            dialogBuilder.setPositiveButton("Delete", {dialog, which ->

                dbHelper.deleteData(name)
                val users: ArrayList<UserModel> = ArrayList()

                val cursor = dbHelper.allData

//            check if there are any records from the database
                if (cursor.count == 0) {
                    show_message("No records ", "Sorry no records were found !!", context)
                } else {
                    while (cursor.moveToNext()) {
                        users.add(
                            UserModel(
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3),
                                cursor.getString(4)
                            )
                        )

                    }
                    this.notifyDataSetChanged()
                }

            })
            dialogBuilder.setNegativeButton("Cancel", {dialog, which -> dialog.dismiss() })
            dialogBuilder.create().show()

        }



        return view as View
    }

    override fun getItem(position: Int): Any {
        return  data.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return data.count()
    }









}


