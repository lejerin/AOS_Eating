package desla.aos.eating.data.repository

import desla.aos.eating.data.model.PostResponse
import desla.aos.eating.data.model.PostsResponse
import desla.aos.eating.data.network.ServerApi
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class HomeRepository(private val server: ServerApi) {


    fun getPosts(category: String, distance: Int, page: Int, size: Int) : Single<Response<PostsResponse>> =

        //한 페이지에 보여질 문서의 개수, 1-30 사이, 기본 값 10
        server.getPost(category, distance, page, size)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun postFavorite(postId: Int) : Single<Response<PostResponse>> =

            server.postFavorite(postId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

    fun postUnFavorite(postId: Int) : Single<Response<PostResponse>> =

            server.postUnFavorite(postId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())


}