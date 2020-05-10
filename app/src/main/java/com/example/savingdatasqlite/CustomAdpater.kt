package com.example.savingdatasqlite


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.openOrCreateDatabase
import android.util.Log


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

        init {

            this.name = row?.findViewById(R.id.tvname) as TextView
            this.profession = row?.findViewById(R.id.tvprofession) as TextView
            this.residence = row?.findViewById(R.id.tvresidence) as TextView
            this.password = row?.findViewById(R.id.tvpassword) as TextView
            this.imgdelete = row?.findViewById(R.id.imgdelete) as ImageView

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

        viewHolder.imgdelete.setOnClickListener {
            val pos = this.getItemId(position)

            try {
                dbHelper.deleteData(pos.toInt().toString())
                Toast.makeText(context, "${pos} is Deleted", Toast.LENGTH_SHORT).show()

            }catch (e: Exception){
                e.printStackTrace()
                Toast.makeText(context, "${e.message.toString()}", Toast.LENGTH_SHORT).show()

            }


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

    fun  getIntID(position: Long):Int{
        return position.toInt()
    }


}


