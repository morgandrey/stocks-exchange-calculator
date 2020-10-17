package com.example.stockexchangecalculator.screens.auth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.stockexchangecalculator.R
import com.example.stockexchangecalculator.databinding.FragmentAuthBinding

class AuthFragment : Fragment(), AuthContract.View {

    private lateinit var binding: FragmentAuthBinding
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var authPresenter: AuthPresenter
    private lateinit var loginEditText: EditText
    private lateinit var passwordEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_auth, container, false
        )
        authPresenter = AuthPresenter(this)
        loginEditText = binding.loginEditText
        passwordEditText = binding.passwordEditText
        loginButton = binding.loginButton
        loginButton.setOnClickListener { view: View ->
            if (authPresenter.checkUser(
                    loginEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            ) {
                dismissKeyboard()
                view.findNavController().navigate(R.id.action_authFragment_to_portfolioFragment)
            } else {
                Toast.makeText(
                    this.context,
                    "Такого пользователя не существует",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        registerButton = binding.registerButton
        registerButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_authFragment_to_registerFragment)
        }

        return binding.root
    }

    private fun dismissKeyboard() {
        val activity = requireActivity()
        val inputMethodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (null != activity.currentFocus) {
            inputMethodManager.hideSoftInputFromWindow(
                activity.currentFocus!!.applicationWindowToken, 0
            )
        }
    }
}