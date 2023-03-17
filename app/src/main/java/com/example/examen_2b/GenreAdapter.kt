package com.example.examen_2b

import android.content.ContentValues.TAG
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class GenreAdapter(
    private val context: MainActivity,
    private val list: ArrayList<Any>
) : RecyclerView.Adapter<GenreAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val genreName: TextView
        val startPeriod: TextView
        val startCountry: TextView
        val signature: TextView
        val bpmAverage: TextView
        val btnUpdate: ImageButton
        val btnDelete: ImageButton

        init {
            genreName = view.findViewById(R.id.tv_genre_name)
            startPeriod = view.findViewById(R.id.tv_start_period)
            startCountry = view.findViewById(R.id.tv_start_country)
            signature = view.findViewById(R.id.tv_signature)
            bpmAverage = view.findViewById(R.id.tv_bpm_average)
            btnUpdate = view.findViewById(R.id.btn_update)
            btnDelete = view.findViewById(R.id.btn_delete)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.rv_element_genre,
                parent,
                false
            )



        return MyViewHolder(itemView)
    }


    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val actualFile: Genre = this.list[position] as Genre
        holder.genreName.text = actualFile.genreName
        holder.startPeriod.text = actualFile.startPeriod
        holder.startCountry.text = actualFile.startCountry
        holder.signature.text = actualFile.signature
        holder.bpmAverage.text = actualFile.bpmAverage.toString()

        holder.btnUpdate.setOnClickListener {
            val holderAttributes = hashMapOf(
                "genreName" to holder.genreName.text.toString(),
                "startPeriod" to holder.startPeriod.text.toString(),
                "startCountry" to holder.startCountry.text.toString(),
                "signature" to holder.signature.text.toString(),
                "bpmAverage" to holder.bpmAverage.text.toString()
            )
            this.context.goToActivity(DataFieldsGenre::class.java, holderAttributes)
        }

        holder.btnDelete.setOnClickListener {
            val db = Firebase.firestore
            db.collection("genres")
                .document(actualFile.genreName.toString())
                .delete()
            this.list.removeAt(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return this.list.size
    }

}