package com.example.suitmediambkmtest

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.suitmediambkmtest.databinding.ActivityPalindromeBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class PalindromeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityPalindromeBinding = ActivityPalindromeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("SharedPref", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val inputLayoutName = binding.inputLayoutName
        val etInputName = binding.inputEditTextUsername
        val inputLayoutPalindrome = binding.inputLayoutPalindrome
        val etInputPalindrome = binding.inputEditTextPalindrome
        val btnCheck = binding.checkBtn
        val btnNext = binding.nextBtn

        btnCheck.setOnClickListener(View.OnClickListener {
            val palindromeInput = inputLayoutPalindrome.getEditText()?.getText().toString()
            val builder = AlertDialog.Builder(this)

            if(palindromeInput.isEmpty()){
                etInputPalindrome.setError("This filed is required for checking palindrome!")
            }else {

                if (isPalindrome(palindromeInput)) {
                    val materialAlertDialogBuilder =
                        MaterialAlertDialogBuilder(this@PalindromeActivity)
                    materialAlertDialogBuilder.setTitle("Entered sentence is palindrome")
                        .setIcon(R.drawable.baseline_check_circle_24)
                        .setNegativeButton("OK", null)
                        .show()
                } else {
                    val materialAlertDialogBuilder =
                        MaterialAlertDialogBuilder(this@PalindromeActivity)
                    materialAlertDialogBuilder.setTitle("Entered sentence is not palindrome")
                        .setIcon(R.drawable.baseline_warning_24)
                        .setNegativeButton("OK", null)
                        .show()
                }
            }
        })

        btnNext.setOnClickListener(View.OnClickListener {
            var next:Intent
            val nameInput = inputLayoutName.getEditText()?.getText().toString()

            if(nameInput.isEmpty()){
                etInputName.setError("This filed is required to continue to the next screen!")
            }else{
                editor.putString("username_input", nameInput);
                editor.commit()

                next = Intent(this@PalindromeActivity, SecondScreenActivity::class.java)
                startActivity(next)
            }

        })

    }

    private fun isPalindrome(palindrome: String): Boolean {
        val reversed = palindrome.reversed().toString()
        return palindrome.equals(reversed, ignoreCase = true)
    }
}