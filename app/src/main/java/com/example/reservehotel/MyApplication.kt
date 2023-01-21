package com.example.reservehotel

import android.app.Application
import org.apache.commons.io.FileUtils
import android.util.Log
import com.example.reservehotel.hotelUtils.ReservationsList
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.apache.commons.io.FileUtils.writeStringToFile
import java.io.File
import java.io.IOException

const val FILE_NAME = "mydata.json"

class MyApplication: Application() {
    var data: ReservationsList = ReservationsList()
    private var gson: Gson = GsonBuilder().setPrettyPrinting().create()
    private lateinit var file: File

    override fun onCreate() {
        super.onCreate()
        gson = Gson()
        file = File(filesDir, FILE_NAME)
        Log.i("file name", file.path)

        //readFromFile()
    }

    private fun readFromFile() {
        data = try {
            Log.i("reading", "reading from file")
            gson.fromJson(FileUtils.readFileToString(file), ReservationsList::class.java)
        } catch (e: IOException) {
            Log.i("error", "no data in file")
            this.data
        }
    }

    fun saveToFile() {
        try {
            writeStringToFile(file, gson.toJson(data))
            Log.i("saving", "saved to file")
        } catch (e: IOException) {
            Log.i("error", "error saving to file")
        }
    }
}