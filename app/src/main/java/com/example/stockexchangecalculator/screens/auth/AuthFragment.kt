package com.example.stockexchangecalculator.screens.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.stockexchangecalculator.R
import com.example.stockexchangecalculator.databinding.FragmentAuthBinding

class AuthFragment : Fragment(), AuthContract.View  {

    private lateinit var binding: FragmentAuthBinding
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var presenter: AuthPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_stock, container, false
        )
        presenter = AuthPresenter(this)
        loginButton = binding.loginButton
        loginButton.setOnClickListener { view: View ->
            if (presenter.checkUser()) {
                view.findNavController().navigate(R.id.action_authFragment_to_titleFragment)
            } else {
                Toast.makeText(this.context, "Такого пользователя не существует", Toast.LENGTH_SHORT).show()
            }
        }
        registerButton = binding.registerButton
        registerButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_authFragment_to_registerFragment)
        }

        return binding.root
    }
}