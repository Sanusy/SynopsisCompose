package com.example.synopsiscompose.data.database

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import kotlin.random.Random

open class ThesisDto: RealmObject() {

    @PrimaryKey
    var id: Int = Random.nextInt()
    var title: String = ""
    var content: String = ""
    var themeId: Int = 0
}