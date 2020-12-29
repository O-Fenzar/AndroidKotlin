package com.example.androidkotlin.ui.register_activity

import com.example.androidkotlin.R
import com.example.androidkotlin.data.local.UserDatabase
import com.example.androidkotlin.data.local.model.User
import com.example.androidkotlin.ui.login_viewmodel.LoginViewModel
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*


class SignupActivity : AppCompatActivity() {

    var isExist = false

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val userDetailsRepository =
            ViewModelProvider(this@SignupActivity).get(LoginViewModel::class.java)

        val btn = findViewById<TextView>(R.id.alreadyHaveAccount)
        btn.setOnClickListener {
            startActivity(
                Intent(
                    this@SignupActivity,
                    LoginActivity::class.java
                )
            )
        }

        btnRegister.setOnClickListener {
            if (validation()) {
                userDetailsRepository.getGetAllData().observe(this, object : Observer<List<User>> {
                    override fun onChanged(t: List<User>) {
                        var userObject = t

                        for (i in userObject.indices) {


                            if (userObject[i].email?.equals(inputEmailRegister.text.toString())!!) {
                                isExist = true
                                //Toast.makeText(this@SignupActivity," User Already Registered ", Toast.LENGTH_LONG).show()
                                break

                            } else {
                                isExist = false
                                continue
                            }
                        }

                        if (isExist) {
                            Toast.makeText(this@SignupActivity, " User Already Registered !!! ", Toast.LENGTH_LONG)
                                .show()

                        } else {

                            val user = User()
                            user.username = inputUsername.text.toString()
                            user.email = inputEmailRegister.text.toString()
                            user.password = inputPasswordRegister.text.toString()
                            user.confirmPassword = inputConformPassword.text.toString()
                            val userDatabase = UserDatabase
                            userDatabase.getDatabase(this@SignupActivity)?.daoAccess()?.insertUserData(user)
                            Toast.makeText(this@SignupActivity, " User  Registered Successfully", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                })
            }

        }
    }

    private fun validation(): Boolean {

        val different_password = inputPasswordRegister.getText().toString() != inputConformPassword.getText().toString()

        if (inputUsername.text.isNullOrEmpty()) {
            Toast.makeText(this@SignupActivity, " Enter a username ", Toast.LENGTH_LONG).show()
            return false
        }

        if (inputEmailRegister.text.isNullOrEmpty()) {
            Toast.makeText(this@SignupActivity, " Enter an email ", Toast.LENGTH_LONG).show()
            return false
        }
        if (inputPasswordRegister.text.toString().length != 10) {
            Toast.makeText(this@SignupActivity, " Enter a 10 digit password Number ", Toast.LENGTH_LONG).show()
            return false
        }
        if (inputConformPassword.text.isNullOrEmpty()) {
            Toast.makeText(this@SignupActivity, " Confirm your password ", Toast.LENGTH_LONG).show()
            return false
        }

        if(different_password){
            Toast.makeText(baseContext, "Different Password", Toast.LENGTH_LONG).show()
            return false
        }
        return true

    }
}

