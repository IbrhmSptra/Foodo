package id.kotlin.foodo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class splashscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        //get value email dari shared preference
        val sharedPreference =  getSharedPreferences(
            "app_preference", Context.MODE_PRIVATE
        )
        var email = sharedPreference.getString("email", "").toString()


        Handler().postDelayed({
            //seleksi sei
            if (email.isNotEmpty()) {
                goToHomePage()
            } else {
                goToSignInPage()
            }
        },3500)
    }
    private fun goToSignInPage() {
        val intent = Intent(this, login::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToHomePage() {
        val intent = Intent(this, main::class.java)
        startActivity(intent)
        finish()
    }
}