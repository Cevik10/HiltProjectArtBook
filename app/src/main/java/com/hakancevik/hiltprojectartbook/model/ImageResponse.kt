package com.hakancevik.hiltprojectartbook.model


data class ImageResponse(
    val hits: List<ImageResult>,
    val total: Int,
    val totalHits: Int
)

