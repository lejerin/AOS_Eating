package desla.aos.eating.data.repository

import desla.aos.eating.data.model.PostsResponse
import desla.aos.eating.data.model.Review
import desla.aos.eating.data.model.UserResponse
import desla.aos.eating.data.network.ServerApi
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class MainRepository(private val server: ServerApi) {


    fun getReview() : Single<Response<Review>> =

            server.getMyReview()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())


    fun getUser() : Single<Response<UserResponse>> =

            server.getMember()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())


    fun getPostParticipated() : Single<Response<PostsResponse>> =

            server.getMyParticipated()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())


    fun getPostsMine(category: String, distance: Int, page: Int, size: Int) : Single<Response<PostsResponse>> =

            //한 페이지에 보여질 문서의 개수, 1-30 사이, 기본 값 10
            server.getPostMine(category, distance, page, size, 1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
}