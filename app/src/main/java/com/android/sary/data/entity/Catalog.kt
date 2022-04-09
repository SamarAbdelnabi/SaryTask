package com.android.sary.data.entity

import com.google.gson.annotations.SerializedName


data class Catalog(

    @SerializedName("result") var result: ArrayList<CatalogResult> = arrayListOf(),
    @SerializedName("other") var other: Other? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("status") var status: Boolean? = null

)

data class CatalogResult(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("data") var data: ArrayList<Data> = arrayListOf(),
    @SerializedName("data_type") var dataType: String? = null,
    @SerializedName("show_title") var showTitle: Boolean? = null,
    @SerializedName("ui_type") var uiType: String? = null,
    @SerializedName("row_count") var rowCount: Int? = null

){
    fun getDataType(): DataType{
        return when (dataType){
            "smart" -> DataType.SMART
            "group" -> DataType.GROUP
            "banner" -> DataType.BANNER
            else -> DataType.UNKNOWN
        }
    }

    fun getUiType(): UiType{
        return when (uiType){
            "grid" -> UiType.GRID
            "linear" -> UiType.LINEAR
            "slider" -> UiType.SLIDER
            else -> UiType.UNKNOWN
        }
    }
}

enum class DataType(val id: Int) {
    SMART(1), GROUP(2), BANNER(3), UNKNOWN(0)
}
enum class UiType(val id: Int) {
    GRID(1), LINEAR(2), SLIDER(3), UNKNOWN(0)
}

data class BusinessStatus(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("title") var title: String? = null

)

data class Data(

    @SerializedName("group_id") var groupId: Int? = null,
    @SerializedName("filters") var filters: ArrayList<Filter> = arrayListOf(),
    @SerializedName("name") var name: String? = null,
    @SerializedName("image") var image: String? = null,
    @SerializedName("empty_content_image") var emptyContentImage: String? = null,
    @SerializedName("empty_content_message") var emptyContentMessage: String? = null,
    @SerializedName("has_data") var hasData: Boolean? = null,
    @SerializedName("show_unavailable_items") var showUnavailableItems: Boolean? = null,
    @SerializedName("show_in_brochure_link") var showInBrochureLink: Boolean? = null

)

data class Other(

    @SerializedName("show_special_order_view") var showSpecialOrderView: Boolean? = null,
    @SerializedName("uncompleted_profile_settings") var uncompletedProfileSettings: UncompletedProfileSettings? =null,
    @SerializedName("business_status") var businessStatus: BusinessStatus? = null

)

data class UncompletedProfileSettings(

    @SerializedName("show_tag") var showTag: Boolean? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("image") var image: String? = null,
    @SerializedName("is_completed_profile") var isCompletedProfile: Boolean? = null

)

data class Filter(

    @SerializedName("filter_id") var filterId: Int? = null,
    @SerializedName("name") var name: String? = null

)