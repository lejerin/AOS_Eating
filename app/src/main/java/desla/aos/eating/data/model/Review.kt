package desla.aos.eating.data.model


import com.google.gson.annotations.SerializedName

data class Review(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
) {
    data class Data(
        @SerializedName("review")
        val review: String,
        @SerializedName("senderNickname")
        val senderNickname: String,
        @SerializedName("writeDate")
        val writeDate: String
    )
}