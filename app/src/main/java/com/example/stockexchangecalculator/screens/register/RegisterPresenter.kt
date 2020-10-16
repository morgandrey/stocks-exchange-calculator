package com.example.stockexchangecalculator.screens.register

import com.example.stockexchangecalculator.data.models.User
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import java.lang.Exception
import java.util.*

class RegisterPresenter (private val view: RegisterContract.View) : RegisterContract.Presenter {

    private var realm: Realm = Realm.getDefaultInstance()

    override fun createUser(login: String, password: String): Boolean {
        try {
            if (!checkLogin(login)) {
                return false
            }
            realm.executeTransaction { realm ->
                val user = realm.createObject<User>(UUID.randomUUID().toString())
                user.userLogin = login
                user.userPassword = password
            }
            return true
        } catch (e: Exception) {
            print(e.message)
            return false
        }
    }

    private fun checkLogin(login: String): Boolean {
        val user = realm.where<User>()
            .equalTo("userLogin", login)
            .findFirst()
        return when (user) {
            null -> true
            else -> false
        }
    }
}