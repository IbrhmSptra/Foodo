package id.kotlin.foodo.DetailKategori

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
import id.kotlin.foodo.recycleviewTrending.dataTrending

class detailkatAdapter (private val detailkatlist : ArrayList<datadetailkat>)
    : RecyclerView.Adapter<detailkatAdapter.detailkatviewholder>() {

    class detailkatviewholder(view : View) : RecyclerView.ViewHolder(view) {
        val kartu : CardView = view.findViewById(R.id.kartudetail)
        val imgrc : ImageView = view.findViewById(R.id.imgrcfood)
        val tvrc : TextView = view.findViewById(R.id.tvrcfood)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): detailkatviewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_kategoridetail , parent , false)
        return detailkatviewholder(view)
    }

    override fun onBindViewHolder(holder: detailkatviewholder, position: Int) {
        val katdetail = detailkatlist[position]
        holder.tvrc.text = katdetail.food
        Glide.with(holder.imgrc.context).load(katdetail.imgrc).into(holder.imgrc)
        holder.kartu.setOnClickListener {
            //intent ke detailactivity lempar nama food, img food dan imgrckat
            val intent = Intent(holder.kartu.context , detailActivity::class.java)
            intent.putExtra("food" , katdetail.food)
            intent.putExtra("img" , katdetail.img)
            intent.putExtra("imgrckat" , katdetail.imgrc)
            holder.kartu.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return detailkatlist.size
    }

}