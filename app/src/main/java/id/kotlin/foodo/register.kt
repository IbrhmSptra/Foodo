package id.kotlin.foodo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import id.kotlin.foodo.userAPI.API_user
import id.kotlin.foodo.userAPI.dataUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class register : AppCompatActivity() {

    //komponen
    lateinit var regusername : EditText
    lateinit var regemail : EditText
    lateinit var regpassword : EditText
    lateinit var regrepassword : EditText
    lateinit var btnsignup : Button

    //API
    val apikey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InFxbmtlZXV0dGFjeWZjdGdlYnpjIiwicm9sZSI6ImFub24iLCJpYXQiOjE2Njk5NTUxNTgsImV4cCI6MTk4NTUzMTE1OH0.ZK9H5UcqMWlrrbbqzEnHVhJbdSi7x5CQRVsdB92Kt8c"
    val token = "Bearer $apikey"
    val api = RetrofitHelper.getInstance().create(API_user::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //init
        regusername = findViewById(R.id.regusername)
        regemail = findViewById(R.id.regemail)
        regpassword = findViewById(R.id.regpassword)
        regrepassword = findViewById(R.id.regrepassword)
        btnsignup = findViewById(R.id.signup)

        btnsignup.setOnClickListener {

            //get value edit text
            val username = regusername.text.toString()
            val email = regemail.text.toString()
            val password = regpassword.text.toString()
            val repassword = regrepassword.text.toString()

            //validasi jika kosong
            if (username.isNullOrEmpty() || email.isNullOrEmpty() || password.isNullOrEmpty() || repassword.isNullOrEmpty()){
                Toast.makeText(this, "Lengkapi Data Terlebih Dahulu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            //validasi jika password dan retype tidak sama
            if (password != repassword) {
                regpassword.error = "Tidak Sama"
                regrepassword.error = "Tidak Sama"
            }else {
                //menyimpan username ke shared preferance
                val sharedPreference =  getSharedPreferences(
                    "app_preference", Context.MODE_PRIVATE
                )
                var editor = sharedPreference.edit()
                editor.putString("username" , regusername.text.toString())
                editor.commit()

                //fungsi signup
                signUp(regemail.text.toString(), regpassword.text.toString())
            }


        }

    }


    private fun signUp(email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {

            var data = dataUser(email = email, password = password)
            var response = api.signUp(token = token , apiKey = apikey , data = data)

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
                msg = "Successfully sign up!"
            } else {
                var errorMessage = jsonResponse.get("error_description")
                msg += errorMessage
            }

            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(
                    applicationContext,
                    msg,
                    Toast.LENGTH_SHORT
                ).show()

                finish()
            }
        }

    }
}