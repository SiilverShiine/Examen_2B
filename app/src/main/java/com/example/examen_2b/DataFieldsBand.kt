package com.example.examen_2b

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.R as Rc
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DataFieldsBand : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_fields_band)

        val db = Firebase.firestore
        val genreSpinner = findViewById<Spinner>(R.id.sp_genre)
        val anInSpinner = findViewById<Spinner>(R.id.sp_analogical_instruments)
        val genres = MainActivity.genreArray
        findViewById<TextView>(R.id.et_band_name).text = intent.getStringExtra("bandName")
        findViewById<TextView>(R.id.et_creation_year).text = intent.getStringExtra("creationYear")
        findViewById<TextView>(R.id.et_type_of_band).text = intent.getStringExtra("typeOfBand")


        val anInBoolean = arrayListOf(true, false)

        val adapterGenres = ArrayAdapter(
            this,
            Rc.layout.support_simple_spinner_dropdown_item,
            genres
        )

        val adapterBoolean = ArrayAdapter(
            this,
            Rc.layout.support_simple_spinner_dropdown_item,
            anInBoolean
        )

        genreSpinner.adapter = adapterGenres
        anInSpinner.adapter = adapterBoolean

        val btnSave = findViewById<Button>(R.id.btn_save_band)
        btnSave.setOnClickListener {
            val bands = db.collection("bands")
            val bandName = findViewById<TextView>(R.id.et_band_name).text.toString()
            bands.document(intent.getStringExtra("bandName").toString()).delete()
            val genre = genres[genreSpinner.selectedItemPosition]

            val newBand = hashMapOf(
                "bandName" to bandName,
                "creationYear" to findViewById<TextView>(R.id.et_creation_year).text.toString(),
                "genre" to genre,
                "typeOfBand" to findViewById<TextView>(R.id.et_type_of_band).text.toString(),
                "analogicalInstruments" to anInSpinner.selectedItem,
            )
            bands.document(bandName).set(newBand)
            goToActivity(MainActivity::class.java)
        }

        val btnCancel = findViewById<Button>(R.id.btn_cancel_band)
        btnCancel.setOnClickListener {
            Log.d("YOc", genres.toString())
            goToActivity(MainActivity::class.java)
        }

    }

    fun goToActivity(
        aClass: Class<*>
    ) {
        val intent = Intent(this, aClass)
        startActivity(intent)
    }

}