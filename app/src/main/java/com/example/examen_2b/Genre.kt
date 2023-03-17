package com.example.examen_2b

class Genre(
    var genreName: String?,
    var startPeriod: String?,
    var startCountry: String?,
    var signature: String?,
    var bpmAverage: Double?,
):java.io.Serializable{
    override fun toString(): String {
        return "$genreName"
    }
}