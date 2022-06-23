package com.mangalovervv.admin;

import static com.mangalovervv.RetrofitClient.getRetrofit;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.mangalovervv.interfaceRepository.Methods;
import com.mangalovervv.R;
import com.mangalovervv.model.CategoryModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryInsertOrUpdate extends AppCompatActivity {

    EditText edtNameCateInsert;
    EditText edtDescribesInsert;
    CategoryModel categoryModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_insert_or_update);

        edtNameCateInsert = findViewById(R.id.edtNameCateInsert);
        edtDescribesInsert = findViewById(R.id.edtDescribesInsert);

        Intent intent = this.getIntent();
        categoryModel = (CategoryModel) intent.getSerializableExtra("model");

        if(categoryModel != null){
            // edit name button
            Button btnInsertCategory = findViewById(R.id.btnInsertCategory);
            btnInsertCategory.setText("Sứa");

            // add info plain text
            edtNameCateInsert.setText(categoryModel.getName());
            edtDescribesInsert.setText(categoryModel.getDescribes());

        }else {
            // edit name button
            Button btnInsertCategory = findViewById(R.id.btnInsertCategory);
            btnInsertCategory.setText("Thêm");
        }

    }

    //insert or update category
    public void goToInsertOrUpdateCate(View view) {
        // check
        if(categoryModel == null){
            // insert model

            try {
                if(edtNameCateInsert.getText().toString().equals("")){
                    errorInertOrupdate();
                    return;
                }

                categoryModel = new CategoryModel();
                categoryModel.setName(edtNameCateInsert.getText().toString());
                categoryModel.setDescribes(edtDescribesInsert.getText().toString());

                Methods methods = getRetrofit().create(Methods.class);
                Call<CategoryModel> call = methods.postCategory(categoryModel);
                call.enqueue(new Callback<CategoryModel>() {
                    @Override
                    public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {
                        if(response.body() != null){
                            infoInertOrupdate();
                        } else {
                            errorInertOrupdate();
                        }
                    }
                    @Override
                    public void onFailure(Call<CategoryModel> call, Throwable t) {
                    }
                });

            }catch (Exception e){
                errorInertOrupdate();
            }
        } else {
            //update model
            try {

                if(edtNameCateInsert.getText().toString().equals("")){
                    errorInertOrupdate();
                    return;
                }

                categoryModel.setName(edtNameCateInsert.getText().toString());
                categoryModel.setDescribes(edtDescribesInsert.getText().toString());

                Methods methods = getRetrofit().create(Methods.class);
                Call<CategoryModel> call = methods.putCategory(categoryModel);
                call.enqueue(new Callback<CategoryModel>() {
                    @Override
                    public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {
                        if(response.body() != null){
                            infoInertOrupdate();
                        } else {
                            errorInertOrupdate();
                        }
                    }
                    @Override
                    public void onFailure(Call<CategoryModel> call, Throwable t) {
                    }
                });

            }catch (Exception e){errorInertOrupdate();}
        }
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

    // alertDialog error
    private void errorInertOrupdate(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Thông báo!");
        alert.setIcon(R.drawable.ic_baseline_info_24);
        alert.setMessage("Không được để trống hoặc đã trùng tên thể loại!");

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alert.show();
    }

    public void goToBackInsertOrUpdate(View view) {
        finish();
    }
}