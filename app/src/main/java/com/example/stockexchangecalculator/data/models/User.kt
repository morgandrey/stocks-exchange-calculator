package com.example.stockexchangecalculator.data.models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class User(
    @PrimaryKey var userId: String = UUID.randomUUID().toString(),
    var userLogin: String = "",
    var userPassword: String = "",
    var userMoney: Double = 10_000.0,
    var stocks: RealmList<Stock> = RealmList()
) : RealmObject()
