package id.kotlin.foodo

import android.content.Context
import android.content.SharedPreferences
import android.widget.EditText

class sessionakun (context : Context) {
    var sharedPreferences : SharedPreferences? = null
    var editor : SharedPreferences.Editor?= null

    //variabel
    val preferance = "akun"
    val usernamekey = "username"
    val passwordkey = "password"
    val usernamelogin = "usernamelog"
    val passwordlogin = "passwordlog"


    init {
        sharedPreferences = context.getSharedPreferences(preferance, Context.MODE_PRIVATE)
        editor = sharedPreferences?.edit()
    }

    //daftar / menyimpan akun
    fun daftar (username : String, password : String) {
        editor?.putString(usernamekey, username)
        editor?.putString(passwordkey, password)
        editor?.apply()
    }

    //login
    fun login(username: String, password: String) {
        editor?.putString(usernamelogin, username)
        editor?.putString(passwordlogin , password)
        editor?.apply()
    }


    //logout
    fun logout() {
        editor?.remove(usernamelogin)
        editor?.remove(passwordlogin)
        editor?.apply()
    }

    //getter username dan password register
    val username : String?
        get() = sharedPreferences?.getString(usernamekey, null)
    val password : String?
        get() = sharedPreferences?.getString(passwordkey, null)

    //getter username dan password login
    val usernamelog :String?
        get() = sharedPreferences?.getString(usernamelogin, null)
    val passwordlog :String?
        get() = sharedPreferences?.getString(passwordlogin, null)


    //session
    fun islogin() : Boolean {
        if (!username.isNullOrEmpty() && !password.isNullOrEmpty() && !usernamelog.isNullOrEmpty() && !passwordlog.isNullOrEmpty()) {
            return true
        }
        return false
    }

}