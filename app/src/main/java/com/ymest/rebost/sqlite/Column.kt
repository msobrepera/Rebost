package com.ymest.rebost.sqlite

import android.provider.BaseColumns

class Column {
    companion object{
        const val VERSION = 4
        class TProductes: BaseColumns {
            companion object {
                //TAULA PRODUCTES
                const val T_PRODUCTES = "tProductes"

                //JSON PRODUCTO
                const val COL_ID = "id"
                const val COL_CODI = "codibarres"
                const val COL_STATUS = "status"
                const val COL_STATUS_VERBOSE = "status_verbose"

                //JSON PRODUCT
                const val COL_NAME_ES = "productNameEs"
                const val COL_NAME_EN = "productNameEn"
                const val COL_GENERIC_NAME = "genericName"
                const val COL_GENERIC_NAME_ES = "genericNameEs"
                const val COL_PRODUCT_NAME = "productName"
                const val COL_QUANTITY = "quantity"
                const val COL_PRODUCT_QUANTITY = "productQuantity"
                const val COL_SERVING_SIZE = "servingSize"
                const val COL_SERVING_QUANTITY = "servingQuantity"
                const val COL_BRANDS = "brands"
                const val COL_STORES = "stores"
                const val COL_STORES_TAGS = "storesTags"
                const val COL_MANUFACTURING_PLACES = "manufacturingPlaces"
                const val COL_CATEGORIES = "categories"
                const val COL_CATEGORIES_TAGS = "categoriesTags"
                const val COL_CATEGORIES_HIERARCHY = "categoriesHierarchy"
                const val COL_PNNS_GROUPS_1 = "pnnsGroups1"
                const val COL_PNNS_GROUPS_2 = "pnnsGroups2"
                const val COL_INGREDIENTS = "ingredients"
                const val COL_INGREDIENTS_TAGS = "ingredientsTags"
                const val COL_INGREDIENTS_TEXT = "ingredientsText"
                const val COL_INGREDIENTS_TEXT_ES = "ingredientsTextEs"
                const val COL_INGREDIENTS_TEXT_EN = "ingredientsTextEn"
                const val COL_INGREDIENTS_TEXT_WITH_ALLERGENS = "ingredientsTextWithAllergens"
                const val COL_INGREDIENTS_TEXT_WITH_ALLERGENS_ES =           "ingredientsTextWithAllergensEs"
                const val COL_INGREDIENTS_TEXT_WITH_ALLERGENS_EN =          "ingredientsTextWithAllergensEn"
                const val COL_INGREDIENTS_FROM_PALM_OIL_TAGS =              "ingredientsFromPalmOilTags"
                const val COL_INGREDIENTS_THAT_MAY_BE_FROM_PALM_OIL_TAGS =  "ingredientsThatMayBeFromPalmOilTags"
                const val COL_ADDITIVES_TAGS = "additivesTags"
                const val COL_ADDITIVES_ORIGINAL_TAGS = "additivesOriginalTags"
                const val COL_ALLERGENS_TAGS = "allergensTags"
                const val COL_ALLERGENS_HIERARCHY = "allergensHierarchy"
                const val COL_TRACES = "traces"
                const val COL_NUTRITION_GRADES = "nutritionGrades"
                const val COL_NUTRITION_DATA_PREPARED_PER = "nutritionDataPreparedPer"
                const val COL_NUTRITION_DATA_PER = "nutritionDataPer"
                const val COL_NUTRIENT_LEVELS_TAGS = "nutrientLevelsTags"
                const val COL_IMAGE_URL = "imageUrl"
                const val COL_IMAGE_SMALL_URL = "imageSmallUrl"
                const val COL_IMAGE_THUMB_URL = "imageThumbUrl"
                const val COL_IMAGE_INGREDIENTS_URL = "imageIngredientsUrl"
                const val COL_IMAGE_INGREDIENTS_SMALL_URL = "imageIngredientsSmallUrl"
                const val COL_IMAGE_INGREDIENTS_THUMB_URL = "imageIngredientsThumbUrl"
                const val COL_IMAGE_NUTRITION_URL = "imageNutritionUrl"
                const val COL_IMAGE_NUTRITION_SMALL_URL = "imageNutritionSmallUrl"
                const val COL_IMAGE_NUTRITION_THUMB_URL = "imageNutritionThumbUrl"
                const val COL_IMAGE_FRONT_URL = "imageFrontUrl"
                const val COL_IMAGE_FRONT_SMALL_URL = "imageFrontSmallUrl"
                const val COL_IMAGE_FRONT_THUMB_URL = "imageFrontThumbUrl"
                const val COL_NOVA_GROUP = "novaGroup"
                const val COL_NOVA_GROUPS = "novaGroups"
                const val COL_NUTRISCORE_SCORE = "nutriscoreScore"
                //const val COL_NUTRISCORE_DATA = "nutriscoreData"
                const val COL_NUTRISCORE_GRADE = "nutriscoreGrade"
                const val COL_UNIQUE_SCANS_N = "uniqueScansN"
                const val COL_SCANS_N = "scansN"
                const val COL_KEYWORDS = "keywords"
            }}
        class TNutrmients: BaseColumns {
            companion object {
                //JSON NUTRIMENTS
                const val T_NUTRIMENTS = "tNutriments"
                const val COL_NUTRIMENTS_CODE = "nutrimentsCode"
                const val COL_ENERGY = "energy"
                const val COL_ENERGY_100G = "energy100g"
                const val COL_ENERGY_SERVING = "energyServing"
                const val COL_ENERGY_VALUE = "energyValue"
                const val COL_ENERGY_UNIT = "energyUnit"

                const val COL_ENERGY_KCAL = "energyKcal"
                const val COL_ENERGY_KCAL_100G = "energyKcal100g"
                const val COL_ENERGY_KCAL_SERVING = "energyKcalServing"
                const val COL_ENERGY_KCAL_VALUE = "energyKcalValue"
                const val COL_ENERGY_KCAL_UNIT = "energyKcalUnit"

                const val COL_ENERGY_KJ = "energyKj"
                const val COL_ENERGY_KJ_100G = "energyKj100g"
                const val COL_ENERGY_KJ_SERVING = "energyKjServing"
                const val COL_ENERGY_KJ_VALUE = "energyKjValue"
                const val COL_ENERGY_KJ_UNIT = "energyKjUnit"

                const val COL_FAT = "fat"
                const val COL_FAT_100G = "fat100g"
                const val COL_FAT_SERVING = "fatServing"
                const val COL_FAT_VALUE = "fatValue"
                const val COL_FAT_UNIT = "fatUnit"

                const val COL_SATURED_FAT = "saturatedFat"
                const val COL_SATURED_FAT_100G = "saturatedFat100g"
                const val COL_SATURED_FAT_SERVING = "saturatedFatServing"
                const val COL_SATURED_FAT_VALUE = "saturatedFatValue"
                const val COL_SATURED_FAT_UNIT = "saturatedFatUnit"

                const val COL_CARBOHYDRATES = "carbohydrates"
                const val COL_CARBOHYDRATES_100G = "carbohydrates100g"
                const val COL_CARBOHYDRATES_SERVING = "carbohydratesServing"
                const val COL_CARBOHYDRATES_VALUE = "carbohydratesValue"
                const val COL_CARBOHYDRATES_UNIT = "carbohydratesUnit"

                const val COL_SUGARS = "sugars"
                const val COL_SUGARS_100G = "sugars100g"
                const val COL_SUGARS_SERVING = "sugarsServing"
                const val COL_SUGARS_VALUE = "sugarsValue"
                const val COL_SUGARS_UNIT = "sugarsUnit"

                const val COL_PROTEINS = "proteins"
                const val COL_PROTEINS_100G = "proteins100g"
                const val COL_PROTEINS_SERVING = "proteinsServing"
                const val COL_PROTEINS_VALUE = "proteinsValue"
                const val COL_PROTEINS_UNIT = "proteinsUnit"

                const val COL_SALT = "salt"
                const val COL_SALT_100G = "salt100g"
                const val COL_SALT_SERVING = "saltServing"
                const val COL_SALT_VALUE = "saltValue"
                const val COL_SALT_UNIT = "saltUnit"

                const val COL_SODIUM = "sodium"
                const val COL_SODIUM_100G = "sodium100g"
                const val COL_SODIUM_SERVING = "sodiumServing"
                const val COL_SODIUM_VALUE = "sodiumValue"
                const val COL_SODIUM_UNIT = "sodiumUnit"

                const val COL_FRUITS_VEGETABLES_NUTS_ESTIMATE_FROM_INGREDIENTS_100G =
                    "fruitsVegetablesNutsEstimateFromIngredients100g"

                const val COL_NOVA_GROUP_NUTRMIENTS = "novaGroupNutriments"
                const val COL_NOVA_GROUP_100G = "novaGroup100g"
                const val COL_NOVA_GROUP_SERVING = "novaGroupServing"
                const val COL_NUTRITION_SCORE_FR = "nutritionScoreFr"
                const val COL_NUTRITION_SCORE_FR_100G = "nutritionScoreFr100g"
            }}
        class TNutrientLevels: BaseColumns {
            companion object {
                // JSON NUTRIENTLEVELS
                const val T_NUTRIENTLEVELS = "tNutrientsLevels"
                const val COL_NUTRIENT_LEVELS_CODE = "nutrientLevelscode"
                const val COL_NUTRIENT_LEVELS_SUGARS = "nutrientLevelsSugars"
                const val COL_NUTRIENT_LEVELS_SALT = "nutrientLevelsSalt"
                const val COL_NUTRIENT_LEVELS_FAT = "nutrientLevelsFat"
                const val COL_NUTRIENT_LEVELS_SATURED_FAT = "nutrientLevelsSaturedFat"
            }}
        class TNutriscoreData: BaseColumns {
            companion object {

                // JSON NUTRISCOREDATA
                const val T_NUTRISCOREDATA = "tNutriscoreData"
                const val COL_NUTRISCOREDATA_CODE = "nutriscoreCode"
                const val COL_NUTRISCOREDATA_ENERGY = "nutriscoreDataEnergy"
                const val COL_NUTRISCOREDATA_ENERGY_POINTS = "nutriscoreDataEnergyPoints"
                const val COL_NUTRISCOREDATA_ENERGY_VALUE = "nutriscoreDataEnergyValue"

                const val COL_NUTRISCOREDATA_SATURED_FAT = "nutriscoreDataSaturedFat"
                const val COL_NUTRISCOREDATA_SATURED_FAT_POINTS = "nutriscoreDataSaturatedFatPoints"
                const val COL_NUTRISCOREDATA_SATURED_FAT_RATIO = "nutriscoreDataSaturatedFatRatio"
                const val COL_NUTRISCOREDATA_SATURED_FAT_VALUE = "nutriscoreDataSaturatedFatValue"
                const val COL_NUTRISCOREDATA_SATURED_FAT_RATIO_VALUE =
                    "nutriscoreDataSaturatedFatRatioValue"
                const val COL_NUTRISCOREDATA_SATURED_FAT_RATIO_POINTS =
                    "nutriscoreDataSaturatedFatRatioPoints"

                const val COL_NUTRISCOREDATA_FIBER = "nutriscoreDataFiber"
                const val COL_NUTRISCOREDATA_FIBER_POINTS = "nutriscoreDataFiberPoints"
                const val COL_NUTRISCOREDATA_FIBER_VALUE = "nutriscoredataFiberValue"

                const val COL_NUTRISCOREDATA_PROTEINS = "nutriscoreDataProteins"
                const val COL_NUTRISCOREDATA_PROTEINS_POINTS = "nutriscoreDataProteinsPoints"
                const val COL_NUTRISCOREDATA_PROTEINS_VALUE = "nutriscoreDataProteinsValue"

                const val COL_NUTRISCOREDATA_SUGARS = "nutriscoreDataSugars"
                const val COL_NUTRISCOREDATA_SUGARS_POINTS = "nutriscoreDataSugarsPoints"
                const val COL_NUTRISCOREDATA_SUGARS_VALUE = "nutriscoreDataSugarsValue"

                const val COL_NUTRISCOREDATA_SODIUM = "nutriscoreDataSodium"
                const val COL_NUTRISCOREDATA_SODIUM_POINTS = "nutriscoreDataSodiumPoints"
                const val COL_NUTRISCOREDATA_SODIUM_VALUE = "nutriscoredataSodiumValue"

                const val COL_NUTRISCOREDATA_IS_CHEESE = "nutriscoredataIsCheese"
                const val COL_NUTRISCOREDATA_IS_WATER = "nutriscoredataIsWater"
                const val COL_NUTRISCOREDATA_IS_FAT = "nutriscoredataIsFat"
                const val COL_NUTRISCOREDATA_IS_BEVERAGE = "nutriscoredataIsBeverage"

                const val COL_NUTRISCOREDATA_FRUITS_VEGETABLES_NUTS_COLZA_WALNUT_OLIVE_OILS =
                    "nutriscoredatafruitsVegetablesNutsColzaWalnutOliveOils"
                const val COL_NUTRISCOREDATA_FRUITS_VEGETABLES_NUTS_COLZA_WALNUT_OLIVE_OILS_POINTS =
                    "nutriscoredatafruitsVegetablesNutsColzaWalnutOliveOilsPoints"
                const val COL_NUTRISCOREDATA_FRUITS_VEGETABLES_NUTS_COLZA_WALNUT_OLIVE_OILS_VALUE =
                    "nutriscoredatafruitsVegetablesNutsColzaWalnutOliveOilsValue"

                const val COL_NUTRISCOREDATA_POSITIVE_POINTS = "nutriscoredataPositivePoints"
                const val COL_NUTRISCOREDATA_NEGATIVE_POINTS = "nutriscoreDataNegativePoints"
                const val COL_NUTRISCOREDATA_SCORE = "nutriscoredataScore"
                const val COL_NUTRISCOREDATA_GRADE = "nutriscoreDataGrade"
            }}
        class TIngredientsItem: BaseColumns {
            companion object {
                // JSON INGREDIENTSITEM
                const val T_INGREDIENTSITEM = "tIngredientsItem"
                const val COL_INGREDIENTSITEM_CODE = "ingredientsitemCode"
                const val COL_INGREDIENTSITEM_PERCENT_MAX = "ingredientsitemPercentMax"
                const val COL_INGREDIENTSITEM_PERCENT_MIN = "ingredientsitemPercentMin"
                const val COL_INGREDIENTSITEM_VEGETARIAN = "ingredientsitemVegetarian"
                const val COL_INGREDIENTSITEM_ID = "ingredientsitemId"
                const val COL_INGREDIENTSITEM_VEGAN = "ingredientsitemVegan"
                const val COL_INGREDIENTSITEM_TEXT = "ingredientsitemText"
                const val COL_INGREDIENTSITEM_RANK = "ingredientsitemRank"
                const val COL_INGREDIENTSITEM_HAS_SUB_INGREDIENTS =
                    "ingredientsitemHasSubIngredients"
            }}
        class TPersonalitzada: BaseColumns {
            companion object {
                //TAULA PERSONALITZATS
                const val T_PERSONALITZADA = "tPersonalitzada"
                const val COL_PERSONALITZADA_CODE = "personalitzadaCode"
                const val COL_DATA_AFEGIT = "dataAfegit"
                const val COL_DATA_VIST = "dataVist"
                const val COL_FAVORIT = "favorit"
                const val COL_GUST = "gust"
                const val COL_PUNTUACIO = "puntuacio"
            }}

        class TLlistes: BaseColumns{
            companion object{
                //TAULA LLISTES
                const val T_LLISTES = "tllistes"
                const val COL_LLISTES_ID = "llistesId"
                const val COL_LLISTES_NOM = "llistesNom"
                const val COL_DATA_CAD = "llistesDataCaducitat"
                const val COL_GESTIONA_CANTIDAD = "llistesGestionaCantidad"
                const val COL_GESTIONA_UBICACIONES = "llistesGestionaUbicacions"
            }
        }
        class TProductesALlista: BaseColumns{
            companion object{
                const val T_PRODUCTESALLISTA = "tproductesALlistes"
                const val COL_PRODUCTESALLISTA_ID = "productesALlistesId"
                const val COL_PRODUCTESALLISTA_CODI_BARRES = "productesALlistesCodiBarres"
                const val COL_PRODUCTESALLISTA_ID_LLISTA = "productesALlistesIdLlista"
                const val COL_PRODUCTESALLISTA_QUANTITAT = "productesALlistesQuantitat"
                const val COL_PRODUCTESALLISTA_DATA_CAD = "productesALlistesDataCad"
                const val COL_PRODUCTESALLISTA_ID_UBICACIO = "productesALlistesIdUbicacio"
            }
        }
        class TDataCad: BaseColumns{
            companion object{
                //TAULA DATA CADUCITAT
                const val T_DATACADUCITAT = "tdatacaducitat"
                const val COL_DATACADUCITAT_ID = "datacaducitatId"
                const val COL_DATACADUCITAT_LLISTAID = "datacaducitatIdLlista"
                const val COL_DATACADUCITAT_CODIBARRES = "datacaducitatCodiBarres"
                const val COL_DATACADUCITAT_DATA = "datacaducitatData"
                const val COL_DATACADUCITAT_QUANTITAT = "datacaducitatQuantitat"
            }
        }
        class TUbicacions: BaseColumns{
            companion object {
                //TAULA UBICACIONS
                const val T_UBICACIONS = "tubicacions"
                const val COL_ID_UBICACIONS = "id"
                const val COL_NOM_UBICACIONS = "nomubicacio"
                const val COL_DESCUBICACIONS = "descubicacio"
            }
        }
        class TAltresTaules: BaseColumns {
            companion object {
                //TAULA REBOST
                const val NOM_TAULA_REBOST = "rebost"
                const val COL_ID_REBOST = "id"
                const val COL_CODI_BARRES_REBOST = "codibarres"
                const val COL_DATA_CADUCITAT_REBOST = "datacaducitat"
                const val COL_QUANTITAT_REBOST = "quantitat"
                const val COL_CONSUMIT_REBOST = "consumit"
                const val COL_ID_UBICACIO_REBOST = "idubicacio"
                const val COL_DATA_ALTA_REBOST = "dataalta"

                //TAULA COMPRAR
                const val NOM_TAULA_COMPRAR = "comprar"
                const val COL_ID_COMPRAR = "id"
                const val COL_CODI_BARRES_COMPRAR = "codibarres"
                const val COL_QUANTITAT_COMPRAR = "quantitat"
                const val COL_DATA_ALTA_COMPRAR = "dataalta"
                const val COL_COMPRAT_COMPRAR = "comprat"




                //TAULA DATES CADUCITAT
                const val NOM_TAULA_DATES_CADUCITAT = "datescaducitat"
                const val COL_ID_DATES_CADUCITAT = "id"
                const val COL_CODI_BARRES_DATES_CADUCITAT = "codibarres"
                const val COL_QUANTITAT_DATES_CADUCITAT = "quantitat"
                const val COL_DATA_DATES_CADUCITAT = "data"
            }
        }
    }
}