package com.example.stockexchangecalculator.screens.portfolio

import com.example.stockexchangecalculator.data.models.Stock
import com.example.stockexchangecalculator.utils.CurrentUser
import io.realm.Realm

class PortfolioPresenter(private val view: PortfolioContract.View) : PortfolioContract.Presenter {

    private var realm: Realm = Realm.getDefaultInstance()

    override fun deleteAllMyStocks() {
        realm.executeTransaction {
            CurrentUser.currentUser.stocks.deleteAllFromRealm()
        }
        realm.close()
        view.setupDataset()
    }

    override fun initDataset(): MutableList<Stock> {
        return CurrentUser.currentUser.stocks.toMutableList()
    }
}