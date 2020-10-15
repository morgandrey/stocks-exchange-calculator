package com.example.stockexchangecalculator.screens.auth

import io.realm.Realm

class AuthPresenter (private val view: AuthContract.View) : AuthContract.Presenter {

    private lateinit var realm: Realm

    override fun checkUser(): Boolean {
        realm = Realm.getDefaultInstance()

        return true
    }
}