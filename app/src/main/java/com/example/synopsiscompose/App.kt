package com.example.synopsiscompose

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class App : Application() {

    companion object{
        lateinit var realm: Realm
    }


    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
        val realmName = "Synopsis"

        val config =
            RealmConfiguration.Builder()
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .name(realmName).build()
        Realm.getInstanceAsync(config, object : Realm.Callback() {
            override fun onSuccess(r: Realm) {
                realm = r
            }
        })
    }
}