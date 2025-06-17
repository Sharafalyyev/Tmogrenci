package com.example.tmogrenci

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.tmogrenci.databinding.ActivityUniversiteDetailsBinding
import android.widget.TextView
import android.widget.ImageView
import com.bumptech.glide.Glide

class UniversiteDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_universite_details)

        val isim = intent.getStringExtra("isim")
        val sehir = intent.getStringExtra("sehir")
        val tur = intent.getStringExtra("tur")
        val aciklama = intent.getStringExtra("aciklama")
        val logoUrl = intent.getStringExtra("logoUrl")
        val appldate = intent.getStringExtra("appldate")
        val appexam = intent.getStringExtra("appexam")

        val txtIsim = findViewById<TextView>(R.id.txtIsim)
        val txtSehir = findViewById<TextView>(R.id.txtSehir)
        val txtTur = findViewById<TextView>(R.id.txtTur)
        val txtAciklama = findViewById<TextView>(R.id.txtAciklama)
        val imgLogo = findViewById<ImageView>(R.id.imgLogo)
        val txtappldate = findViewById<TextView>(R.id.txtappldate)
        val txtappexam = findViewById<TextView>(R.id.txtappexam)

        txtIsim.text = isim
        txtSehir.text = sehir
        txtTur.text = tur
        txtAciklama.text = aciklama
        txtappldate.text = appldate
        txtappexam.text = appexam

        Glide.with(this)
            .load(logoUrl)
            .into(imgLogo)
    }
}