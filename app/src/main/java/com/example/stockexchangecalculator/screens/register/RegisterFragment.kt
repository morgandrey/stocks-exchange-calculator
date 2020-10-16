package com.example.stockexchangecalculator.screens.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.stockexchangecalculator.R
import com.example.stockexchangecalculator.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment(), RegisterContract.View {

    private lateinit var registerPresenter: RegisterPresenter
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var registerButton: Button
    private lateinit var loginEditText: EditText
    private lateinit var passwordEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_register, container, false
        )
        registerButton = binding.registerNewButton
        loginEditText = binding.loginEditText
        passwordEditText = binding.passwordEditText
        registerPresenter = RegisterPresenter(this)

        registerButton.setOnClickListener {
            if (registerPresenter.createUser(
                    loginEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            ) {
                onSuccess()
            } else {
                onError()
            }
        }
        return binding.root
    }

    override fun onError() {
        Toast.makeText(
            this.context,
            "Выберите другой логин, такой пользователь уже существует",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onSuccess() {
        Toast.makeText(this.context, "Новый пользователь создан", Toast.LENGTH_SHORT).show()
        requireView().findNavController().navigate(R.id.action_registerFragment_to_authFragment)
    }
}