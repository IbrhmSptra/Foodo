package id.kotlin.foodo.Resep.recycleviewBahan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.kotlin.foodo.R


class bahanAdapter (private val bahanlist : ArrayList<dataBahan>)
    : RecyclerView.Adapter<bahanAdapter.bahanviewholder>() {

    class bahanviewholder(view : View) : RecyclerView.ViewHolder(view) {
        val angka : TextView = view.findViewById(R.id.tv_angka)
        val bahan : TextView = view.findViewById(R.id.tv_bahan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): bahanviewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bahan , parent , false)
        return bahanviewholder(view)
    }

    override fun onBindViewHolder(holder: bahanviewholder, position: Int) {
        val bahan = bahanlist[position]
        holder.angka.text = bahan.jumlah
        holder.bahan.text = bahan.bahan
    }

    override fun getItemCount(): Int {
        return bahanlist.size
    }

}