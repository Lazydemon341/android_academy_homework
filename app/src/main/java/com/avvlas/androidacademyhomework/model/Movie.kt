package com.avvlas.androidacademyhomework.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.avvlas.androidacademyhomework.data.local.typeconverters.GenreTypeConverters

@Entity(tableName = "movies")
@TypeConverters(GenreTypeConverters::class)
data class Movie(
    @PrimaryKey val movieId: Int,
    val pgAge: Int,
    val title: String,
    // TODO: many to many
    val genres: List<Genre>,
    val reviewCount: Int,
    val isLiked: Boolean,
    val rating: Int,
    val imageUrl: String,
    val detailImageUrl: String,
    val storyLine: String
) {
//    public constructor(
//        movieId: Int,
//        pgAge: Int,
//        title: String,
//        reviewCount: Int,
//        isLiked: Boolean,
//        rating: Int,
//        imageUrl: String,
//        detailImageUrl: String,
//        storyLine: String
//    ) : this(
//        movieId,
//        pgAge,
//        title,
//        arrayListOf(),
//        reviewCount,
//        isLiked,
//        rating,
//        imageUrl,
//        detailImageUrl,
//        storyLine
//    )


}
