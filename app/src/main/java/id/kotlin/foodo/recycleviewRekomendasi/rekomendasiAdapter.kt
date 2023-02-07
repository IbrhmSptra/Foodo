package id.kotlin.foodo.recycleviewRekomendasi

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.kotlin.foodo.R
import id.kotlin.foodo.Resep.detailActivity

class rekomendasiAdapter (private val rekomendlist : ArrayList<dataRekomendasi>)
    : RecyclerView.Adapter<rekomendasiAdapter.rekomendviewholder>() {

    class rekomendviewholder(view : View) : RecyclerView.ViewHolder(view) {
        val kartu : CardView = view.findViewById(R.id.karturekomend)
        val imgrc : ImageView = view.findViewById(R.id.pic_rekomendasi)
        val tvfood : TextView = view.findViewById(R.id.tv_rekomendasi)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): rekomendviewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rekomendasi , parent , false)
        return rekomendviewholder(view)
    }

    override fun onBindViewHolder(holder: rekomendviewholder, position: Int) {
        val rekomend = rekomendlist[position]
        holder.tvfood.text = rekomend.food
        Glide.with(holder.imgrc.context).load(rekomend.imgrc).into(holder.imgrc)
        holder.kartu.setOnClickListener {
            //intent ke detailactivity lempar nama food, img food dan imgrckat
            val intent = Intent(holder.kartu.context , detailActivity::class.java)
            intent.putExtra("food" , rekomend.food)
            intent.putExtra("img" , rekomend.img)
            intent.putExtra("imgrckat" , rekomend.imgrckat)
            holder.kartu.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return rekomendlist.size
    }

}