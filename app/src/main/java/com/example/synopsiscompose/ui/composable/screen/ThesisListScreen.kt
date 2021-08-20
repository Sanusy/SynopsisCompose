package com.example.synopsiscompose.ui.composable.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.synopsiscompose.R
import com.example.synopsiscompose.data.Thesis
import com.example.synopsiscompose.ui.composable.TextFieldAlertDialog
import com.example.synopsiscompose.ui.theme.PlainTextColor
import com.example.synopsiscompose.viewmodel.ThesisListViewModel

@Composable
fun ThesisListScreen(
    viewModel: ThesisListViewModel,
    onUpClick: () -> Unit,
    onThesisClick: (Int) -> Unit
) {

    viewModel.getThesisList()

    val isAddingThesis = rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Theme") }, navigationIcon = {
                IconButton(onClick = onUpClick) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null)
                }
            })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { isAddingThesis.value = true }) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        },
    ) {
        LazyColumn {
            items(viewModel.thesisList) {
                ThesisItem(thesis = it, onThesisClick = onThesisClick)
            }
        }
        if (isAddingThesis.value) {
            TextFieldAlertDialog(
                dialogTitle = stringResource(id = R.string.enter_thesis_name),
                onDismissRequest = { isAddingThesis.value = false },
                onConfirmClick = { thesisTitle ->
                    viewModel.addThesis(thesisTitle)
                    isAddingThesis.value = false
                })
        }
    }
}

@Composable
private fun ThesisItem(thesis: Thesis, onThesisClick: (Int) -> Unit) {
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onThesisClick(thesis.id) }) {
        Column(
            modifier = Modifier.padding(
                start = 24.dp,
                top = 16.dp,
                end = 24.dp,
                bottom = 24.dp
            )
        ) {
            Text(text = thesis.title, modifier = Modifier.padding(bottom = 8.dp))
            Text(text = thesis.content, fontSize = 14.sp, color = PlainTextColor)
        }
    }
}