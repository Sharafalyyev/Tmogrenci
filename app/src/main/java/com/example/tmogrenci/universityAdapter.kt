package com.example.tmogrenci

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class UniversityAdapter(
    private val context: Context,
    private val universityList: List<University>
) : RecyclerView.Adapter<UniversityAdapter.UniversityViewHolder>() {

    inner class UniversityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtisim: TextView = itemView.findViewById(R.id.txtisim)
        val txtsehir: TextView = itemView.findViewById(R.id.txtsehir)
        val txttur: TextView = itemView.findViewById(R.id.txttur)


        //val txtaciklama: TextView = itemView.findViewById(R.id.txtaciklama)
        val imgLogo: ImageView = itemView.findViewById(R.id.logoUrl)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UniversityViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_university, parent, false)
        return UniversityViewHolder(view)
    }

    override fun onBindViewHolder(holder: UniversityViewHolder, position: Int) {
        val university = universityList[position]

        holder.txtisim.text = university.isim
        holder.txtsehir.text = university.sehir
        holder.txttur.text = university.tur

        //holder.txtaciklama.text = university.aciklama

        Glide.with(holder.itemView.context)
            .load(university.logoUrl)
            .into(holder.imgLogo)

        // 👇 Kartın tamamı tıklanınca detay sayfasına geç
        holder.itemView.setOnClickListener {
            val intent = Intent(context, UniversiteDetails::class.java)
            intent.putExtra("isim", university.isim)
            intent.putExtra("sehir", university.sehir)
            intent.putExtra("tur", university.tur)
            intent.putExtra("aciklama", university.aciklama)
            intent.putExtra("logoUrl", university.logoUrl)
            intent.putExtra("appldate", university.appldate)
            intent.putExtra("appexam", university.appexam)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = universityList.size
}