package com.example.synopsiscompose.data.database

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import kotlin.random.Random

open class ThemeDto : RealmObject() {

    @PrimaryKey
    var id: Int = Random.nextInt()
    var name: String = ""
}