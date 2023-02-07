package id.kotlin.foodo.pesan.recycleviewdetailharga

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.kotlin.foodo.DetailKategori.detailkatAdapter
import id.kotlin.foodo.R
import id.kotlin.foodo.Resep.recycleviewBahan.dataBahan

class detailhargaAdapter (private val detailhargalist : ArrayList<dataDetailHarga>)
    : RecyclerView.Adapter<detailhargaAdapter.detailhargaviewholder>() {

    class detailhargaviewholder(view : View) : RecyclerView.ViewHolder(view) {
        val angka : TextView = view.findViewById(R.id.tv_angka)
        val bahan : TextView = view.findViewById(R.id.tv_bahan)
        val harga : TextView = view.findViewById(R.id.tv_harga)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): detailhargaviewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_detailharga , parent , false)
        return detailhargaviewholder(view)
    }

    override fun onBindViewHolder(holder: detailhargaviewholder, position: Int) {
        val data = detailhargalist[position]
        holder.angka.text = data.jumlah
        holder.bahan.text = data.bahan
        holder.harga.text = data.harga.toString()
    }

    override fun getItemCount(): Int {
        return detailhargalist.size
    }

}