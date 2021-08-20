package com.example.synopsiscompose.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.synopsiscompose.App
import com.example.synopsiscompose.data.Thesis
import com.example.synopsiscompose.data.database.ThesisDto
import io.realm.kotlin.where

class ThesisListViewModel(private val themeId: Int, private var testVar: Int = 5) : ViewModel() {

    var thesisList = mutableStateListOf<Thesis>()
        private set

    fun getThesisList() {
        thesisList.clear()

        val thesisDtoList = App.realm.where<ThesisDto>().equalTo("themeId", themeId).findAll()

        thesisList.clear()
        thesisDtoList.map {
            thesisList.add(
                Thesis(
                    it.id,
                    it.title,
                    it.content
                )
            )
        }
    }


    fun addThesis(thesisTitle: String) {
        val thesisDto = ThesisDto()
        thesisDto.title = thesisTitle
        thesisDto.themeId = themeId

        App.realm.executeTransaction { transactionRealm ->
            transactionRealm.insert(thesisDto)
            getThesisList()
        }
    }
}

class ThesisListViewModelFactory(private val themeId: Int) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        ThesisListViewModel(themeId) as T
}