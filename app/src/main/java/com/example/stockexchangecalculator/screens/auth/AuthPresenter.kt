package com.example.stockexchangecalculator.screens.auth

import com.example.stockexchangecalculator.data.models.User
import com.example.stockexchangecalculator.utils.CurrentUser
import io.realm.Realm
import io.realm.kotlin.where

class AuthPresenter (private val view: AuthContract.View) : AuthContract.Presenter {

    private var realm: Realm = Realm.getDefaultInstance()

    override fun checkUser(login: String, password: String): Boolean {
        val user = realm.where<User>()
            .equalTo("userLogin", login)
            .equalTo("userPassword", password)
            .findFirst() ?: return false
        CurrentUser.currentUser = user
        return true
    }
}