package id.kotlin.foodo.recycleviewKategori

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.kotlin.foodo.DetailKategori.Detailkategori_activity
import id.kotlin.foodo.R

class kategoriAdapter (private val kategorilist : ArrayList<dataKategori>)
    : RecyclerView.Adapter<kategoriAdapter.kategoriviewholder>() {

    class kategoriviewholder(view : View) : RecyclerView.ViewHolder(view) {
        val kartu : CardView = view.findViewById(R.id.card)
        val imageView : ImageView = view.findViewById(R.id.pic_kategori)
        val textView : TextView = view.findViewById(R.id.tv_kategori)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): kategoriviewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_kategori , parent , false)
        return kategoriviewholder(view)
    }

    override fun onBindViewHolder(holder: kategoriviewholder, position: Int) {
        val kategori = kategorilist[position]
        holder.textView.text = kategori.kategori
        Glide.with(holder.imageView.context).load(kategori.imgrc).into(holder.imageView)
        holder.kartu.setOnClickListener {
            //pindah ke detail kategori dan lempar imgheader dan id kategori
            val intent = Intent(holder.kartu.context , Detailkategori_activity::class.java)
            intent.putExtra("id" , kategori.idkat)
            intent.putExtra("header" , kategori.imgheader)
            holder.kartu.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return kategorilist.size
    }

}