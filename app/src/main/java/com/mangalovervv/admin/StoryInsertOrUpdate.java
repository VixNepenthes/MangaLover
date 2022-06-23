package com.mangalovervv.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import static com.mangalovervv.RetrofitClient.getRetrofit;


import com.mangalovervv.R;
import com.mangalovervv.adapter.CategoryAdapter;
import com.mangalovervv.adapter.StatusAdapter;
import com.mangalovervv.interfaceRepository.Methods;
import com.mangalovervv.model.CategoryModel;
import com.mangalovervv.model.StatusModel;
import com.mangalovervv.model.StoryModel;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class StoryInsertOrUpdate extends AppCompatActivity {

    //category
    CategoryAdapter adapter;
    List<CategoryModel> list = new ArrayList<>();
    Spinner spnListCateIU;

    //status
    StatusAdapter statusAdapter;
    Spinner spnListStatusIU;

    //story
    StoryModel model;
    EditText name;
    int cateId;
    int statusId;
    EditText author;
    EditText summary;
    EditText image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_insert_or_update);

        //list cate
        spnListCateIU = findViewById(R.id.spnListCateIU);
        adapter = new CategoryAdapter(this, R.layout.admin_spinner_item);
        GetCategoryName();

        //list status
        spnListStatusIU = findViewById(R.id.spnListStatusIU);
        statusAdapter = new StatusAdapter(this, R.layout.admin_spinner_item);
        GetStatus();

        //add find id view
        Button btnInsertOrUpdateIU = findViewById(R.id.btnInsertOrUpdateIU);
        name = findViewById(R.id.txtNameStoryIU);
        author = findViewById(R.id.txtAuthorIU);
        summary = findViewById(R.id.txtSummaryIU);
        image = findViewById(R.id.txtLinkImgIU);

        spnListCateIU.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CategoryModel categoryModel = (CategoryModel) adapterView.getItemAtPosition(i);
                cateId = categoryModel.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spnListStatusIU.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                StatusModel statusModel = (StatusModel) adapterView.getItemAtPosition(i);
                statusId = statusModel.getId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //model story
        Intent intent = this.getIntent();
        model = (StoryModel) intent.getSerializableExtra("model");

        if(model == null){
            // edit name button
            btnInsertOrUpdateIU.setText("Thêm");

        } else { //set text layout from model
            btnInsertOrUpdateIU.setText("Sửa");
            name.setText(model.getName());
            author.setText(model.getAuthor());
            summary.setText(model.getSummaryContent());
            image.setText(model.getImage());
        }
    }

    // get list category
    public void GetCategoryName(){

        Methods methods = getRetrofit().create(Methods.class);
        retrofit2.Call<List<CategoryModel>> call = methods.getCategory();
        call.enqueue(new Callback<List<CategoryModel>>() {
            @Override
            public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {
                list = response.body();
                CategoryModel temp = new CategoryModel();
                for(CategoryModel i: list){
                    adapter.add(i);
                    if(model != null && (int)model.getCategoryId() == (int)i.getId()){
                        temp = i;
                    }
                }
                spnListCateIU.setAdapter(adapter);
                spnListCateIU.setSelection(adapter.getPosition(temp));
            }

            @Override
            public void onFailure(Call<List<CategoryModel>> call, Throwable t) {
            }
        });
    }

    // get list status
    public void GetStatus(){

        Methods methods = getRetrofit().create(Methods.class);
        Call<List<StatusModel>> call = methods.getStatus();
        call.enqueue(new Callback<List<StatusModel>>() {
            @Override
            public void onResponse(Call<List<StatusModel>> call, Response<List<StatusModel>> response) {
                StatusModel temp = new StatusModel();
                for(StatusModel i: response.body()){
                    statusAdapter.add(i);
                    if(model != null && (int)model.getStatusId() == (int)i.getId()){
                        temp = i;
                    }
                }
                spnListStatusIU.setAdapter(statusAdapter);
                spnListStatusIU.setSelection(statusAdapter.getPosition(temp));
                int o=0;
            }

            @Override
            public void onFailure(Call<List<StatusModel>> call, Throwable t) {
            }
        });
    }

    //button insert or update story
    public void goToInsertOrUpdateStory(View view) {
        //Insert
        if(model == null){
            model = new StoryModel();

            //check
            if(name.getText().toString().equals("") || author.getText().toString().equals("")){

            }else {
                //set model
                model.setName(name.getText().toString());
                model.setAuthor(author.getText().toString());
                model.setSummaryContent(summary.getText().toString());
                model.setImage(image.getText().toString());
                model.setStatusId(statusId);
                model.setCategoryId(cateId);
                model.setUserId(1L);

                // call api
                Methods methods = getRetrofit().create(Methods.class);
                Call<StoryModel> call = methods.postStory(model);
                call.enqueue(new Callback<StoryModel>() {
                    @Override
                    public void onResponse(Call<StoryModel> call, Response<StoryModel> response) {
                        if(response.body() != null){
                            infoInertOrupdate();
                        } else {
                            errorInertOrupdate();
                        }
                    }

                    @Override
                    public void onFailure(Call<StoryModel> call, Throwable t) {
                    }
                });
            }
        } else {
            //update
            //check
            if(name.getText().toString().equals("") || author.getText().toString().equals("")){

            }else {
                //set model
                model.setName(name.getText().toString());
                model.setAuthor(author.getText().toString());
                model.setSummaryContent(summary.getText().toString());
                model.setImage(image.getText().toString());
                model.setStatusId(statusId);
                model.setCategoryId(cateId);
                model.setUserId(1L);

                //date
                Timestamp temp = model.getDateCreate();
                if(temp != null){
                    model.setDateCreate(convertStringToTimestamp(temp.toString()));
                }

                // call api
                Methods methods = getRetrofit().create(Methods.class);
                Call<StoryModel> call = methods.putStory(model);
                call.enqueue(new Callback<StoryModel>() {
                    @Override
                    public void onResponse(Call<StoryModel> call, Response<StoryModel> response) {
                        if(response.body() != null){
                            infoInertOrupdate();
                        } else {
                            errorInertOrupdate();
                        }
                    }

                    @Override
                    public void onFailure(Call<StoryModel> call, Throwable t) {
                        System.out.println(t.toString());
                    }
                });
            }
        }
    }

    // fomat time
    public static Timestamp convertStringToTimestamp(String strDate) {
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSZ");
            // you can change format of date
            Date date = formatter.parse(strDate);
            Timestamp timeStampDate = new Timestamp(date.getTime());

            return timeStampDate;
        } catch (ParseException e) {
            System.out.println("Exception :" + e);
            return null;
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

    //close
    public void goToBackListStoryAdmin(View view) {
        finish();
    }
}