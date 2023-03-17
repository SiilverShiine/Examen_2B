package com.example.examen_2b

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DataFieldsGenre : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_fields_genre)
        val db = Firebase.firestore
        findViewById<TextView>(R.id.et_genre_name).text = intent.getStringExtra("genreName")
        findViewById<TextView>(R.id.et_start_period).text = intent.getStringExtra("startPeriod")
        findViewById<TextView>(R.id.et_start_country).text = intent.getStringExtra("startCountry")
        findViewById<TextView>(R.id.et_signature).text = intent.getStringExtra("signature")
        findViewById<TextView>(R.id.et_bpm_average).text = intent.getStringExtra("bpmAverage")


        val btnSave = findViewById<Button>(R.id.btn_save_genre)
        btnSave.setOnClickListener {
            val genres = db.collection("genres")
            val genreName = findViewById<TextView>(R.id.et_genre_name).text.toString()
            genres.document(intent.getStringExtra("genreName").toString()).delete()
            val startPeriod = findViewById<TextView>(R.id.et_start_period).text.toString()
            val startCountry = findViewById<TextView>(R.id.et_start_country).text.toString()
            val signature = findViewById<TextView>(R.id.et_signature).text.toString()
            val bpmAverage = findViewById<TextView>(R.id.et_bpm_average).text.toString()

            val newBand = hashMapOf(
                "genreName" to genreName,
                "startPeriod" to startPeriod,
                "startCountry" to startCountry,
                "signature" to signature,
                "bpmAverage" to bpmAverage,
            )
            genres.document(genreName).set(newBand)
            goToActivity(MainActivity::class.java)
        }

        val btnCancel = findViewById<Button>(R.id.btn_cancel_band)
        btnCancel.setOnClickListener {
            goToActivity(MainActivity::class.java)
        }

    }

    fun goToActivity(
        AClass: Class<*>
    ) {
        val intent = Intent(this, AClass)
        startActivity(intent)
    }
}