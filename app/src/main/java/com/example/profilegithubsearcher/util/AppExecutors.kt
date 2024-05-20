package com.example.profilegithubsearcher.util

import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors {
    val diskIO : Executor = Executors.newSingleThreadExecutor()

}