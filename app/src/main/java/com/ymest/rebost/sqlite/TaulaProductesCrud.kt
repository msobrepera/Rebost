package com.ymest.rebost.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.ymest.rebost.json.Product
import com.ymest.rebost.json.Producto
import com.ymest.rebost.sqlite.old.ProductesContract

class TaulaProductesCrud(context: Context) {
    private var helper: DataBaseHelper

    init {
        helper = DataBaseHelper(context)
    }

    fun addProducte(item: Producto) {
        val db = helper.writableDatabase

        val values = ContentValues()
        values.put(Column.Companion.TProductes.COL_CODI, item.code)
        values.put(Column.Companion.TProductes.COL_STATUS, item.status)
        values.put(Column.Companion.TProductes.COL_STATUS_VERBOSE, item.statusVerbose)
        values.put(Column.Companion.TProductes.COL_NAME_ES, item.product?.productNameEs)
        values.put(Column.Companion.TProductes.COL_NAME_EN, item.product?.productNameEn)
        values.put(Column.Companion.TProductes.COL_GENERIC_NAME, item.product?.genericName)
        values.put(Column.Companion.TProductes.COL_GENERIC_NAME_ES, item.product?.genericNameEs)
        values.put(Column.Companion.TProductes.COL_PRODUCT_NAME, item.product?.productName)
        values.put(Column.Companion.TProductes.COL_QUANTITY, item.product?.quantity)
        values.put(Column.Companion.TProductes.COL_PRODUCT_QUANTITY, item.product?.productQuantity)
        values.put(Column.Companion.TProductes.COL_SERVING_SIZE, item.product?.servingSize)
        values.put(Column.Companion.TProductes.COL_SERVING_QUANTITY, item.product?.servingQuantity)
        values.put(Column.Companion.TProductes.COL_BRANDS, item.product?.brands)
        values.put(Column.Companion.TProductes.COL_STORES, item.product?.stores)
        values.put(Column.Companion.TProductes.COL_STORES_TAGS, item.product?.storesTags.toString())
        values.put(
            Column.Companion.TProductes.COL_MANUFACTURING_PLACES,
            item.product?.manufacturingPlaces
        )
        values.put(Column.Companion.TProductes.COL_CATEGORIES, item.product?.categories)
        values.put(
            Column.Companion.TProductes.COL_CATEGORIES_TAGS,
            item.product?.categoriesTags.toString()
        )
        values.put(
            Column.Companion.TProductes.COL_CATEGORIES_HIERARCHY,
            item.product?.categoriesHierarchy.toString()
        )
        values.put(Column.Companion.TProductes.COL_PNNS_GROUPS_1, item.product?.pnnsGroups1)
        values.put(Column.Companion.TProductes.COL_PNNS_GROUPS_2, item.product?.pnnsGroups2)
        values.put(
            Column.Companion.TProductes.COL_INGREDIENTS,
            item.product?.ingredients.toString()
        )
        values.put(
            Column.Companion.TProductes.COL_INGREDIENTS_TAGS,
            item.product?.ingredientsTags.toString()
        )
        values.put(Column.Companion.TProductes.COL_INGREDIENTS_TEXT, item.product?.ingredientsText)
        values.put(
            Column.Companion.TProductes.COL_INGREDIENTS_TEXT_ES,
            item.product?.ingredientsTextEs
        )
        values.put(
            Column.Companion.TProductes.COL_INGREDIENTS_TEXT_EN,
            item.product?.ingredientsTextEn
        )
        values.put(
            Column.Companion.TProductes.COL_INGREDIENTS_TEXT_WITH_ALLERGENS,
            item.product?.ingredientsTextWithAllergens
        )
        values.put(
            Column.Companion.TProductes.COL_INGREDIENTS_TEXT_WITH_ALLERGENS_ES,
            item.product?.ingredientsTextWithAllergensEs
        )
        values.put(
            Column.Companion.TProductes.COL_INGREDIENTS_TEXT_WITH_ALLERGENS_EN,
            item.product?.ingredientsTextWithAllergensEn
        )
        values.put(
            Column.Companion.TProductes.COL_INGREDIENTS_FROM_PALM_OIL_TAGS,
            item.product?.ingredientsFromPalmOilTags.toString()
        )
        values.put(
            Column.Companion.TProductes.COL_ADDITIVES_TAGS,
            item.product?.additivesTags.toString()
        )
        values.put(
            Column.Companion.TProductes.COL_ADDITIVES_ORIGINAL_TAGS,
            item.product?.additivesOriginalTags.toString()
        )
        values.put(
            Column.Companion.TProductes.COL_ALLERGENS_TAGS,
            item.product?.allergensTags.toString()
        )
        values.put(
            Column.Companion.TProductes.COL_ALLERGENS_HIERARCHY,
            item.product?.allergensHierarchy.toString()
        )
        values.put(Column.Companion.TProductes.COL_TRACES, item.product?.traces)
        values.put(Column.Companion.TProductes.COL_NUTRITION_GRADES, item.product?.nutritionGrades)
        values.put(
            Column.Companion.TProductes.COL_NUTRITION_DATA_PREPARED_PER,
            item.product?.nutritionDataPreparedPer
        )
        values.put(
            Column.Companion.TProductes.COL_NUTRITION_DATA_PER,
            item.product?.nutritionDataPer
        )
        values.put(
            Column.Companion.TProductes.COL_NUTRIENT_LEVELS_TAGS,
            item.product?.nutrientLevelsTags.toString()
        )
        values.put(Column.Companion.TProductes.COL_IMAGE_URL, item.product?.imageUrl)
        values.put(Column.Companion.TProductes.COL_IMAGE_SMALL_URL, item.product?.imageSmallUrl)
        values.put(Column.Companion.TProductes.COL_IMAGE_THUMB_URL, item.product?.imageThumbUrl)
        values.put(
            Column.Companion.TProductes.COL_IMAGE_INGREDIENTS_URL,
            item.product?.imageIngredientsUrl
        )
        values.put(
            Column.Companion.TProductes.COL_IMAGE_INGREDIENTS_SMALL_URL,
            item.product?.imageIngredientsSmallUrl
        )
        values.put(
            Column.Companion.TProductes.COL_IMAGE_INGREDIENTS_THUMB_URL,
            item.product?.imageIngredientsThumbUrl
        )
        values.put(
            Column.Companion.TProductes.COL_IMAGE_NUTRITION_URL,
            item.product?.imageNutritionUrl
        )
        values.put(
            Column.Companion.TProductes.COL_IMAGE_NUTRITION_SMALL_URL,
            item.product?.imageNutritionSmallUrl
        )
        values.put(
            Column.Companion.TProductes.COL_IMAGE_NUTRITION_THUMB_URL,
            item.product?.imageNutritionThumbUrl
        )
        values.put(Column.Companion.TProductes.COL_IMAGE_FRONT_URL, item.product?.imageFrontUrl)
        values.put(
            Column.Companion.TProductes.COL_IMAGE_FRONT_SMALL_URL,
            item.product?.imageFrontSmallUrl
        )
        values.put(
            Column.Companion.TProductes.COL_IMAGE_FRONT_THUMB_URL,
            item.product?.imageFrontThumbUrl
        )
        values.put(Column.Companion.TProductes.COL_NOVA_GROUP, item.product?.novaGroup)
        values.put(Column.Companion.TProductes.COL_NOVA_GROUPS, item.product?.novaGroups)
        values.put(Column.Companion.TProductes.COL_NUTRISCORE_SCORE, item.product?.nutriscoreScore)
        values.put(Column.Companion.TProductes.COL_NUTRISCORE_GRADE, item.product?.nutriscoreGrade)
        values.put(Column.Companion.TProductes.COL_UNIQUE_SCANS_N, item.product?.uniqueScansN)
        values.put(Column.Companion.TProductes.COL_SCANS_N, item.product?.scansN)
        values.put(Column.Companion.TProductes.COL_KEYWORDS, item.product?.keywords.toString())

        val newRowId = db.insert(Column.Companion.TProductes.T_PRODUCTES, null, values)
        db.close()
    }

    fun getProductes(): ArrayList<Producto> {
        val items: ArrayList<Producto> = ArrayList()
        //Abrir base de datos para leer
        val db = helper.readableDatabase
        val columnas = arrayOf(
            Column.Companion.TProductes.COL_ID,
            Column.Companion.TProductes.COL_CODI,
            Column.Companion.TProductes.COL_STATUS,
            Column.Companion.TProductes.COL_STATUS_VERBOSE,
            Column.Companion.TProductes.COL_NAME_ES,
            Column.Companion.TProductes.COL_NAME_EN,
            Column.Companion.TProductes.COL_GENERIC_NAME,
            Column.Companion.TProductes.COL_GENERIC_NAME_ES,
            Column.Companion.TProductes.COL_PRODUCT_NAME,
            Column.Companion.TProductes.COL_QUANTITY,
            Column.Companion.TProductes.COL_PRODUCT_QUANTITY,
            Column.Companion.TProductes.COL_SERVING_SIZE,
            Column.Companion.TProductes.COL_SERVING_QUANTITY,
            Column.Companion.TProductes.COL_BRANDS,
            Column.Companion.TProductes.COL_STORES,
            Column.Companion.TProductes.COL_STORES_TAGS,
            Column.Companion.TProductes.COL_MANUFACTURING_PLACES,
            Column.Companion.TProductes.COL_CATEGORIES,
            Column.Companion.TProductes.COL_CATEGORIES_TAGS,
            Column.Companion.TProductes.COL_CATEGORIES_HIERARCHY,
            Column.Companion.TProductes.COL_PNNS_GROUPS_1,
            Column.Companion.TProductes.COL_PNNS_GROUPS_2,
            Column.Companion.TProductes.COL_INGREDIENTS,
            Column.Companion.TProductes.COL_INGREDIENTS_TAGS,
            Column.Companion.TProductes.COL_INGREDIENTS_TEXT,
            Column.Companion.TProductes.COL_INGREDIENTS_TEXT_ES,
            Column.Companion.TProductes.COL_INGREDIENTS_TEXT_EN,
            Column.Companion.TProductes.COL_INGREDIENTS_TEXT_WITH_ALLERGENS,
            Column.Companion.TProductes.COL_INGREDIENTS_TEXT_WITH_ALLERGENS_ES,
            Column.Companion.TProductes.COL_INGREDIENTS_TEXT_WITH_ALLERGENS_EN,
            Column.Companion.TProductes.COL_INGREDIENTS_FROM_PALM_OIL_TAGS,
            Column.Companion.TProductes.COL_INGREDIENTS_THAT_MAY_BE_FROM_PALM_OIL_TAGS,
            Column.Companion.TProductes.COL_ADDITIVES_TAGS,
            Column.Companion.TProductes.COL_ADDITIVES_ORIGINAL_TAGS,
            Column.Companion.TProductes.COL_ALLERGENS_TAGS,
            Column.Companion.TProductes.COL_ALLERGENS_HIERARCHY,
            Column.Companion.TProductes.COL_TRACES,
            Column.Companion.TProductes.COL_NUTRITION_GRADES,
            Column.Companion.TProductes.COL_NUTRITION_DATA_PREPARED_PER,
            Column.Companion.TProductes.COL_NUTRITION_DATA_PER,
            Column.Companion.TProductes.COL_NUTRIENT_LEVELS_TAGS,
            Column.Companion.TProductes.COL_IMAGE_URL,
            Column.Companion.TProductes.COL_IMAGE_SMALL_URL,
            Column.Companion.TProductes.COL_IMAGE_THUMB_URL,
            Column.Companion.TProductes.COL_IMAGE_INGREDIENTS_URL,
            Column.Companion.TProductes.COL_IMAGE_INGREDIENTS_SMALL_URL,
            Column.Companion.TProductes.COL_IMAGE_INGREDIENTS_THUMB_URL,
            Column.Companion.TProductes.COL_IMAGE_NUTRITION_URL,
            Column.Companion.TProductes.COL_IMAGE_NUTRITION_SMALL_URL,
            Column.Companion.TProductes.COL_IMAGE_NUTRITION_THUMB_URL,
            Column.Companion.TProductes.COL_IMAGE_FRONT_URL,
            Column.Companion.TProductes.COL_IMAGE_FRONT_SMALL_URL,
            Column.Companion.TProductes.COL_IMAGE_FRONT_THUMB_URL,
            Column.Companion.TProductes.COL_NOVA_GROUP,
            Column.Companion.TProductes.COL_NOVA_GROUPS,
            Column.Companion.TProductes.COL_NUTRISCORE_SCORE,
            Column.Companion.TProductes.COL_NUTRISCORE_GRADE,
            Column.Companion.TProductes.COL_UNIQUE_SCANS_N,
            Column.Companion.TProductes.COL_SCANS_N,
            Column.Companion.TProductes.COL_KEYWORDS
        )

        val c: Cursor = db.query(
            Column.Companion.TProductes.T_PRODUCTES,
            columnas,
            null,
            null,
            null,
            null,
            null
        )

        while (c.moveToNext()) {
            items.add(
                Producto(
                    Product(
                        listOf(),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_NAME_ES)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_IMAGE_FRONT_THUMB_URL)),
                        listOf(c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_INGREDIENTS_TAGS))),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_IMAGE_NUTRITION_URL)),
                        null,
                        c.getInt(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_NOVA_GROUP)),
                        c.getInt(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_NUTRISCORE_SCORE)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_SERVING_SIZE)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_CATEGORIES)),
                        c.getInt(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_UNIQUE_SCANS_N)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_PRODUCT_NAME)),
                        listOf(c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_KEYWORDS))),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_ID)),
                        null,
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_IMAGE_SMALL_URL)),
                        listOf(c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_ADDITIVES_TAGS))),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_IMAGE_INGREDIENTS_THUMB_URL)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_IMAGE_URL)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_GENERIC_NAME)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_IMAGE_INGREDIENTS_SMALL_URL)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_TRACES)),
                        listOf(c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_INGREDIENTS_FROM_PALM_OIL_TAGS))),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_PRODUCT_NAME)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_GENERIC_NAME_ES)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_IMAGE_FRONT_SMALL_URL)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_IMAGE_NUTRITION_SMALL_URL)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_INGREDIENTS_TEXT_ES)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_INGREDIENTS_TEXT_WITH_ALLERGENS_EN)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_INGREDIENTS_TEXT_EN)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_CODI)),
                        listOf(c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_ALLERGENS_TAGS))),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_PRODUCT_QUANTITY)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_IMAGE_INGREDIENTS_URL)),
                        listOf(c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_CATEGORIES_TAGS))),
                        listOf(c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_ADDITIVES_ORIGINAL_TAGS))),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_NUTRITION_DATA_PREPARED_PER)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_NUTRITION_DATA_PER)),
                        null,
                        listOf(c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_NUTRIENT_LEVELS_TAGS))),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_IMAGE_FRONT_URL)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_NOVA_GROUPS)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_SERVING_QUANTITY)),
                        c.getInt(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_SCANS_N)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_IMAGE_NUTRITION_THUMB_URL)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_BRANDS)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_QUANTITY)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_STORES)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_INGREDIENTS_TEXT_WITH_ALLERGENS)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_MANUFACTURING_PLACES)),
                        listOf(c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_CATEGORIES_HIERARCHY))),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_IMAGE_THUMB_URL)),
                        listOf(c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_INGREDIENTS_THAT_MAY_BE_FROM_PALM_OIL_TAGS))),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_INGREDIENTS_TEXT)),
                        null,
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_NUTRISCORE_GRADE)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_INGREDIENTS_TEXT_WITH_ALLERGENS_ES)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_PNNS_GROUPS_1)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_NUTRITION_GRADES)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_PNNS_GROUPS_2)),
                        listOf(c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_STORES_TAGS))),
                        listOf(c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_ALLERGENS_HIERARCHY)))
                    ),
                    c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_CODI)),
                    c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_STATUS_VERBOSE)),
                    c.getInt(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_STATUS))
                )
            )
        }
        db.close()
        return items
    }

    fun getProducte(cb: String): ArrayList<Producto> {
        val items: ArrayList<Producto> = ArrayList()
        //Abrir base de datos para leer
        val db = helper.readableDatabase
        val columnas = arrayOf(
            Column.Companion.TProductes.COL_ID,
            Column.Companion.TProductes.COL_CODI,
            Column.Companion.TProductes.COL_STATUS,
            Column.Companion.TProductes.COL_STATUS_VERBOSE,
            Column.Companion.TProductes.COL_NAME_ES,
            Column.Companion.TProductes.COL_NAME_EN,
            Column.Companion.TProductes.COL_GENERIC_NAME,
            Column.Companion.TProductes.COL_GENERIC_NAME_ES,
            Column.Companion.TProductes.COL_PRODUCT_NAME,
            Column.Companion.TProductes.COL_QUANTITY,
            Column.Companion.TProductes.COL_PRODUCT_QUANTITY,
            Column.Companion.TProductes.COL_SERVING_SIZE,
            Column.Companion.TProductes.COL_SERVING_QUANTITY,
            Column.Companion.TProductes.COL_BRANDS,
            Column.Companion.TProductes.COL_STORES,
            Column.Companion.TProductes.COL_STORES_TAGS,
            Column.Companion.TProductes.COL_MANUFACTURING_PLACES,
            Column.Companion.TProductes.COL_CATEGORIES,
            Column.Companion.TProductes.COL_CATEGORIES_TAGS,
            Column.Companion.TProductes.COL_CATEGORIES_HIERARCHY,
            Column.Companion.TProductes.COL_PNNS_GROUPS_1,
            Column.Companion.TProductes.COL_PNNS_GROUPS_2,
            Column.Companion.TProductes.COL_INGREDIENTS,
            Column.Companion.TProductes.COL_INGREDIENTS_TAGS,
            Column.Companion.TProductes.COL_INGREDIENTS_TEXT,
            Column.Companion.TProductes.COL_INGREDIENTS_TEXT_ES,
            Column.Companion.TProductes.COL_INGREDIENTS_TEXT_EN,
            Column.Companion.TProductes.COL_INGREDIENTS_TEXT_WITH_ALLERGENS,
            Column.Companion.TProductes.COL_INGREDIENTS_TEXT_WITH_ALLERGENS_ES,
            Column.Companion.TProductes.COL_INGREDIENTS_TEXT_WITH_ALLERGENS_EN,
            Column.Companion.TProductes.COL_INGREDIENTS_FROM_PALM_OIL_TAGS,
            Column.Companion.TProductes.COL_INGREDIENTS_THAT_MAY_BE_FROM_PALM_OIL_TAGS,
            Column.Companion.TProductes.COL_ADDITIVES_TAGS,
            Column.Companion.TProductes.COL_ADDITIVES_ORIGINAL_TAGS,
            Column.Companion.TProductes.COL_ALLERGENS_TAGS,
            Column.Companion.TProductes.COL_ALLERGENS_HIERARCHY,
            Column.Companion.TProductes.COL_TRACES,
            Column.Companion.TProductes.COL_NUTRITION_GRADES,
            Column.Companion.TProductes.COL_NUTRITION_DATA_PREPARED_PER,
            Column.Companion.TProductes.COL_NUTRITION_DATA_PER,
            Column.Companion.TProductes.COL_NUTRIENT_LEVELS_TAGS,
            Column.Companion.TProductes.COL_IMAGE_URL,
            Column.Companion.TProductes.COL_IMAGE_SMALL_URL,
            Column.Companion.TProductes.COL_IMAGE_THUMB_URL,
            Column.Companion.TProductes.COL_IMAGE_INGREDIENTS_URL,
            Column.Companion.TProductes.COL_IMAGE_INGREDIENTS_SMALL_URL,
            Column.Companion.TProductes.COL_IMAGE_INGREDIENTS_THUMB_URL,
            Column.Companion.TProductes.COL_IMAGE_NUTRITION_URL,
            Column.Companion.TProductes.COL_IMAGE_NUTRITION_SMALL_URL,
            Column.Companion.TProductes.COL_IMAGE_NUTRITION_THUMB_URL,
            Column.Companion.TProductes.COL_IMAGE_FRONT_URL,
            Column.Companion.TProductes.COL_IMAGE_FRONT_SMALL_URL,
            Column.Companion.TProductes.COL_IMAGE_FRONT_THUMB_URL,
            Column.Companion.TProductes.COL_NOVA_GROUP,
            Column.Companion.TProductes.COL_NOVA_GROUPS,
            Column.Companion.TProductes.COL_NUTRISCORE_SCORE,
            Column.Companion.TProductes.COL_NUTRISCORE_GRADE,
            Column.Companion.TProductes.COL_UNIQUE_SCANS_N,
            Column.Companion.TProductes.COL_SCANS_N,
            Column.Companion.TProductes.COL_KEYWORDS
        )
        val where = Column.Companion.TProductes.COL_CODI + " = ? "
        val args = arrayOf(cb)

        val c: Cursor = db.query(
            Column.Companion.TProductes.T_PRODUCTES,
            columnas,
            where,
            args,
            null,
            null,
            null
        )

        while (c.moveToNext()) {
            items.add(
                Producto(
                    Product(
                        listOf(),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_NAME_ES)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_IMAGE_FRONT_THUMB_URL)),
                        listOf(c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_INGREDIENTS_TAGS))),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_IMAGE_NUTRITION_URL)),
                        null,
                        c.getInt(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_NOVA_GROUP)),
                        c.getInt(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_NUTRISCORE_SCORE)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_SERVING_SIZE)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_CATEGORIES)),
                        c.getInt(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_UNIQUE_SCANS_N)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_PRODUCT_NAME)),
                        listOf(c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_KEYWORDS))),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_ID)),
                        null,
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_IMAGE_SMALL_URL)),
                        listOf(c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_ADDITIVES_TAGS))),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_IMAGE_INGREDIENTS_THUMB_URL)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_IMAGE_URL)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_GENERIC_NAME)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_IMAGE_INGREDIENTS_SMALL_URL)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_TRACES)),
                        listOf(c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_INGREDIENTS_FROM_PALM_OIL_TAGS))),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_PRODUCT_NAME)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_GENERIC_NAME_ES)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_IMAGE_FRONT_SMALL_URL)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_IMAGE_NUTRITION_SMALL_URL)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_INGREDIENTS_TEXT_ES)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_INGREDIENTS_TEXT_WITH_ALLERGENS_EN)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_INGREDIENTS_TEXT_EN)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_CODI)),
                        listOf(c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_ALLERGENS_TAGS))),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_PRODUCT_QUANTITY)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_IMAGE_INGREDIENTS_URL)),
                        listOf(c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_CATEGORIES_TAGS))),
                        listOf(c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_ADDITIVES_ORIGINAL_TAGS))),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_NUTRITION_DATA_PREPARED_PER)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_NUTRITION_DATA_PER)),
                        null,
                        listOf(c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_NUTRIENT_LEVELS_TAGS))),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_IMAGE_FRONT_URL)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_NOVA_GROUPS)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_SERVING_QUANTITY)),
                        c.getInt(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_SCANS_N)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_IMAGE_NUTRITION_THUMB_URL)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_BRANDS)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_QUANTITY)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_STORES)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_INGREDIENTS_TEXT_WITH_ALLERGENS)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_MANUFACTURING_PLACES)),
                        listOf(c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_CATEGORIES_HIERARCHY))),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_IMAGE_THUMB_URL)),
                        listOf(c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_INGREDIENTS_THAT_MAY_BE_FROM_PALM_OIL_TAGS))),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_INGREDIENTS_TEXT)),
                        null,
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_NUTRISCORE_GRADE)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_INGREDIENTS_TEXT_WITH_ALLERGENS_ES)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_PNNS_GROUPS_1)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_NUTRITION_GRADES)),
                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_PNNS_GROUPS_2)),
                        listOf(c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_STORES_TAGS))),
                        listOf(c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_ALLERGENS_HIERARCHY)))
                    ),
                    c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_CODI)),
                    c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_STATUS_VERBOSE)),
                    c.getInt(c.getColumnIndexOrThrow(Column.Companion.TProductes.COL_STATUS))
                )
            )
        }
        db.close()
        return items
    }

    fun existeProducto(codibarres: String):Boolean{
        val items:ArrayList<Producto> = ArrayList()
        var existe: Boolean = false
        //Abrir base de datos para leer
        val db = helper.readableDatabase
        val columnas = arrayOf(Column.Companion.TProductes.COL_CODI)
        val seleccion: String = Column.Companion.TProductes.COL_CODI + "= ?"

        val c:Cursor = db.query(Column.Companion.TProductes.T_PRODUCTES, columnas, seleccion, arrayOf(codibarres), null, null, null)
        existe = c.moveToFirst()
        db.close()
        return existe
    }

    fun deleteProducte(codibarres: String){
        var db = helper.writableDatabase
        var where = Column.Companion.TProductes.COL_CODI + "=?"
        db.delete(Column.Companion.TProductes.T_PRODUCTES, where, arrayOf(codibarres))
        db.close()
    }
}