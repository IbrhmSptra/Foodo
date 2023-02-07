package id.kotlin.foodo.recycleviewPencarian

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.kotlin.foodo.DetailKategori.Detailkategori_activity
import id.kotlin.foodo.R
import id.kotlin.foodo.Resep.detailActivity


class searchAdapter (private val searchlist : ArrayList<dataSearch>)
    : RecyclerView.Adapter<searchAdapter.searchviewholder>() {

    class searchviewholder(view : View) : RecyclerView.ViewHolder(view) {
        val kartu : CardView = view.findViewById(R.id.kartusearch)
        val imgrc : ImageView = view.findViewById(R.id.pic_food)
        val tvfood : TextView = view.findViewById(R.id.tv_food)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): searchviewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search , parent , false)
        return searchviewholder(view)
    }

    override fun onBindViewHolder(holder: searchviewholder, position: Int) {
        val search = searchlist[position]
        holder.tvfood.text = search.food
        Glide.with(holder.imgrc.context).load(search.imgrc).into(holder.imgrc)
        holder.kartu.setOnClickListener {
            //intent ke detailactivity lempar nama food, img food dan imgrckat
            val intent = Intent(holder.kartu.context , detailActivity::class.java)
            intent.putExtra("food" , search.food)
            intent.putExtra("img" , search.img)
            intent.putExtra("imgrckat" , search.imgrckat)
            holder.kartu.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return searchlist.size
    }

}