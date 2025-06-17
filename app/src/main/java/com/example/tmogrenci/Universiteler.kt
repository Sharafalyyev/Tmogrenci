package com.example.tmogrenci

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class Universiteler : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UniversityAdapter
    private val universityList = mutableListOf<University>()

    private var selectedCity: String? = null
    private var selectedType: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_universiteler)

        recyclerView = findViewById(R.id.universityRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = UniversityAdapter(this, universityList)
        recyclerView.adapter = adapter

        val btnFiltrele = findViewById<MaterialButton>(R.id.btnFiltrele)
        btnFiltrele.setOnClickListener {
            showFilterDialog()
        }

        fetchUniversities() // Show all at first

        // Add FAB for chat support
        val fabChat = findViewById<FloatingActionButton>(R.id.fabChat)
        fabChat.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showFilterDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_university_filter, null)
        val spinnerCity = dialogView.findViewById<Spinner>(R.id.dialogSpinnerCity)
        val spinnerType = dialogView.findViewById<Spinner>(R.id.dialogSpinnerType)

        val cities = listOf("Tümü", "Ankara", "İstanbul")
        val types = listOf("Tümü", "Devlet", "Vakıf")

        spinnerCity.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, cities)
        spinnerType.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, types)

        // Set current selection
        spinnerCity.setSelection(selectedCity?.let { cities.indexOf(it) } ?: 0)
        spinnerType.setSelection(selectedType?.let { types.indexOf(it) } ?: 0)

        AlertDialog.Builder(this)
            .setTitle("Filtrele")
            .setView(dialogView)
            .setPositiveButton("Uygula") { dialog, _ ->
                selectedCity = if (spinnerCity.selectedItemPosition == 0) null else cities[spinnerCity.selectedItemPosition]
                selectedType = if (spinnerType.selectedItemPosition == 0) null else types[spinnerType.selectedItemPosition]
                fetchUniversities()
                dialog.dismiss()
            }
            .setNegativeButton("İptal") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun fetchUniversities() {
        val db = FirebaseFirestore.getInstance()
        var query: Query = db.collection("universiteler")

        // Only add city filter if a city is selected (not "Tümü")
        selectedCity?.let { city ->
            query = query.whereEqualTo("sehir", "Şehir: $city")
        }

        // Only add type filter if a type is selected (not "Tümü")
        selectedType?.let { type ->
            query = query.whereEqualTo("tur", type)
        }

        query.get()
            .addOnSuccessListener { result ->
                universityList.clear()
                for (document in result) {
                    val uni = document.toObject(University::class.java)
                    if (uni != null) {
                        universityList.add(uni)
                    }
                }
                adapter.notifyDataSetChanged()
                if (universityList.isEmpty()) {
                    android.widget.Toast.makeText(this, "Üniversite bulunamadı.", android.widget.Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                android.widget.Toast.makeText(this, "Veri alınamadı: ${e.message}", android.widget.Toast.LENGTH_LONG).show()
            }
    }
}