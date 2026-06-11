package com.example.noteplusadmin

import android.app.Application
import com.example.noteplusadmin.data.database.AppDatabase

class NotePlusAdminApp : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}
