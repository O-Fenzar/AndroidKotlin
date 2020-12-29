package com.example.androidkotlin.ui.register_activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.androidkotlin.R
import com.example.androidkotlin.data.local.model.User
import com.example.androidkotlin.ui.login_viewmodel.LoginViewModel
import com.example.androidkotlin.ui.popular_movies.MainActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    var isExist = false

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val userDetailsRepository = ViewModelProvider(this).get(LoginViewModel::class.java)

        val btn = findViewById<TextView>(R.id.textViewSignUp)
        btn.setOnClickListener {

            val intent = Intent(

                this@LoginActivity,
                SignupActivity::class.java

            )
            startActivity(intent)
        }

        btnlogin.setOnClickListener {

            if (validation()) {

                userDetailsRepository.getGetAllData().observe(this, object : Observer<List<User>> {
                    override fun onChanged(t: List<User>) {
                        var userObject = t

                        for (i in userObject.indices) {
                            if (userObject[i].email?.equals(inputEmailLogin.text.toString())!!) {

                                if (userObject[i].password?.equals(inputPasswordLogin.text.toString())!!) {

                                    val intent =
                                        Intent(this@LoginActivity, MainActivity::class.java)
                                            .putExtra("UserDetials", userObject[i])
                                    // start your next activity
                                    startActivity(intent)

                                } else {
                                    Toast.makeText(
                                        this@LoginActivity,
                                        " Password is Incorrect ",
                                        Toast.LENGTH_LONG
                                    )
                                        .show()
                                }
                                isExist = true
                                break

                            } else {
                                isExist = false
                            }
                        }

                        if (isExist) {

                        } else {

                            Toast.makeText(
                                this@LoginActivity,
                                " User Not Registered ",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    }

                })
            }
        }

    }

    private fun validation(): Boolean {

        if (inputEmailLogin.text.isNullOrEmpty()) {
            Toast.makeText(this@LoginActivity, " Enter a register email ", Toast.LENGTH_LONG)
                .show()
            return false
        }
        /*if (et_mobile_no.text.toString().length != 10) {
            Toast.makeText(this@LoginActivity, " Enter 10 digit Mobile Number ", Toast.LENGTH_LONG).show()
            return false
        }*/
        if (inputPasswordLogin.text.isNullOrEmpty()) {
            Toast.makeText(this@LoginActivity, " Enter Password ", Toast.LENGTH_LONG).show()
            return false
        }
        return true

    }

    fun googleLoginPage(view: View) {

        val googlebrowserIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://accounts.google.com/signin/v2/identifier?hl=fr&passive=true&continue=https%3A%2F%2Fwww.google.fr%2F&ec=GAZAAQ&flowName=GlifWebSignIn&flowEntry=ServiceLogin")
        )
        startActivity(googlebrowserIntent)
    }

    fun facebookLoginPage(view: View) {

        val facebookbrowserIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://fr-fr.facebook.com/")
        )
        startActivity(facebookbrowserIntent)

    }
}