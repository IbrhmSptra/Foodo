package id.kotlin.foodo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import id.kotlin.foodo.userAPI.API_akun
import id.kotlin.foodo.userAPI.API_user
import id.kotlin.foodo.userAPI.dataRegAkun
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
    val apireg = RetrofitHelper.getInstance().create(API_akun::class.java)

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

                CoroutineScope(Dispatchers.Main).launch {

                    //get value edit text
                    val username = regusername.text.toString()
                    val email = regemail.text.toString()
                    val password = regpassword.text.toString()
                    val repassword = regrepassword.text.toString()

                    //get value email dari database
                    var akun = ""
                    val query = "eq.$email"
                    val response = apireg.get(token = token, apiKey = apikey, query = query)
                    response.body()?.forEach{
                        akun = it.email
                    }

                    //validasi jika kosong
                    if (username.isNullOrEmpty() || email.isNullOrEmpty() || password.isNullOrEmpty() || repassword.isNullOrEmpty()) {
                        Toast.makeText(this@register, "Lengkapi Data Terlebih Dahulu", Toast.LENGTH_SHORT).show()
                    }
                    //validasi jika password dan retype tidak sama
                    else if (password != repassword) {
                        regpassword.error = "Tidak Sama"
                        regrepassword.error = "Tidak Sama"
                    }
                    //validasi jika email sudah pernah di daftar
                    else if (!akun.isNullOrEmpty()){
                        Toast.makeText(this@register, "Email Sudah Pernah Didaftar", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        //fungsi signup
                        signUp(regemail.text.toString(), regpassword.text.toString())

                        //fungsi nyimpan username
                        akunreg(username, email)
                    }

                }


            }

    }

    private fun akunreg(username : String, email : String) {
        CoroutineScope(Dispatchers.Main).launch {
            var data = dataRegAkun(username = username , email = email)
            val response = apireg.createakun(token = token , apiKey = apikey, data = data)
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
                msg = "Pesan Autentikasi Dikirim Ke Email Anda"
            } else {
                var errorMessage = jsonResponse.get("error_description")
                msg += errorMessage
            }

            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(
                    applicationContext,
                    msg,
                    Toast.LENGTH_LONG
                ).show()

                finish()
            }
        }

    }
}