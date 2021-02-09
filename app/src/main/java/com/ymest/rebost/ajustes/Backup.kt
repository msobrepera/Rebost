package com.ymest.rebost.ajustes

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings.Global.getString
import android.provider.Settings.System.getString
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.opencsv.CSVParserBuilder
import com.opencsv.CSVReaderBuilder
import com.ymest.rebost.MainActivity
import com.ymest.rebost.R
import com.ymest.rebost.json.*
import com.ymest.rebost.sqlite.*
import java.io.File
import java.io.FileWriter
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

class Backup(var ctx: Context) {
    lateinit var fw : FileWriter
    var fileNameAndPath: String = ""
    var mAuth = FirebaseAuth.getInstance()
    var folder: File = File(
        Environment.getExternalStorageDirectory().toString() + "/" + "com.ymest.rebost"
    )
    
    fun exportCSV(){
        createFolder()
        val csvFileName = "SQLite_Backup.csv"
        fileNameAndPath = "$folder/$csvFileName"
        fw = FileWriter(fileNameAndPath)

        exportCSVLlistes()
        exportCSVUbicacions()
        exportCSVProductes()
        exportCSVProductesALlistes()
        exportCSVTaulaPersonalitzada()

        fw.flush()
        fw.close()

        Toast.makeText(ctx, "Exportación finalizada", Toast.LENGTH_SHORT).show()

    }

    fun exportCSVtoFirebase() {
        if(mAuth.currentUser != null){
            val storage = Firebase.storage
            val storageRef = storage.reference

            val file = Uri.fromFile(File(fileNameAndPath))
            val csvRef: StorageReference? = storageRef.child(mAuth.uid.toString() + "_Backup.csv")
            val uploadTask = csvRef?.putFile(file)

            uploadTask?.addOnFailureListener {
                Log.d("FIREBASEERR", it.message.toString())
                Toast.makeText(ctx, "Error al guardar copia en la nube", Toast.LENGTH_SHORT).show()
            }
            uploadTask?.addOnSuccessListener {
                Toast.makeText(ctx, "Copia guardada en la nube", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun copyCSVfromFirebase() {
        var storageRef = Firebase.storage.reference
        Log.d("FIREBASEERR", "Existe onSuccessListener")
        createFolder()
        val csvFileName = "SQLite_Backup"
        val localFile = File.createTempFile(csvFileName, ".csv", folder)
        vaciaDirectorio()
        storageRef.child(mAuth.uid.toString() + "_Backup.csv").getFile(localFile).addOnSuccessListener {
            Log.d("FIREBASEERR", "Archivo copiado")
            importCSV()
        }
    }

    fun eliminarArchivosBackupFirebase(){
        var storageRef = Firebase.storage.reference
        Log.d("FIREBASEERR", "Existe onSuccessListener")
        storageRef.child(mAuth.uid.toString() + "_Backup.csv").delete()
        Log.d("FIREBASEERR", "Archivo eliminado")
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun importCSV(){
        var hijos = folder.listFiles()
        hijos.sortByDescending { it.lastModified() }

        for(i in hijos.indices) {
            Log.d("FIREBASEERR", hijos[i].name + ": " + hijos[i].lastModified().toString())
        }

        val filePathAndName = "${Environment.getExternalStorageDirectory()}/com.ymest.rebost/${hijos[0].name}"
        val path = Paths.get(filePathAndName)
        val csvFile = File(filePathAndName)

        var parser = CSVParserBuilder().withSeparator(';').build()
        var br = Files.newBufferedReader(path, StandardCharsets.UTF_8)

        if (csvFile.exists()){
            try{
                /*var dbHelper = DataBaseHelper(ctx)
                dbHelper.onUpgrade(dbHelper.writableDatabase,0,0)*/
                TaulaLlistesCrud(ctx).deleteAllLlista()
                TaulaUbicacionsCrud(ctx).deleteAllUbicacio()
                TaulaProductesCrud(ctx).deleteAllProducte()
                TaulaProductesALlistesCrud(ctx).deleteProductesDeAllLlista()
                TaulaPersonalitzadaCrud(ctx).deleteAllProductePersonalitzat()
                var csvReader = CSVReaderBuilder(br).withCSVParser(parser).build()
                val rows: List<Array<String>> = csvReader.readAll()

                for (nextLine in rows) {
                    val taula = nextLine[0]
                    when(taula){
                        Column.Companion.TLlistes.T_LLISTES -> {
                            val idd = nextLine[1].toInt()
                            val nomLlista = nextLine[2]
                            val gestionaDataCad = nextLine[3].toInt()
                            val gestionaCantidad = nextLine[4].toInt()
                            val gestionaUbicaciones = nextLine[5].toInt()
                            TaulaLlistesCrud(ctx).addLlista(
                                idd,
                                nomLlista,
                                gestionaDataCad,
                                gestionaCantidad,
                                gestionaUbicaciones
                            )
                        }
                        Column.Companion.TUbicacions.T_UBICACIONS -> {
                            val ubicacio = Ubicacio(nextLine[1].toInt(), nextLine[2], nextLine[3])
                            TaulaUbicacionsCrud(ctx).addUbicacio(ubicacio)
                        }
                        Column.Companion.TProductes.T_PRODUCTES -> {
                            val producte = Producto(
                                Product(
                                    productNameEs = nextLine[4],
                                    productNameEn = nextLine[5],
                                    genericName = nextLine[6],
                                    genericNameEs = nextLine[7],
                                    productName = nextLine[8],
                                    quantity = nextLine[9],
                                    productQuantity = nextLine[10],
                                    servingSize = nextLine[11],
                                    servingQuantity = nextLine[12],
                                    brands = nextLine[13],
                                    stores = nextLine[14],
                                    storesTags = listOf(nextLine[15]),
                                    manufacturingPlaces = nextLine[16],
                                    categories = nextLine[17],
                                    categoriesTags = listOf(nextLine[18]),
                                    categoriesHierarchy = listOf(nextLine[19]),
                                    pnnsGroups1 = nextLine[20],
                                    pnnsGroups2 = nextLine[21],
                                    ingredients = null,
                                    ingredientsTags = listOf(nextLine[23]),
                                    /*ingredientsText = nextLine[24],
                                    ingredientsTextEs = nextLine[25],
                                    ingredientsTextEn = nextLine[26],
                                    ingredientsTextWithAllergens = nextLine[27],
                                    ingredientsTextWithAllergensEs = nextLine[28],
                                    ingredientsTextWithAllergensEn = nextLine[29],*/
                                    ingredientsFromPalmOilTags = listOf(nextLine[30]),
                                    additivesTags = listOf(nextLine[31]),
                                    additivesOriginalTags = listOf(nextLine[32]),
                                    allergensTags = listOf(nextLine[33]),
                                    allergensHierarchy = listOf(nextLine[34]),
                                    traces = nextLine[35],
                                    nutritionGrades = nextLine[36],
                                    nutritionDataPreparedPer = nextLine[37],
                                    nutritionDataPer = nextLine[38],
                                    nutrientLevelsTags = listOf(nextLine[39]),
                                    imageUrl = nextLine[40],
                                    imageSmallUrl = nextLine[41],
                                    imageThumbUrl = nextLine[42],
                                    imageIngredientsUrl = nextLine[43],
                                    imageIngredientsSmallUrl = nextLine[44],
                                    imageIngredientsThumbUrl = nextLine[45],
                                    imageNutritionUrl = nextLine[46],
                                    imageNutritionSmallUrl = nextLine[47],
                                    imageNutritionThumbUrl = nextLine[48],
                                    imageFrontUrl = nextLine[49],
                                    imageFrontSmallUrl = nextLine[50],
                                    imageFrontThumbUrl = nextLine[51],
                                    novaGroup = nextLine[52].toInt(),
                                    novaGroups = nextLine[53],
                                    nutriscoreScore = nextLine[54].toInt(),
                                    nutriscoreGrade = nextLine[55],
                                    uniqueScansN = nextLine[56].toInt(),
                                    scansN = nextLine[57].toInt(),
                                    keywords = listOf(nextLine[58])
                                ),
                                code = nextLine[1],
                                status = nextLine[2].toInt(),
                                statusVerbose = nextLine[3],
                            )
                            TaulaProductesCrud(ctx).addProducte(producte)
                        }
                        Column.Companion.TProductesALlista.T_PRODUCTESALLISTA -> {
                            val prodALlista = ProducteALlista(
                                0,
                                nextLine[1],
                                nextLine[2].toInt(),
                                nextLine[3].toInt(),
                                nextLine[4].toLong(),
                                nextLine[5].toInt(),
                                nextLine[6].toInt()
                            )
                            TaulaProductesALlistesCrud(ctx).addProducteALlista(prodALlista)
                        }
                        Column.Companion.TPersonalitzada.T_PERSONALITZADA -> {
                            val personalitzat = Personalitzat(
                                nextLine[1],
                                nextLine[2].toInt(),
                                nextLine[3].toInt(),
                                nextLine[4].toInt(),
                                nextLine[5].toInt(),
                                nextLine[6].toDouble()
                            )
                            TaulaPersonalitzadaCrud(ctx).addProductePersonalitzat(personalitzat)
                        }
                    }
                }
                muestraADCopiaRecuperada()
            } catch (e: Exception){
                Toast.makeText(ctx, "ERROR RECUPERANDO: " + e.message, Toast.LENGTH_SHORT).show()
                Log.d("IMPORTCSV", e.message.toString())
            }
        }else {
            Toast.makeText(ctx, "No existe copia de seguridad", Toast.LENGTH_SHORT).show()
        }
    }

    private fun muestraADCopiaRecuperada() {
        val builder = AlertDialog.Builder(ctx)
        builder.setTitle(R.string.backup)
        builder.setMessage(R.string.copia_recuperada)
        builder.setPositiveButton("ACEPTAR"){ dialog, which ->
            var intent = Intent(ctx, MainActivity::class.java)
            startActivity(ctx, intent, null)
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun exportCSVTaulaPersonalitzada() {
        var recordList = ArrayList<Personalitzat>()
        recordList.clear()
        recordList = TaulaPersonalitzadaCrud(ctx).getAllProductePersonalitzat()
        try{
            for(i in recordList.indices){
                fw.append("" + Column.Companion.TPersonalitzada.T_PERSONALITZADA)
                fw.append(";")
                fw.append("" + recordList[i].code)
                fw.append(";")
                fw.append("" + recordList[i].dataAfegit)
                fw.append(";")
                fw.append("" + recordList[i].dataVist)
                fw.append(";")
                fw.append("" + recordList[i].favorit)
                fw.append(";")
                fw.append("" + recordList[i].gust)
                fw.append(";")
                fw.append("" + recordList[i].puntuacio)
                fw.append("\n")
            }

        } catch (e: Exception){
            Toast.makeText(ctx, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun exportCSVProductesALlistes() {
        var recordList = ArrayList<ProducteALlista>()
        recordList.clear()
        recordList = TaulaProductesALlistesCrud(ctx).getProductesAllLlistes()
        try{
            for(i in recordList.indices){
                fw.append("" + Column.Companion.TProductesALlista.T_PRODUCTESALLISTA)
                fw.append(";")
                fw.append("" + recordList[i].code)
                fw.append(";")
                fw.append("" + recordList[i].idLlista)
                fw.append(";")
                fw.append("" + recordList[i].quantitat)
                fw.append(";")
                fw.append("" + recordList[i].dataCaducitat)
                fw.append(";")
                fw.append("" + recordList[i].idUbicacio)
                fw.append(";")
                fw.append("" + recordList[i].comprat)
                fw.append("\n")
            }

        } catch (e: Exception){
            Toast.makeText(ctx, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun exportCSVProductes() {
        var recordList = ArrayList<Producto>()
        recordList.clear()
        recordList = TaulaProductesCrud(ctx).getProductes()
        try{
            for(i in recordList.indices){
                fw.append("" + Column.Companion.TProductes.T_PRODUCTES)
                fw.append(";")
                fw.append("" + recordList[i].code?.replace(";", ","))
                fw.append(";")
                fw.append("" + recordList[i].status)
                fw.append(";")
                fw.append("" + recordList[i].statusVerbose?.replace(";", ","))
                fw.append(";")
                fw.append("" + recordList[i].product?.productNameEs?.replace(";", ","))
                fw.append(";")
                fw.append("" + recordList[i].product?.productNameEn?.replace(";", ","))
                fw.append(";")
                fw.append("" + recordList[i].product?.genericName?.replace(";", ","))
                fw.append(";")
                fw.append("" + recordList[i].product?.genericNameEs?.replace(";", ","))
                fw.append(";")
                fw.append("" + recordList[i].product?.productName?.replace(";", ","))
                fw.append(";")
                fw.append("" + recordList[i].product?.quantity?.replace(";", ","))
                fw.append(";")
                fw.append("" + recordList[i].product?.productQuantity?.replace(";", ","))
                fw.append(";")
                fw.append("" + recordList[i].product?.servingSize?.replace(";", ","))
                fw.append(";")
                fw.append("" + recordList[i].product?.servingQuantity?.replace(";", ","))
                fw.append(";")
                fw.append("" + recordList[i].product?.brands?.replace(";", ","))
                fw.append(";")
                fw.append("" + recordList[i].product?.stores?.replace(";", ","))
                fw.append(";")
                fw.append("" + recordList[i].product?.storesTags?.toString()?.replace(";", ","))
                fw.append(";")
                fw.append("" + recordList[i].product?.manufacturingPlaces?.replace(";", ","))
                fw.append(";")
                fw.append("" + recordList[i].product?.categories?.replace(";", ","))
                fw.append(";")
                fw.append("" + recordList[i].product?.categoriesTags?.toString()?.replace(";", ","))
                fw.append(";")
                fw.append(
                    "" + recordList[i].product?.categoriesHierarchy?.toString()?.replace(
                        ";",
                        ","
                    )
                )
                fw.append(";")
                fw.append("" + recordList[i].product?.pnnsGroups1).toString().replace(";", ",")
                fw.append(";")
                fw.append("" + recordList[i].product?.pnnsGroups2).toString().replace(";", ",")
                fw.append(";")
                fw.append("" + recordList[i].product?.ingredients).toString().replace(";", ",")
                fw.append(";")
                fw.append("" + recordList[i].product?.ingredientsTags).toString().replace(
                    ";",
                    ","
                )
                fw.append(";")
                fw.append("" /*+ recordList[i].product?.ingredientsText*/).toString().replace(
                    ";",
                    ","
                )
                fw.append(";")
                fw.append("" /*+ recordList[i].product?.ingredientsTextEs*/).toString().replace(
                    ";",
                    ","
                )
                fw.append(";")
                fw.append("" /*+ recordList[i].product?.ingredientsTextEn*/).toString().replace(
                    ";",
                    ","
                )
                fw.append(";")
                fw.append("" /*+ recordList[i].product?.ingredientsTextWithAllergens*/).toString().replace(
                    ";",
                    ","
                )
                fw.append(";")
                fw.append("" /*+ recordList[i].product?.ingredientsTextWithAllergensEs*/).toString().replace(
                    ";",
                    ","
                )
                fw.append(";")
                fw.append("" /*+ recordList[i].product?.ingredientsTextWithAllergensEn*/).toString().replace(
                    ";",
                    ","
                )
                fw.append(";")
                fw.append("" + recordList[i].product?.ingredientsFromPalmOilTags).toString().replace(
                    ";",
                    ","
                )
                fw.append(";")
                fw.append("" + recordList[i].product?.additivesTags).toString().replace(";", ",")
                fw.append(";")
                fw.append("" + recordList[i].product?.additivesOriginalTags).toString().replace(
                    ";",
                    ","
                )
                fw.append(";")
                fw.append("" + recordList[i].product?.allergensTags).toString().replace(";", ",")
                fw.append(";")
                fw.append("" + recordList[i].product?.allergensHierarchy).toString().replace(
                    ";",
                    ","
                )
                fw.append(";")
                fw.append("" + recordList[i].product?.traces).toString().replace(";", ",")
                fw.append(";")
                fw.append("" + recordList[i].product?.nutritionGrades).toString().replace(
                    ";",
                    ","
                )
                fw.append(";")
                fw.append("" + recordList[i].product?.nutritionDataPreparedPer).toString().replace(
                    ";",
                    ","
                )
                fw.append(";")
                fw.append("" + recordList[i].product?.nutritionDataPer).toString().replace(
                    ";",
                    ","
                )
                fw.append(";")
                fw.append("" + recordList[i].product?.nutrientLevelsTags).toString().replace(
                    ";",
                    ","
                )
                fw.append(";")
                fw.append("" + recordList[i].product?.imageUrl).toString().replace(";", ",")
                fw.append(";")
                fw.append("" + recordList[i].product?.imageSmallUrl).toString().replace(";", ",")
                fw.append(";")
                fw.append("" + recordList[i].product?.imageThumbUrl).toString().replace(";", ",")
                fw.append(";")
                fw.append("" + recordList[i].product?.imageIngredientsUrl).toString().replace(
                    ";",
                    ","
                )
                fw.append(";")
                fw.append("" + recordList[i].product?.imageIngredientsSmallUrl).toString().replace(
                    ";",
                    ","
                )
                fw.append(";")
                fw.append("" + recordList[i].product?.imageIngredientsThumbUrl).toString().replace(
                    ";",
                    ","
                )
                fw.append(";")
                fw.append("" + recordList[i].product?.imageNutritionUrl).toString().replace(
                    ";",
                    ","
                )
                fw.append(";")
                fw.append("" + recordList[i].product?.imageNutritionSmallUrl).toString().replace(
                    ";",
                    ","
                )
                fw.append(";")
                fw.append("" + recordList[i].product?.imageNutritionThumbUrl).toString().replace(
                    ";",
                    ","
                )
                fw.append(";")
                fw.append("" + recordList[i].product?.imageFrontUrl).toString().replace(";", ",")
                fw.append(";")
                fw.append("" + recordList[i].product?.imageFrontSmallUrl).toString().replace(
                    ";",
                    ","
                )
                fw.append(";")
                fw.append("" + recordList[i].product?.imageFrontThumbUrl).toString().replace(
                    ";",
                    ","
                )
                fw.append(";")
                fw.append("" + recordList[i].product?.novaGroup).toString().replace(";", ",")
                fw.append(";")
                fw.append("" + recordList[i].product?.novaGroups).toString().replace(";", ",")
                fw.append(";")
                fw.append("" + recordList[i].product?.nutriscoreScore).toString().replace(
                    ";",
                    ","
                )
                fw.append(";")
                fw.append("" + recordList[i].product?.nutriscoreGrade).toString().replace(
                    ";",
                    ","
                )
                fw.append(";")
                fw.append("" + recordList[i].product?.uniqueScansN).toString().replace(";", ",")
                fw.append(";")
                fw.append("" + recordList[i].product?.scansN).toString().replace(";", ",")
                fw.append(";")
                fw.append("" + recordList[i].product?.keywords).toString().replace(";", ",")
                fw.append("\n")
            }

        } catch (e: Exception){
            Toast.makeText(ctx, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun exportCSVUbicacions() {
        var recordList = ArrayList<Ubicacio>()
        recordList.clear()
        recordList = TaulaUbicacionsCrud(ctx).getAllUbicacions()
        try{
            for(i in recordList.indices){
                fw.append("" + Column.Companion.TUbicacions.T_UBICACIONS)
                fw.append(";")
                fw.append("" + recordList[i].id)
                fw.append(";")
                fw.append("" + recordList[i].nomubicacio)
                fw.append(";")
                fw.append("" + recordList[i].descubicacio)
                fw.append("\n")
            }

        } catch (e: Exception){
            Toast.makeText(ctx, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun exportCSVLlistes() {
        var recordList = ArrayList<Llistes>()
        recordList.clear()
        recordList = TaulaLlistesCrud(ctx).getAllLlista()
        try{
            for(i in recordList.indices){
                fw.append("" + Column.Companion.TLlistes.T_LLISTES)
                fw.append(";")
                fw.append("" + recordList[i].id)
                fw.append(";")
                fw.append("" + recordList[i].nomLlista)
                fw.append(";")
                fw.append("" + recordList[i].gestion_dataCad)
                fw.append(";")
                fw.append("" + recordList[i].gestiona_cantidad)
                fw.append(";")
                fw.append("" + recordList[i].gestiona_ubicaciones)
                fw.append("\n")
            }


        } catch (e: Exception){
            Toast.makeText(ctx, e.message, Toast.LENGTH_SHORT).show()
        }
    }
    
    fun createFolder(){
        folder = File(
            Environment.getExternalStorageDirectory().toString() + "/" + "com.ymest.rebost"
        )
        var isFolderCreated = false
        if(!folder.exists()) isFolderCreated = folder.mkdir()
    }

    private fun vaciaDirectorio() {
        if(folder.isDirectory){
            var hijos = folder.list()
            for (element in hijos) {
                File(folder, element).delete()
            }
        }
    }

}