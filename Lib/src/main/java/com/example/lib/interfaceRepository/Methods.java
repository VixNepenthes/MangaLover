package com.example.lib.interfaceRepository;


import com.example.lib.model.CategoryModel;
import com.example.lib.model.ChapterModel;
import com.example.lib.model.DeleteModel;
import com.example.lib.model.StatusModel;
import com.example.lib.model.StoryModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Methods {
    // list category
    @GET("api/category")
    Call<List<CategoryModel>> getCategory();

    // update category
    @PUT("api/category")
    Call<CategoryModel> putCategory(@Body CategoryModel model);

    //insert category
    @POST("api/category")
    Call<CategoryModel> postCategory(@Body CategoryModel model);

    //delete category
    //@DELETE("api/category")
    @HTTP(method = "DELETE", path = "/api/category", hasBody = true)
    Call<DeleteModel> deleteCategory(@Body DeleteModel model);

   /* ---------------------------------------------------------------------------------*/

    //list story
    @GET("api/story")
    Call<List<StoryModel>> getStory();

    // search story
    @GET("api/timkiem")
    Call<List<StoryModel>> searchStory(@Query("theloai") Integer categoryId, @Query("tentruyen") String storyName, @Query("tacgia") String author, @Query("tomtat") String summary);

    //insert story
    @POST("api/story")
    Call<StoryModel> postStory(@Body StoryModel model);

    //update story
    @PUT("api/story")
    Call<StoryModel> putStory(@Body StoryModel model);

    //delete story
    @HTTP(method = "DELETE", path = "/api/story", hasBody = true)
    Call<DeleteModel> deleteStory(@Body DeleteModel model);

    /* ---------------------------------------------------------------------------------*/

    //list chapter
    @GET("api/chapter/{id}")
    Call<List<ChapterModel>> getChapter(@Path("id") Long id);

    //insert chapter
    @POST("api/chapter")
    Call<ChapterModel> postChapter(@Body ChapterModel model);

    //update chapter
    @PUT("api/chapter")
    Call<ChapterModel> putChapter(@Body ChapterModel model);

    //delete chapter
    @HTTP(method = "DELETE", path = "/api/chapter", hasBody = true)
    Call<DeleteModel> deleteChapter(@Body DeleteModel model);

    /* ---------------------------------------------------------------------------------*/

    //list status
    @GET("api/status")
    Call<List<StatusModel>> getStatus();
}
