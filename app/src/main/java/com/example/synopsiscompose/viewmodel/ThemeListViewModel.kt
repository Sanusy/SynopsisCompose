package com.example.synopsiscompose.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.synopsiscompose.App
import com.example.synopsiscompose.data.Theme
import com.example.synopsiscompose.data.database.ThemeDto
import com.example.synopsiscompose.data.database.ThesisDto
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.where

class ThemeListViewModel : ViewModel() {

    var themes = mutableStateListOf<Theme>()
        private set

    fun getThemes() {
        themes.clear()

        val retreivedThemes = App.realm.where<ThemeDto>().findAll()

        themes.clear()
        retreivedThemes.map {

            val thesisCount = App.realm.where<ThesisDto>().equalTo("themeId", it.id).findAll().size

            themes.add(
                Theme(
                    it.id,
                    it.name,
                    thesisCount
                )
            )
        }
    }

    fun addTheme(themeName: String) {
        val themeDto = ThemeDto()
        themeDto.name = themeName

        App.realm.executeTransaction { transactionRealm ->
            transactionRealm.insert(themeDto)
            getThemes()
        }
    }
}
