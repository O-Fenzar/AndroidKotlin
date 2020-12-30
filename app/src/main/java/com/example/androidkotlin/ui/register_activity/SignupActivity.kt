package com.example.androidkotlin.ui.register_activity

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.androidkotlin.R
import com.example.androidkotlin.data.local.UserDatabase
import com.example.androidkotlin.data.local.model.User
import com.example.androidkotlin.ui.login_viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import java.util.regex.Matcher
import java.util.regex.Pattern


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
                            Toast.makeText(
                                this@SignupActivity,
                                " User Already Registered !!! ",
                                Toast.LENGTH_LONG
                            )
                                .show()

                        } else {

                            val user = User()
                            user.username = inputUsername.text.toString()
                            user.email = inputEmailRegister.text.toString()
                            user.password = inputPasswordRegister.text.toString()
                            user.confirmPassword = inputConformPassword.text.toString()
                            val userDatabase = UserDatabase
                            userDatabase.getDatabase(this@SignupActivity)?.daoAccess()
                                ?.insertUserData(
                                    user
                                )
                            Toast.makeText(
                                this@SignupActivity,
                                " User  Registered Successfully",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
                    }
                })
            }

        }
    }

    private fun validation(): Boolean {

        val different_password =
            inputPasswordRegister.getText().toString() != inputConformPassword.getText().toString()

        //Username condition

        if (inputUsername.text.isNullOrEmpty()) {
            Toast.makeText(this@SignupActivity, " Enter a username ", Toast.LENGTH_LONG).show()
            return false
        }
        if (inputUsername.text.toString().length < 4) {
            Toast.makeText(this@SignupActivity, " Username too short ", Toast.LENGTH_LONG).show()
            return false
        } else if (inputUsername.text.toString().length >= 10) {

            Toast.makeText(this@SignupActivity, " Username too long, please enter an username between 4 and 9 characteres ", Toast.LENGTH_LONG).show()
            return false
        }

        //Email condition

        if (inputEmailRegister.text.isNullOrEmpty()) {
            Toast.makeText(this@SignupActivity, " Enter an email ", Toast.LENGTH_LONG).show()
            return false
        }

        val textInputEmail = findViewById<EditText>(R.id.inputEmailRegister)
        val emailInput: String = textInputEmail.getText().toString().trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {

            Toast.makeText(this@SignupActivity, " Enter a correct email ", Toast.LENGTH_LONG).show()
            return false

        }

        // Password condition

        if (inputPasswordRegister.text.isNullOrEmpty()) {

            Toast.makeText(this@SignupActivity, " You need a password ", Toast.LENGTH_LONG).show()
            return false
        }

        val textInputPassword = findViewById<EditText>(R.id.inputPasswordRegister)
        val passwordInput: String = textInputPassword.getText().toString().trim()
        if (!isValidPassword(passwordInput)) {

            Toast.makeText(this@SignupActivity, " Please enter a correct Password you need at least : " +
                    "1 digit," +
                    " 1 lower case letter," +
                    " 1 upper case letter," +
                    " 1 special character," +
                    " 8 characters," +
                    " and no white spaces."
                , Toast.LENGTH_LONG).show()
            return false

        }

        if (inputConformPassword.text.isNullOrEmpty()) {

            Toast.makeText(this@SignupActivity, " Confirm your password ", Toast.LENGTH_LONG).show()
            return false
        }

        if (different_password) {

            Toast.makeText(baseContext, "Different Password", Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }

    private fun isValidPassword(passwordInput: String): Boolean {
        val passwordREGEX = Pattern.compile(
            "^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{8,}" +               //at least 8 characters
                "$");
        return passwordREGEX.matcher(passwordInput).matches()
    }


}


