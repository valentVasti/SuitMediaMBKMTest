package com.example.suitmediambkmtest

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.suitmediambkmtest.databinding.ActivitySecondScreenBinding


class SecondScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivitySecondScreenBinding = ActivitySecondScreenBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val nameTextView = binding.nameTextView
        val selectedUserName = binding.selectedTextView

        val chooseBtn = binding.chooseBtn

        val bundle = intent.extras
        val choosed_username = bundle?.getString("user_name")

        val sharedPref = getSharedPreferences("SharedPref", MODE_PRIVATE)
        val username_input = sharedPref.getString("username_input", "").toString()

        nameTextView.text = username_input

        if(!choosed_username.isNullOrEmpty()){
            selectedUserName.text = choosed_username
        }

        chooseBtn.setOnClickListener{
            var next: Intent

            next = Intent(this@SecondScreenActivity, ThirdScreenActivity::class.java)
            startActivity(next)
        }
    }
}