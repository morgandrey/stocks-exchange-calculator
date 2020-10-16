package com.example.stockexchangecalculator.screens.auth

interface AuthContract {
    interface View {

    }

    interface Presenter {
        fun checkUser(login: String, password: String): Boolean
    }
}