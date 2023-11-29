package com.example.myfirebaseexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.example.myfirebaseexample.api.FirebaseApiAdapter
import com.example.myfirebaseexample.api.response.AnimexResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    // Referenciar campos de las interfaz
    private lateinit var idSpinner: Spinner
    private lateinit var nameField: EditText
    private lateinit var tempoField: EditText
    private lateinit var capiField: EditText
    private lateinit var linkField: EditText
    private lateinit var buttonSave: Button
    private lateinit var buttonLoad: Button
    private lateinit var buttonEliminate: Button

    // Referenciar la API
    private var firebaseApi = FirebaseApiAdapter()

    // Mantener los nombres e IDs de las armas
    private var animeList = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        idSpinner = findViewById(R.id.idSpinner)
        nameField = findViewById(R.id.nameField)
        tempoField = findViewById(R.id.tempoField)
        capiField = findViewById(R.id.capiField)
        linkField = findViewById(R.id.linkField)

        buttonLoad = findViewById(R.id.buttonLoad)
        buttonLoad.setOnClickListener {
            Toast.makeText(this, "Cargando información", Toast.LENGTH_SHORT).show()
            runBlocking {
                getAnimeFromApi()
            }
        }

        buttonSave = findViewById(R.id.buttonSave)
        buttonSave.setOnClickListener {
            Toast.makeText(this, "Guardando información", Toast.LENGTH_SHORT).show()
            runBlocking {
                sendAnimeToApi()
            }
        }

        runBlocking {
            populateIdSpinner()
        }
    }

    private suspend fun populateIdSpinner() {
        val response = GlobalScope.async(Dispatchers.IO) {
            firebaseApi.getAnime()
        }
        val anime = response.await()
        anime?.forEach { entry ->
            animeList.add("${entry.key}: ${entry.value.name}")
        }
        val animeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, animeList)
        with(idSpinner) {
            adapter = animeAdapter
            setSelection(0, false)
            gravity = Gravity.CENTER
        }
    }

    private suspend fun getAnimeFromApi() {
        val selectedItem = idSpinner.selectedItem.toString()
        val animeId = selectedItem.subSequence(0, selectedItem.indexOf(":")).toString()
        println("Loading ${animeId}... ")
        val animeResponse = GlobalScope.async(Dispatchers.IO) {
            firebaseApi.getAnime(animeId)
        }
        val anime = animeResponse.await()
        nameField.setText(anime?.name)
        tempoField.setText(anime?.tempo)
        capiField.setText("${anime?.capi}")
        linkField.setText(anime?.link)

    }

    private suspend fun sendAnimeToApi() {
        val animeName = nameField.text.toString()
        val tempo = tempoField.text.toString()
        val link = linkField.text.toString()
        val capi = capiField.text.toString().toLong()
        val anime = AnimexResponse("", animeName, tempo, link, capi)
        val animeResponse = GlobalScope.async(Dispatchers.IO) {
            firebaseApi.setAnime(anime)
        }
        val response = animeResponse.await()
        nameField.setText(anime?.name)
        tempoField.setText(anime?.tempo)
        capiField.setText("${anime?.capi}")
        linkField.setText(anime?.link)
    }
}