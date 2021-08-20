package com.example.synopsiscompose.ui.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.synopsiscompose.R
import com.example.synopsiscompose.data.Theme
import com.example.synopsiscompose.ui.theme.LightGray
import com.example.synopsiscompose.viewmodel.ThemeListViewModel
import kotlin.random.Random

@Composable
fun ThemeListScreen(themeListViewModel: ThemeListViewModel, onThemeClick: (Int) -> Unit) {

    themeListViewModel.getThemes()

    val isAddingTheme = rememberSaveable { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(id = R.string.app_title)) })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { isAddingTheme.value = true }) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }) {
        LazyColumn {
            items(themeListViewModel.themes) { theme ->
                ThemeItem(theme = theme, onThemeClick = onThemeClick)
            }
        }
        if (isAddingTheme.value) {
            TextFieldAlertDialog(
                dialogTitle = stringResource(id = R.string.enter_topic_name),
                onDismissRequest = { isAddingTheme.value = false },
                onConfirmClick = { themeTitle ->
//                    val themeToAdd = Theme(Random.nextInt(), themeTitle, 0)// TODO: 7/6/21 test
                    themeListViewModel.addTheme(themeTitle)
                    isAddingTheme.value = false
                })
        }
    }
}

@Composable
private fun ThemeItem(theme: Theme, onThemeClick: (Int) -> Unit) {
    Column(modifier = Modifier
        .clickable {
            onThemeClick(
                theme.id
            )
        }
        .padding(start = 24.dp, end = 8.dp, top = 18.dp, bottom = 4.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 14.dp)
        ) {
            Text(theme.name)
            Row(modifier = Modifier.padding(end = 10.dp)) {
                Text(
                    text = theme.thesisCount.toString(),
                    modifier = Modifier.padding(end = 10.dp),
                    color = LightGray
                )
                Icon(
                    painterResource(R.drawable.ic_bookmark),
                    contentDescription = null,
                    tint = LightGray
                )
            }
        }
        Divider(color = LightGray)
    }
}