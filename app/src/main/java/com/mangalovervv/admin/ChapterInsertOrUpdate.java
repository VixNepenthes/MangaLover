package com.mangalovervv.admin;

import static com.mangalovervv.RetrofitClient.getRetrofit;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import com.mangalovervv.interfaceRepository.Methods;
import com.mangalovervv.model.ChapterModel;
import com.mangalovervv.R;


import java.sql.Timestamp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChapterInsertOrUpdate extends AppCompatActivity {

    //
    ChapterModel chapterModel;
    EditText txtChapNumAdmin;
    EditText txtChapterNameAdmin;
    EditText txtContentChapterAdmin;
    Button btnInsertOrUpdateChapIU;
    Long storyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_insert_or_update);

        //take model chapter
        Intent intent = this.getIntent();
        chapterModel = (ChapterModel) intent.getSerializableExtra("model");
        storyId = (Long) intent.getSerializableExtra("storyId");

        //
        txtChapNumAdmin = findViewById(R.id.txtChapNumAdmin);
        txtChapterNameAdmin = findViewById(R.id.txtChapterNameAdmin);
        txtContentChapterAdmin = findViewById(R.id.txtContentChapterAdmin);

        // check
        if(chapterModel != null){
            // edit name button
            btnInsertOrUpdateChapIU = findViewById(R.id.btnInsertOrUpdateChapIU);
            btnInsertOrUpdateChapIU.setText("Sứa");

            TextView lblChapNumAdmin = findViewById(R.id.lblChapNumAdmin);

            lblChapNumAdmin.setVisibility(View.GONE);
            txtChapNumAdmin.setVisibility(View.GONE);

            txtChapterNameAdmin.setText(chapterModel.getName());
            txtContentChapterAdmin.setText(Html.fromHtml(chapterModel.getContent(), HtmlCompat.FROM_HTML_MODE_LEGACY));
        }

    }

    //insert or update
    public void InserOrUpdateChapIU(View view) {
        // check
        if(chapterModel == null){
            // insert model

            try {
                if(txtChapNumAdmin.getText().toString().equals("") || txtChapterNameAdmin.getText().toString().equals("") || txtContentChapterAdmin.getText().toString().equals("")){
                    errorInertOrupdate();
                    return;
                }

                chapterModel = new ChapterModel();
                chapterModel.setChapterNumber(Integer.parseInt(txtChapNumAdmin.getText().toString()));
                chapterModel.setStoryId(storyId);
                chapterModel.setName(txtChapterNameAdmin.getText().toString());
                chapterModel.setContent(txtContentChapterAdmin.getText().toString());

                Methods methods = getRetrofit().create(Methods.class);
                Call<ChapterModel> call = methods.postChapter(chapterModel);
                call.enqueue(new Callback<ChapterModel>() {
                    @Override
                    public void onResponse(Call<ChapterModel> call, Response<ChapterModel> response) {
                        if(response.body() != null){
                            infoInertOrupdate();
                        } else if(response.body().toString().equals("")) {
                            errorInertOrupdate();
                        }
                    }

                    @Override
                    public void onFailure(Call<ChapterModel> call, Throwable t) {
                        errorInertOrupdate();
                    }
                });
                if(call.isExecuted()){
                    chapterModel = null;
                }
            }catch (Exception e){
                errorInertOrupdate();
            }
        } else {
            //update model
            try {

                if(txtChapterNameAdmin.getText().toString().equals("") || txtContentChapterAdmin.getText().toString().equals("")){
                    errorInertOrupdate();
                    return;
                }

                chapterModel.setName(txtChapterNameAdmin.getText().toString());
                chapterModel.setContent(txtContentChapterAdmin.getText().toString());
                //date
                Timestamp temp = chapterModel.getDateCreate();
                if(temp != null){
                    chapterModel.setDateCreate(StoryInsertOrUpdate.convertStringToTimestamp(temp.toString()));
                }
                Timestamp currentTime = new Timestamp(System.currentTimeMillis());;
                chapterModel.setDateUpdate(StoryInsertOrUpdate.convertStringToTimestamp(currentTime.toString()));

                Methods methods = getRetrofit().create(Methods.class);
                Call<ChapterModel> call = methods.putChapter(chapterModel);
                call.enqueue(new Callback<ChapterModel>() {
                    @Override
                    public void onResponse(Call<ChapterModel> call, Response<ChapterModel> response) {
                        if(response.body() != null){
                            infoInertOrupdate();
                        } else {
                            errorInertOrupdate();
                        }
                    }
                    @Override
                    public void onFailure(Call<ChapterModel> call, Throwable t) {
                    }
                });

            }catch (Exception e){System.out.println(e); errorInertOrupdate();}
        }
    }

    // alertDialog error
    private void errorInertOrupdate(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Thông báo!");
        alert.setIcon(R.drawable.ic_baseline_info_24);
        alert.setMessage("Không được để trống hoặc đã trùng tên!");

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alert.show();
    }

    //alertDialog
    private void infoInertOrupdate(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Thông báo!");
        alert.setIcon(R.drawable.ic_baseline_info_24);
        alert.setMessage("Thay đổi thông tin thành công!");

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alert.show();
    }

    // close
    public void goToBackChapterIU(View view) {
        finish();
    }
}