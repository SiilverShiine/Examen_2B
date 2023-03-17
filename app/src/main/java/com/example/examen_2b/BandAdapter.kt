package com.example.examen_2b

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BandAdapter(
    private val context: MainActivity,
    private val list: ArrayList<Any>
) : RecyclerView.Adapter<BandAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bandName: TextView
        val creationYear: TextView
        val genre: TextView
        val typeOfBand: TextView
        val analogicalInstruments: TextView
        val btnUpdate: ImageButton
        val btnDelete: ImageButton

        init {
            bandName = view.findViewById(R.id.tv_band_name)
            creationYear = view.findViewById(R.id.tv_creation_year)
            genre = view.findViewById(R.id.tv_genre)
            typeOfBand = view.findViewById(R.id.tv_type_of_band)
            analogicalInstruments = view.findViewById(R.id.tv_analogical_instruments)
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
                R.layout.rv_element_band,
                parent,
                false
            )
        return MyViewHolder(itemView)
    }


    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val actualFile: Band = this.list[position] as Band
        holder.bandName.text = actualFile.bandName
        holder.creationYear.text = actualFile.creationYear.toString()
        holder.genre.text = actualFile.genre.toString()
        holder.typeOfBand.text = actualFile.typeOfBand.toString()
        holder.analogicalInstruments.text = actualFile.analogicalInstruments.toString()

        holder.btnUpdate.setOnClickListener {
            val holderAttributes = hashMapOf(
                "bandName" to holder.bandName.text.toString(),
                "creationYear" to holder.creationYear.text.toString(),
                "genre" to holder.genre.text.toString(),
                "typeOfBand" to holder.typeOfBand.text.toString(),
                "analogicalInstruments" to holder.analogicalInstruments.text.toString()
            )
            this.context.goToActivity(DataFieldsBand::class.java, holderAttributes)
        }

        holder.btnDelete.setOnClickListener {
            val db = Firebase.firestore
            db.collection("bands")
                .document(actualFile.bandName.toString())
                .delete()
            this.list.removeAt(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return this.list.size
    }

}