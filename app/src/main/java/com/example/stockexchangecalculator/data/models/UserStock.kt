package com.example.stockexchangecalculator.data.models

import io.realm.RealmObject

open class UserStock (
    var userId: String = "",
    var stockId: String = ""
) : RealmObject()