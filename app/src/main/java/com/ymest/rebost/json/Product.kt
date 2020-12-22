package com.ymest.rebost.json

import com.google.gson.annotations.SerializedName

data class Product(
    /*var brands: String,
    val brands_tags: List<String>,
    val countries: String,
    var product_name: String,
    val id: String,
    var image_url: String,
    val labels: String,
    var product_name_es: String,
    var stores: String,
    var stores_tags: List<String>,
    var quantity: String,*/
    @field:SerializedName("emb_codes_tags")
    val embCodesTags: List<Any?>? = null,

    @field:SerializedName("product_name_es")
    val productNameEs: String? = null,

    @field:SerializedName("image_front_thumb_url")
    val imageFrontThumbUrl: String? = null,

    @field:SerializedName("ingredients_tags")
    val ingredientsTags: List<String?>? = null,

    @field:SerializedName("image_nutrition_url")
    val imageNutritionUrl: String? = null,

    @field:SerializedName("nutriments")
    val nutriments: Nutriments? = null,

    @field:SerializedName("nova_group")
    val novaGroup: Int? = null,

    @field:SerializedName("nutriscore_score")
    val nutriscoreScore: Int? = null,

    @field:SerializedName("serving_size")
    val servingSize: String? = null,

    @field:SerializedName("categories")
    val categories: String? = null,

    @field:SerializedName("unique_scans_n")
    val uniqueScansN: Int? = null,

    @field:SerializedName("product_name_en")
    val productNameEn: String? = null,

    @field:SerializedName("_keywords")
    val keywords: List<String?>? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("nutrient_levels")
    val nutrientLevels: NutrientLevels? = null,

    @field:SerializedName("image_small_url")
    val imageSmallUrl: String? = null,

    @field:SerializedName("additives_tags")
    val additivesTags: List<String?>? = null,

    @field:SerializedName("image_ingredients_thumb_url")
    val imageIngredientsThumbUrl: String? = null,

    @field:SerializedName("image_url")
    val imageUrl: String? = null,

    @field:SerializedName("generic_name")
    val genericName: String? = null,

    @field:SerializedName("image_ingredients_small_url")
    val imageIngredientsSmallUrl: String? = null,

    @field:SerializedName("traces")
    val traces: String? = null,

    @field:SerializedName("ingredients_from_palm_oil_tags")
    val ingredientsFromPalmOilTags: List<Any?>? = null,

    @field:SerializedName("product_name")
    val productName: String? = null,

    @field:SerializedName("generic_name_es")
    val genericNameEs: String? = null,

    @field:SerializedName("image_front_small_url")
    val imageFrontSmallUrl: String? = null,

    @field:SerializedName("image_nutrition_small_url")
    val imageNutritionSmallUrl: String? = null,

    @field:SerializedName("ingredients_text_es")
    val ingredientsTextEs: String? = null,

    @field:SerializedName("ingredients_text_with_allergens_en")
    val ingredientsTextWithAllergensEn: String? = null,

    @field:SerializedName("ingredients_text_en")
    val ingredientsTextEn: String? = null,

    @field:SerializedName("code")
    val code: String? = null,

    @field:SerializedName("allergens_tags")
    val allergensTags: List<Any?>? = null,

    @field:SerializedName("product_quantity")
    val productQuantity: String? = null,

    @field:SerializedName("image_ingredients_url")
    val imageIngredientsUrl: String? = null,

    @field:SerializedName("categories_tags")
    val categoriesTags: List<String?>? = null,

    @field:SerializedName("additives_original_tags")
    val additivesOriginalTags: List<String?>? = null,

    @field:SerializedName("nutrition_data_prepared_per")
    val nutritionDataPreparedPer: String? = null,

    @field:SerializedName("nutrition_data_per")
    val nutritionDataPer: String? = null,

    @field:SerializedName("ingredients")
    val ingredients: List<IngredientsItem?>? = null,

    @field:SerializedName("nutrient_levels_tags")
    val nutrientLevelsTags: List<String?>? = null,

    @field:SerializedName("image_front_url")
    val imageFrontUrl: String? = null,

    @field:SerializedName("nova_groups")
    val novaGroups: String? = null,

    @field:SerializedName("serving_quantity")
    val servingQuantity: String? = null,

    @field:SerializedName("scans_n")
    val scansN: Int? = null,

    @field:SerializedName("image_nutrition_thumb_url")
    val imageNutritionThumbUrl: String? = null,

    @field:SerializedName("brands")
    val brands: String? = null,

    @field:SerializedName("quantity")
    val quantity: String? = null,

    @field:SerializedName("stores")
    val stores: String? = null,

    @field:SerializedName("ingredients_text_with_allergens")
    val ingredientsTextWithAllergens: String? = null,

    @field:SerializedName("manufacturing_places")
    val manufacturingPlaces: String? = null,

    @field:SerializedName("categories_hierarchy")
    val categoriesHierarchy: List<String?>? = null,

    @field:SerializedName("image_thumb_url")
    val imageThumbUrl: String? = null,

    @field:SerializedName("ingredients_that_may_be_from_palm_oil_tags")
    val ingredientsThatMayBeFromPalmOilTags: List<Any?>? = null,

    @field:SerializedName("ingredients_text")
    val ingredientsText: String? = null,

    @field:SerializedName("nutriscore_data")
    val nutriscoreData: NutriscoreData? = null,

    @field:SerializedName("nutriscore_grade")
    val nutriscoreGrade: String? = null,

    @field:SerializedName("ingredients_text_with_allergens_es")
    val ingredientsTextWithAllergensEs: String? = null,

    @field:SerializedName("pnns_groups_1")
    val pnnsGroups1: String? = null,

    @field:SerializedName("nutrition_grades")
    val nutritionGrades: String? = null,

    @field:SerializedName("pnns_groups_2")
    val pnnsGroups2: String? = null,

    @field:SerializedName("stores_tags")
    val storesTags: List<String?>? = null,

    @field:SerializedName("allergens_hierarchy")
    val allergensHierarchy: List<Any?>? = null

)