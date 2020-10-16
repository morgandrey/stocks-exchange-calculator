package com.example.stockexchangecalculator.screens.portfolio

import com.example.stockexchangecalculator.data.models.Stock
import com.example.stockexchangecalculator.screens.CurrentUser
import io.realm.Realm
import io.realm.kotlin.where

class MyStocksPresenter(private val view: PortfolioContract.View) : PortfolioContract.Presenter {

    private var realm: Realm = Realm.getDefaultInstance()

    override fun deleteAllMyStocks() {
        realm.executeTransaction { realm ->
            realm.where<Stock>()
                .equalTo("userId", CurrentUser.currentUser.userId)
                .findAll()
                .deleteAllFromRealm()
        }
        realm.close()
    }

    override fun initDataset(): MutableList<Stock> {
        return CurrentUser.currentUser.stocks.toMutableList()
    }
}