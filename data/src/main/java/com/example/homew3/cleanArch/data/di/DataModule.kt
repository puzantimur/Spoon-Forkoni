package com.example.homew3.cleanArch.data.di

import org.koin.dsl.module

val dataModule = module {
    includes(
        apiModule,
        repositoryModule,
        roomModule
    )
}