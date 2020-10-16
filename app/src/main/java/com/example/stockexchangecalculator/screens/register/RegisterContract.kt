package com.example.stockexchangecalculator.screens.register

interface RegisterContract {
    interface View {
        fun onError()
        fun onSuccess()
    }

    interface Presenter {
        fun createUser(login: String, password: String): Boolean
    }
}