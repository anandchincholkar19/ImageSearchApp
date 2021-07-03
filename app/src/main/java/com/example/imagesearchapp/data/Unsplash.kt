package com.example.imagesearchapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Unsplash(
    val results: List<Result>,
):Parcelable

@Parcelize
data class Result(
    val blur_hash: String,
    val color: String,
    val created_at: String,
    val description: String,
    val height: Int,
    val id: String,
    val liked_by_user: Boolean,
    val likes: Int,
    val links: Links,
    val urls: Urls,
    val user: User,
    val width: Int
):Parcelable

@Parcelize
data class User(
    val first_name: String,
    val id: String,
    val instagram_username: String,
    val last_name: String,
    val links: LinksX,
    val name: String,
    val portfolio_url: String,
    val profile_image: ProfileImage,
    val twitter_username: String,
    val username: String
):Parcelable

@Parcelize
data class Urls(
    val full: String,
    val raw: String,
    val regular: String,
    val small: String,
    val thumb: String
):Parcelable

@Parcelize
data class ProfileImage(
    val large: String,
    val medium: String,
    val small: String
):Parcelable

@Parcelize
data class LinksX(
    val html: String,
    val likes: String,
    val photos: String,
    val self: String
):Parcelable


@Parcelize
data class Links(
    val download: String,
    val html: String,
    val self: String
):Parcelable
