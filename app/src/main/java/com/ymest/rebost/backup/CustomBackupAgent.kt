package com.ymest.rebost.backup

import android.app.backup.BackupAgentHelper
import android.app.backup.FileBackupHelper
import java.io.File


class CustomBackupAgent : BackupAgentHelper() {

    private val DB_NAME = "tProductes"

    override fun onCreate() {
        val dbs = FileBackupHelper(this, DB_NAME)
        addHelper("dbs", dbs)
    }

    override fun getFilesDir(): File? {
        val path: File = getDatabasePath(DB_NAME)
        return path.getParentFile()
    }
}