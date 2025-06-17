package com.example.tmogrenci

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Yardim : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yardim)
        // Form bileşenlerini tanımla
        val edtAdSoyad = findViewById<EditText>(R.id.edtAdSoyad)
        val edtIletisim = findViewById<EditText>(R.id.edtIletisim)
        val edtTelefon = findViewById<EditText>(R.id.edtTelefon)
        val edtMesaj = findViewById<EditText>(R.id.edtMesaj)
        val btnGonder = findViewById<MaterialButton>(R.id.btnGonder)

        btnGonder.setOnClickListener {
            val adSoyad = edtAdSoyad.text.toString().trim()
            val iletisim = edtIletisim.text.toString().trim()
            val telefon = edtTelefon.text.toString().trim()
            val mesaj = edtMesaj.text.toString().trim()

            if (adSoyad.isEmpty() || iletisim.isEmpty() || mesaj.isEmpty()) {
                Toast.makeText(this, "Lütfen zorunlu alanları doldurun", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val subject = "Yardım Talebi"
            val message = """
                Ad Soyad: $adSoyad
                E-posta: $iletisim
                Telefon: $telefon
                Mesaj: $mesaj
            """.trimIndent()

            val emailIntent = Intent(Intent.ACTION_SEND).apply {
                type = "message/rfc822"
                putExtra(Intent.EXTRA_EMAIL, arrayOf("sharaf.dz99@gmail.com"))
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, message)
            }

            try {
                startActivity(Intent.createChooser(emailIntent, "E-posta uygulaması seçin"))
            } catch (e: Exception) {
                Toast.makeText(this, "E-posta uygulaması bulunamadı", Toast.LENGTH_SHORT).show()
            }
        }

        // Add FAB for chat support
        val fabChat = findViewById<FloatingActionButton>(R.id.fabChat)
        fabChat.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
            startActivity(intent)
        }
    }
}