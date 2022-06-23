package com.mangalovervv;

import static com.example.lib.RetrofitClient.getRetrofit;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.lib.interfaceRepository.Methods;
import com.example.lib.model.ChapterModel;
import com.example.lib.model.StoryModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoryDetailActivity extends AppCompatActivity {

    TextView txtNameStoryDetail;
    ImageView imgStoryDetail;
    TextView txtAuthorDetail;
    TextView txtCateStoryDetail;
    TextView txtStatusDetail;
    TextView txtSummaryDetail;
    RelativeLayout relativeBackground;
    long id;
    StoryModel model;
    
    //key save chapter
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_detail);

        txtNameStoryDetail = findViewById(R.id.txtNameStoryDetail);
        imgStoryDetail = findViewById(R.id.imgStoryDetail);
        txtAuthorDetail = findViewById(R.id.txtAuthorDetail);
        txtCateStoryDetail = findViewById(R.id.txtCateStoryDetail);
        txtStatusDetail = findViewById(R.id.txtStatusDetail);
        txtSummaryDetail = findViewById(R.id.txtSummaryDetail);
        relativeBackground = findViewById(R.id.relativeBackground);

        Intent intent = getIntent();
        model = (StoryModel) intent.getSerializableExtra("model");

        id = model.getId();
        txtNameStoryDetail.setText(model.getName());
        txtAuthorDetail.setText(model.getAuthor());
        txtCateStoryDetail.setText(model.getCategoryName());
        txtStatusDetail.setText(model.getStatusName());
        txtSummaryDetail.setText(model.getSummaryContent());
        Glide.with(this).load(model.getImage()).into(imgStoryDetail);

        Glide.with(this)
                .load(model.getImage())
                .into(new CustomTarget<Drawable>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        relativeBackground.setBackground(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    public void goToBackHome(View view) {
        finish();
    }

    // go to list chapter
    public void goToChapter(View view) {
        //model into detail story
        Intent intent = new Intent(this, ChapterActivity.class);
        intent.putExtra("modelStory", model);
        startActivity(intent);
    }

    //read chapter from button readStory
    public void goToChapterDetail(View view) {
        GetChapter();
    }

    // get list chapter
    private void GetChapter(){

        Methods methods = getRetrofit().create(Methods.class);
        Call<List<ChapterModel>> call = methods.getChapter(model.getId());
        call.enqueue(new Callback<List<ChapterModel>>() {
            @Override
            public void onResponse(Call<List<ChapterModel>> call, Response<List<ChapterModel>> response) {

                //key save chapter
                sharedPref = getSharedPreferences("ChapterSave", Activity.MODE_PRIVATE);

                //chapter of story read
                int chapterNumber = sharedPref.getInt(model.getId().toString(),-1);

                // find chater read
                if(chapterNumber == -1){
                    try {
                        Intent intent = new Intent(StoryDetailActivity.this, com.example.story_reading_app.ChapterDetailActivity.class);
                        intent.putExtra("model",  response.body().get(0));
                        //save key chapter off
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putInt(model.getId().toString(), 1).commit();
                        startActivity(intent);
                    }catch (Exception e){e.printStackTrace();}

                }else {
                    ChapterModel chapterModel = null;

                    for (ChapterModel item: response.body()){
                        if(chapterNumber == item.getChapterNumber()){
                            chapterModel = item;
                            break;
                        }
                    }
                    Intent intent = new Intent(StoryDetailActivity.this, com.example.story_reading_app.ChapterDetailActivity.class);
                    intent.putExtra("model",  chapterModel);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<List<ChapterModel>> call, Throwable t) {

            }
        });

    }
}