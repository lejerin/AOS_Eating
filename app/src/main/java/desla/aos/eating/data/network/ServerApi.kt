package desla.aos.eating.data.network

import desla.aos.eating.data.model.*
import io.reactivex.Single
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.net.CookieHandler
import java.net.CookieManager
import java.util.concurrent.TimeUnit

interface ServerApi {

    //user info
    @GET("member")
    fun getMember(
    ): Single<Response<UserResponse>>

    @GET("posts/participatied")
    fun getMyParticipated(): Single<Response<PostsResponse>>

    @GET("posts")
    fun getPostMine(
            @Query("category") category: String,
            @Query("distance") distance: Int,
            @Query("page") page: Int,
            @Query("size") size: Int,
            @Query("mine") mine: Int
    ): Single<Response<PostsResponse>>

    @GET("review")
    fun getMyReview(): Single<Response<Review>>


    //Login Repository
    @POST("member/login")
    fun postLogin(
        @Body parameters: HashMap<String, Any>
    ): Single<Response<LoginResponse>>

    @POST("member/join")
    fun postRegister(
        @Body parameters: HashMap<String, Any>
    ): Single<Response<PostResponse>>

    @PUT("member/address")
    fun modifyAddress(
            @Body parameters: HashMap<String, Any>
    ): Single<Response<PostResponse>>


    @POST("post")
    fun postWrite(
            @Body parameters: HashMap<String, Any?>
    ): Single<Response<PostResponse>>

    @GET("posts")
    fun getPost(
            @Query("category") category: String,
            @Query("distance") distance: Int,
            @Query("page") page: Int,
            @Query("size") size: Int
    ): Single<Response<PostsResponse>>

    @GET("posts/participating")
    fun getPostParticipated(): Single<Response<PostsResponse>>

    @GET("posts/favorite")
    fun getPostFavorite(): Single<Response<PostsResponse>>


    @GET("post/{postId}")
    fun getPostDetail(
            @Path("postId") postId: Int
    ): Single<Response<DetailResponse>>

    @GET("post/join/{postId}")
    fun postJoin(
            @Path("postId") postId: Int
    ): Single<Response<PostResponse>>

    //찜하기
    @GET("favorite/{postId}")
    fun postFavorite(
            @Path("postId") postId: Int
    ): Single<Response<PostResponse>>

    //찜하기 취소
    @GET("unfavorite/{postId}")
    fun postUnFavorite(
            @Path("postId") postId: Int
    ): Single<Response<PostResponse>>

    companion object {
        operator fun invoke(): ServerApi {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://3.34.214.72/")
                .client(client)
                .build()
                .create(ServerApi::class.java)
        }

        var cookieHandler: CookieHandler = CookieManager()


        private val client : OkHttpClient
            private get() {
                val builder = OkHttpClient.Builder()
                builder.cookieJar(JavaNetCookieJar(cookieHandler))
                builder.connectTimeout(15, TimeUnit.SECONDS)
                builder.readTimeout(20, TimeUnit.SECONDS)
                builder.writeTimeout(20, TimeUnit.SECONDS)
                builder.retryOnConnectionFailure(true)
                return builder.build()
            }
    }


}