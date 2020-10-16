package com.example.stockexchangecalculator.data.models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Stock (
    @PrimaryKey var stockSymbol: String = "",
    var stockName: String = "",
    var stockPrice: Double = 0.0,
    var stockChange: Double = 0.0,
    var numberOfStocks: Int = 0,
    var averagePrice: Double = 0.0
): RealmObject()

