package com.example.synopsiscompose.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.synopsiscompose.App
import com.example.synopsiscompose.data.Thesis
import com.example.synopsiscompose.data.database.ThesisDto
import io.realm.kotlin.where

class ThesisViewModel(private val thesisId: Int): ViewModel() {

    val thesis = mutableStateOf<Thesis?>(null)

    init {
        getThesis()
    }

    private fun getThesis() {
        val thesisDto = App.realm.where<ThesisDto>().equalTo("id", thesisId).findFirst()

        thesisDto?.let {
            thesis.value = Thesis(it.id, it.title, it.content)
        }
    }

    fun updateThesis(content: String) {
        thesis.value =  thesis.value?.copy(content = content)
    }

    fun saveThesis() {
        thesis.value?.let { App.realm.executeTransaction { transactionRealm ->
            val thesisToUpdate = transactionRealm.where<ThesisDto>().equalTo("id", it.id).findFirst()!!
            thesisToUpdate.content = it.content
            getThesis()
        } }
    }
}

class ThesisViewModelFactory(private val thesisId: Int) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        ThesisViewModel(thesisId) as T
}