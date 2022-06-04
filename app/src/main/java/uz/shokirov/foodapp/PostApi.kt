package uz.shokirov.foodapp

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import uz.shokirov.foodapp.Constants.END_POINT_DELETE
import uz.shokirov.foodapp.Constants.END_POINT_GET
import uz.shokirov.foodapp.Constants.END_POINT_POST

interface PostApi {
    @GET(END_POINT_GET)
    suspend fun getAllPosts():Response<List<PostItem>>

    @POST(END_POINT_POST)
    suspend fun posting(
        @Body postItem: PostItem
    ):Response<PostItem>


    @DELETE(END_POINT_DELETE)
    suspend fun deletePost():Response<ResponseBody>

}