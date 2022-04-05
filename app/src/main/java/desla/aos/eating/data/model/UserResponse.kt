package desla.aos.eating.data.model


import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("data")
    val data: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
) {
    data class Data(
        @SerializedName("fastAnswer")
        val fastAnswer: Int,
        @SerializedName("foodDivide")
        val foodDivide: Int,
        @SerializedName("niceGuy")
        val niceGuy: Int,
        @SerializedName("nickname")
        val nickname: String,
        @SerializedName("sugarScore")
        val sugarScore: Int,
        @SerializedName("timeGood")
        val timeGood: Int,
        @SerializedName("totalCount")
        val totalCount: Int
    )
}