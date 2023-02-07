package id.kotlin.foodo.recycleviewTrending

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


class trendingAdapter (private val trendinglist : ArrayList<dataTrending>)
    : RecyclerView.Adapter<trendingAdapter.trendingviewholder>() {

    class trendingviewholder(view : View) : RecyclerView.ViewHolder(view) {
        val kartu : CardView = view.findViewById(R.id.kartutrend)
        val img : ImageView = view.findViewById(R.id.pic_trending)
        val tvtrend : TextView = view.findViewById(R.id.tv_trending)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): trendingviewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_trending , parent , false)
        return trendingviewholder(view)
    }

    override fun onBindViewHolder(holder: trendingviewholder, position: Int) {
        val trending = trendinglist[position]
        holder.tvtrend.text = trending.food
        Glide.with(holder.img.context).load(trending.imgrc).into(holder.img)
        holder.kartu.setOnClickListener {
            //intent ke detailactivity lempar nama food, img food dan imgrckat
            val intent = Intent(holder.kartu.context , detailActivity::class.java)
            intent.putExtra("food" , trending.food)
            intent.putExtra("img" , trending.img)
            intent.putExtra("imgrckat" , trending.imgrckat)
            holder.kartu.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return trendinglist.size
    }

}