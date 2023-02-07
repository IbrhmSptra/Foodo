package id.kotlin.foodo.Resep.recycleMarimasak

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.kotlin.foodo.R


class marimasakAdapter (private val marimasaklist : ArrayList<dataMarimasak>)
    : RecyclerView.Adapter<marimasakAdapter.marimasakviewholder>() {

    class marimasakviewholder(view : View) : RecyclerView.ViewHolder(view) {
        val langkah : TextView = view.findViewById(R.id.tv_tahap)
        val cara : TextView = view.findViewById(R.id.tv_cara)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): marimasakviewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_marimasak , parent , false)
        return marimasakviewholder(view)
    }

    override fun onBindViewHolder(holder: marimasakviewholder, position: Int) {
        val marimasak = marimasaklist[position]
        holder.langkah.text = marimasak.langkah
        holder.cara.text = marimasak.cara
    }

    override fun getItemCount(): Int {
        return marimasaklist.size
    }

}