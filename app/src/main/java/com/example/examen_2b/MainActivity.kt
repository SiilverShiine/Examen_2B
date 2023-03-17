package com.example.examen_2b

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private var dataArray: ArrayList<Any> = arrayListOf()

    companion object {
        var genreArray: ArrayList<Any> = arrayListOf()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.rv_main)
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)

        getData(dataArray, "genres")
        var createANew = "genre"


        val btnCreateData = findViewById<Button>(R.id.btn_create_data)
        btnCreateData.setOnClickListener {
            createData()
        }

        val btnShowGenres = findViewById<Button>(R.id.btn_show_genres)
        val btnShowBands = findViewById<Button>(R.id.btn_show_bands)
        btnShowGenres.isEnabled = false
        btnShowGenres.setOnClickListener {
            getData(dataArray, "genres")
            createANew = "genre"
            btnShowGenres.isEnabled = false
            btnShowBands.isEnabled = true
        }
        btnShowBands.setOnClickListener {
            getData(dataArray, "bands")
            createANew = "band"
            btnShowGenres.isEnabled = true
            btnShowBands.isEnabled = false
        }

        val btnInsert = findViewById<Button>(R.id.btn_insert)
        btnInsert.setOnClickListener {
            getData(dataArray, "genres")
            if (createANew == "band") {
                goToActivity(DataFieldsBand::class.java, null)
            } else if (createANew == "genre") {
                goToActivity(DataFieldsGenre::class.java, null)
            }
        }


    }

    private fun getData(
        dataArray: ArrayList<Any>,
        collectionName: String,
    ) {
        val db = Firebase.firestore
        val data = db.collection(collectionName)
        val recyclerView = findViewById<RecyclerView>(R.id.rv_main)

        val adapter: RecyclerView.Adapter<*> = if (collectionName == "genres") {
            GenreAdapter(this, dataArray)
        } else {
            BandAdapter(this, dataArray)
        }

        recyclerView.adapter = adapter

        clearData()

        adapter.notifyDataSetChanged()

        data.get().addOnSuccessListener {
            for (myData in it) {
                addData(dataArray, myData, adapter, collectionName)
            }
            if (genreArray.isEmpty()) {
                genreArray = dataArray.clone() as ArrayList<Any>
            }
            if (genreArray[0] is Genre && dataArray.size < genreArray.size){
                genreArray = dataArray.clone() as ArrayList<Any>
            }
            Log.d("YOa", genreArray.toString())
        }

    }

    private fun clearData() {
        dataArray.clear()
    }

    private fun createData() {
        val db = Firebase.firestore
        val genres = db.collection("genres")
        val bands = db.collection("bands")

        val metal = hashMapOf(
            "genreName" to "Metal",
            "startPeriod" to "1960",
            "startCountry" to "EE-UU",
            "signature" to "4/4",
            "bpmAverage" to "120",
        )
        genres.document("Metal").set(metal)

        val cumbia = hashMapOf(
            "genreName" to "Cumbia",
            "startPeriod" to "1890",
            "startCountry" to "Africa",
            "signature" to "6/8",
            "bpmAverage" to "80",
        )
        genres.document("Cumbia").set(cumbia)

        val salsa = hashMapOf(
            "genreName" to "Salsa",
            "startPeriod" to "1890",
            "startCountry" to "Colombia",
            "signature" to "6/8",
            "bpmAverage" to "100",
        )
        genres.document("Salsa").set(salsa)

        val rock = hashMapOf(
            "genreName" to "Rock",
            "startPeriod" to "1920",
            "startCountry" to "UK",
            "signature" to "4/4",
            "bpmAverage" to "85",
        )
        genres.document("Rock").set(rock)

        val jazz = hashMapOf(
            "genreName" to "Jazz",
            "startPeriod" to "1850",
            "startCountry" to "Europe",
            "signature" to "6/8 - 4/4",
            "bpmAverage" to "80",
        )
        genres.document("Jazz").set(jazz)

        val rammstein = hashMapOf(
            "bandName" to "Rammstein",
            "creationYear" to "1995",
            "genre" to metal,
            "typeOfBand" to "w",
            "analogicalInstruments" to "false",
        )
        bands.document("Rammstein").set(rammstein)

        val band2 = hashMapOf(
            "bandName" to "Band2",
            "creationYear" to "2000",
            "genre" to cumbia,
            "typeOfBand" to "w",
            "analogicalInstruments" to "false",
        )
        bands.document("Band2").set(band2)

        val band3 = hashMapOf(
            "bandName" to "Band3",
            "creationYear" to "2000",
            "genre" to cumbia,
            "typeOfBand" to "w",
            "analogicalInstruments" to "false",
        )
        bands.document("Band3").set(band3)

        val band4 = hashMapOf(
            "bandName" to "Band4",
            "creationYear" to "2000",
            "genre" to cumbia,
            "typeOfBand" to "w",
            "analogicalInstruments" to "false",
        )
        bands.document("Band4").set(band4)

        val band5 = hashMapOf(
            "bandName" to "Band5",
            "creationYear" to "2000",
            "genre" to cumbia,
            "typeOfBand" to "w",
            "analogicalInstruments" to "false",
        )
        bands.document("Band5").set(band5)
    }

    private fun addData(
        newArray: ArrayList<Any>,
        data: QueryDocumentSnapshot,
        adapter: RecyclerView.Adapter<*>,
        collectionName: String
    ) {
        var newData: Any? = null



        if (collectionName == "genres") {
            newData = Genre(
                data.data["genreName"] as String?,
                data.data["startPeriod"] as String?,
                data.data["startCountry"] as String?,
                data.data["signature"] as String?,
                data.data["bpmAverage"].toString().toDouble() as Double?
            )
        }

        if (collectionName == "bands") {
            val genresHashMap: HashMap<String, Any> = data.data["genre"] as HashMap<String, Any>

            val genre = Genre(
                genreName = genresHashMap["genreName"] as String?,
                startPeriod = genresHashMap["startPeriod"] as String?,
                startCountry = genresHashMap["startCountry"] as String?,
                signature = genresHashMap["signature"] as String?,
                bpmAverage = genresHashMap["bpmAverage"].toString().toDouble() as Double?,
            )

            newData = Band(
                data.data["bandName"] as String?,
                data.data["creationYear"].toString().toInt() as Int?,
                genre,
                data.data["typeOfBand"].toString()[0] as Char?,
                data.data["analogicalInstruments"].toString().toBoolean() as Boolean?
            )
        }

        if (newData != null) {
            newArray.add(newData)
        }

        adapter.notifyDataSetChanged()
    }

    fun goToActivity(
        AClass: Class<*>,
        extras: HashMap<String, String>?
    ) {
        val intent = Intent(this, AClass)
        intent.putExtra("genres", genreArray)
        if (extras != null) {
            for (extra in extras) {
                intent.putExtra(extra.key, extra.value)
            }
        }

        startActivity(intent)
    }
}