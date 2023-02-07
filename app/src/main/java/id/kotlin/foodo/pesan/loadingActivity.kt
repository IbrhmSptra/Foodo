package id.kotlin.foodo.pesan

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import id.kotlin.foodo.R
import id.kotlin.foodo.main


class loadingActivity : AppCompatActivity() {

    //komponen
    lateinit var status : TextView
    lateinit var icon : ImageView
    lateinit var timer : CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        //initilize komponen
        status = findViewById(R.id.tv_status)
        icon = findViewById(R.id.icon)

        timer = object : CountDownTimer(9000 , 1000){
            override fun onTick(time: Long) {
                //ketika pesanan sedang di proses
                if(time <= 6000 && time >= 3000) {
                    icon.setImageResource(R.drawable.proses)
                    status.text = "Pesananmu Sedang Diproses"
                }
                //ketika pesanan sedang dikirim
                if (time <= 3000) {
                    icon.setImageResource(R.drawable.kirim)
                    status.text = "Pesananmu Sedang Dikirim"
                }
            }
            //selesai
            override fun onFinish() {
                val intent = Intent(applicationContext , main::class.java)
                startActivity(intent)
                finish()
            }

        }

    }

    //start the timer
    override fun onStart() {
        super.onStart()
        timer.start()
    }

    //stop the timer when is over
    override fun onStop() {
        super.onStop()
        timer.cancel()
    }
}