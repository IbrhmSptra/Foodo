package id.kotlin.foodo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import id.kotlin.foodo.userAPI.API_user
import id.kotlin.foodo.userAPI.dataUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class login : AppCompatActivity() {

    //komponen
    lateinit var etemail : EditText
    lateinit var etpassword : EditText
    lateinit var btnlogin : Button
    lateinit var btndaftar : TextView

    //API
    val apikey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InFxbmtlZXV0dGFjeWZjdGdlYnpjIiwicm9sZSI6ImFub24iLCJpYXQiOjE2Njk5NTUxNTgsImV4cCI6MTk4NTUzMTE1OH0.ZK9H5UcqMWlrrbbqzEnHVhJbdSi7x5CQRVsdB92Kt8c"
    val token = "Bearer $apikey"
    val api = RetrofitHelper.getInstance().create(API_user::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //init
        etemail = findViewById(R.id.etemail)
        etpassword = findViewById(R.id.etpassword)
        btnlogin = findViewById(R.id.login)
        btndaftar = findViewById(R.id.daftarbtn)

        btnlogin.setOnClickListener {
            signIn(etemail.text.toString(), etpassword.text.toString())
        }

        btndaftar.setOnClickListener {
            val intent = Intent(this, register::class.java)
            startActivity(intent)
        }
    }

    private fun signIn(email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {

            val data = dataUser(email = email, password = password)
            val response = api.signIn(token = token , apiKey = apikey , data = data)

            val bodyResponse = if(response.code() == 200) {
                response.body()?.string()
            } else {
                response.errorBody()?.string().toString()
            }

            var failed = false
            val jsonResponse = JSONObject(bodyResponse)
            if(jsonResponse.keys().asSequence().toList().contains("error")) {
                failed = true
            }

            var msg = ""
            if (!failed) {
                //menyimpan email ke shared pref untuk login
                val sharedPreference =  getSharedPreferences(
                    "app_preference", Context.MODE_PRIVATE
                )

                var editor = sharedPreference.edit()
                editor.putString("email", email)
                editor.commit()

                //get value username dari shared pref
                val username = sharedPreference.getString("username" , "").toString()

                var email = jsonResponse.getJSONObject("user").get("email").toString()
                msg = "Successfully login! Hi, $username"




            } else {
                msg += jsonResponse.get("error_description").toString()
            }

            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(
                    applicationContext,
                    msg,
                    Toast.LENGTH_SHORT
                ).show()

                if (!failed) {
                    goToHome();
                }
            }
        }
    }

        private fun goToHome() {
            val intent = Intent(this, main::class.java)
            startActivity(intent)
            finish()
        }



}