package com.skilbox.materialdesing.fakestoreapi.data

data class Product(
    val id: Int,
    val title: String,
    val price: String,
    val category: String,
    val description: String,
    val image: String,
    val rating: Rating
)

data class Rating(
    val rate: Double,
    val count: Int
)
