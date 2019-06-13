package io.github.kmeret.frame.di

import androidx.room.Room
import io.github.kmeret.frame.demo.BuildConfig
import io.github.kmeret.frame.storage.Database
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module

val storageModule = module {
    single {
        Room.databaseBuilder(
                androidApplication(),
                Database::class.java,
                BuildConfig.APPLICATION_ID
        ).allowMainThreadQueries().build()
    }
    single { get<Database>().repoDao() }
    single { get<Database>().profileDao() }
    single { get<Database>().topicDao() }
}