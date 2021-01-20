package com.ymest.rebost.ajustes

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.opencsv.CSVParserBuilder
import com.opencsv.CSVReaderBuilder
import com.ymest.rebost.R
import com.ymest.rebost.json.*
import com.ymest.rebost.sqlite.*
import kotlinx.android.synthetic.main.activity_backup.*
import java.io.*
import java.nio.channels.FileChannel
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import kotlin.collections.ArrayList


class BackupActivity : AppCompatActivity() {
    var DB_NAME = Column.Companion.TProductes.T_PRODUCTES
    lateinit var llistesCrud: TaulaLlistesCrud
    //Permisos STORAGE
    private val STORAGE_REQUEST_CODE_EXPORT = 1
    private val STORAGE_REQUEST_CODE_IMPORT = 2
    private lateinit var storagePermission: Array<String>
    lateinit var fw : FileWriter
    var fileNameAndPath: String = ""
    var mAuth = FirebaseAuth.getInstance()
    lateinit var folder: File
    lateinit var newBackup: Backup

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_backup)

        initVars()
        clickEvents()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun clickEvents() {
        btnBackupNow.setOnClickListener {
            if(checkStoragePermission()){
                newBackup.exportCSV()
                newBackup.exportCSVtoFirebase()
            } else{
                requestStoragePermissionExport()
            }
        }

        btnRecoveryNow.setOnClickListener {
            if(checkStoragePermission()){
                if(mAuth.currentUser != null){
                    newBackup.copyCSVfromFirebase()
                }else{
                    newBackup.importCSV()
                }
            } else{
                requestStoragePermissionImport()
            }
        }
    }

    private fun initVars() {
        folder = File(
            Environment.getExternalStorageDirectory().toString() + "/" + "com.ymest.rebost"
        )
        storagePermission = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        llistesCrud = TaulaLlistesCrud(this)
        newBackup = Backup(this)
    }

    private fun checkStoragePermission(): Boolean{
        return ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == (PackageManager.PERMISSION_GRANTED)
    }

    private fun requestStoragePermissionImport(){
        ActivityCompat.requestPermissions(this, storagePermission, STORAGE_REQUEST_CODE_IMPORT)
    }

    private fun requestStoragePermissionExport(){
        ActivityCompat.requestPermissions(this, storagePermission, STORAGE_REQUEST_CODE_EXPORT)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            STORAGE_REQUEST_CODE_EXPORT -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    newBackup.exportCSV()
                    newBackup.exportCSVtoFirebase()
                } else {
                    Toast.makeText(this, "Permiso Denegado", Toast.LENGTH_SHORT).show()
                }
            }
            STORAGE_REQUEST_CODE_IMPORT -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(mAuth.currentUser != null){
                        newBackup.copyCSVfromFirebase()
                    }else{
                        newBackup.importCSV()
                    }
                } else {
                    Toast.makeText(this, "Permiso Denegado", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}