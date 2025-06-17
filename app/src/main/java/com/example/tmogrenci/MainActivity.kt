package com.example.tmogrenci

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAnasayfa = findViewById<Button>(R.id.btnAnasayfa)
        val btnUniversiteler = findViewById<Button>(R.id.btnUniversiteler)
        val btnYardim = findViewById<Button>(R.id.btnYardim)
        val btnIletisim = findViewById<Button>(R.id.btnIletisim)
        val fabChat = findViewById<FloatingActionButton>(R.id.fabChat)

        btnAnasayfa.setOnClickListener {
            val intent = Intent(this, HakkimizdaActivity::class.java)
            startActivity(intent)
        }
        btnUniversiteler.setOnClickListener {
            val intent = Intent(this, Universiteler::class.java)
            startActivity(intent)
        }
        btnYardim.setOnClickListener {
            val intent = Intent(this, Yardim::class.java)
            startActivity(intent)
        }
        btnIletisim.setOnClickListener {
            val intent = Intent(this, Iletisim::class.java)
            startActivity(intent)
        }
        fabChat.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_overflow_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_devlet_belgeler -> {
                Toast.makeText(this, "Devlet Üniversite için gerekli belgeler", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.menu_vakif_belgeler -> {
                Toast.makeText(this, "Vakıf Üniversitesi için gerekli belgeler", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.menu_ikamet_belgeler -> {
                Toast.makeText(this, "Ikamet izni için gerekli belgeler", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.menu_vize_sartlari -> {
                Toast.makeText(this, "Türkiyede vize şartlari", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}