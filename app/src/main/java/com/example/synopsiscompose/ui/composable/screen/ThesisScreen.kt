package com.example.synopsiscompose.ui.composable.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.synopsiscompose.R
import com.example.synopsiscompose.data.Thesis
import com.example.synopsiscompose.viewmodel.ThesisViewModel

@Composable
fun ThesisScreen(viewModel: ThesisViewModel, onUpClick: () -> Unit) {

    val editMode = rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sample thesis") },
                navigationIcon = {
                    IconButton(onClick = onUpClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                })
        },
        floatingActionButton = {
            if (editMode.value) {
                FloatingActionButton(onClick = {
                    editMode.value = false
                    viewModel.saveThesis()
                }) {
                    Icon(painterResource(id = R.drawable.ic_save), contentDescription = null)
                }
            }
        }
    ) {
        viewModel.thesis.value?.let { thesis ->
            ThesisDetails(
                thesis = thesis,
                editMode = editMode.value,
                modifier = if (!editMode.value) Modifier.clickable {
                    editMode.value = true
                } else Modifier,
                onThesisUpdated = viewModel::updateThesis)
        }
    }
}


@Composable
fun ThesisDetails(
    modifier: Modifier = Modifier,
    thesis: Thesis,
    editMode: Boolean,
    onThesisUpdated: (String) -> Unit,
) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(start = 33.dp, top = 24.dp, end = 24.dp)) {
        Text(
            text = thesis.title,
            modifier = Modifier.padding(bottom = 24.dp),
            fontSize = 24.sp,
        )
        BasicTextField(
            value = thesis.content,
            onValueChange = { onThesisUpdated(it) },
            enabled = editMode,
            textStyle = TextStyle(fontSize = 14.sp),
        )
    }
}