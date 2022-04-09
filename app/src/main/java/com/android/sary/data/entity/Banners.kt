package com.android.sary.data.entity

import com.google.gson.annotations.SerializedName


data class BannersResponse(
    @SerializedName("result") var result: ArrayList<ResultResponse> = arrayListOf(),
    @SerializedName("status") var status: Boolean? = null
)

data class ResultResponse(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("button_text") var buttonText: String? = null,
    @SerializedName("expiry_status") var expiryStatus: Boolean? = null,
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("start_date") var startDate: String? = null,
    @SerializedName("expiry_date") var expiryDate: String? = null,
    @SerializedName("image") var image: String? = null,
    @SerializedName("priority") var priority: Int? = null,
    @SerializedName("photo") var photo: String? = null,
    @SerializedName("link") var link: String? = null,
    @SerializedName("level") var level: String? = null,
    @SerializedName("is_available") var isAvailable: Boolean? = null,
    @SerializedName("branch") var branch: Int? = null
)