package com.mangalovervv.admin;

import static com.example.lib.RetrofitClient.getRetrofit;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lib.interfaceRepository.Methods;
import com.example.lib.model.ChapterModel;
import com.example.lib.model.DeleteModel;
import com.example.lib.model.StoryModel;
import com.mangalovervv.R;
import com.mangalovervv.adapter.ChapterAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListChapterAdminActivity extends AppCompatActivity {

    //chapter
    ChapterAdapter adapter;
    List<ChapterModel> list = new ArrayList<>();
    ListView lsvAdminChapter;
    StoryModel storyModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_chapter_admin);

        //chapter
        adapter = new ChapterAdapter(this, R.layout.admin_item_list_chater);
        setTitle("Danh Sách Chương");

        // get story model from list story
        Intent intent = this.getIntent();
        storyModel = (StoryModel) intent.getSerializableExtra("model");

        lsvAdminChapter = findViewById(R.id.lsvAdminChapter);

        //go to edit chap
        lsvAdminChapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //model into detail chap
                ChapterModel model = (ChapterModel) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(ListChapterAdminActivity.this, ChapterInsertOrUpdate.class);
                intent.putExtra("model", model);
                startActivity(intent);
            }
        });

        // insert button
        Button btnLsvInsertChapter = findViewById(R.id.btnLsvInsertChapter);
        btnLsvInsertChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListChapterAdminActivity.this, ChapterInsertOrUpdate.class);
                intent.putExtra("storyId", storyModel.getId());
                startActivity(intent);
            }
        });
    }

    //delete
    private void deleteChapter(DeleteModel model){
        Methods methods = getRetrofit().create(Methods.class);
        Call<DeleteModel> callDelete = methods.deleteChapter(model);
        try
        {
            callDelete.enqueue(new Callback<DeleteModel>() {
                @Override
                public void onResponse(Call<DeleteModel> call, Response<DeleteModel> response) {
                }

                @Override
                public void onFailure(Call<DeleteModel> call, Throwable t) {

                }
            });
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        System.out.println(callDelete.request().body().toString());


        //clear model on list
        if(callDelete.isExecuted() || callDelete.request().isHttps()){
            adapter.clearModelList();
            lsvAdminChapter.deferNotifyDataSetChanged();
        }
    }

    //alertDialog
    private void infoInertOrupdate(DeleteModel model){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Thông báo!");
        alert.setIcon(R.drawable.ic_baseline_info_24);
        alert.setMessage("Bạn có muốn xóa không?");

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteChapter(model);
            }
        });
        alert.show();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.clear();
        lsvAdminChapter.setAdapter(null);
        GetChater();
    }

    // get list chapter
    public void GetChater(){

        Methods methods = getRetrofit().create(Methods.class);
        Call<List<ChapterModel>> call = methods.getChapter(storyModel.getId());
        call.enqueue(new Callback<List<ChapterModel>>() {
            @Override
            public void onResponse(Call<List<ChapterModel>> call, Response<List<ChapterModel>> response) {
                list = response.body();
                for(ChapterModel i: list){
                    adapter.add(i);
                }
                lsvAdminChapter.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<List<ChapterModel>> call, Throwable t) {
            }
        });
    }

    //delete
    public void deleteChapter(View view) {
        ArrayList<Integer> arrayList = adapter.getSelectedCheccked();
        int[] chapterNumber = arrayList.stream().mapToInt(i->i).toArray();
        DeleteModel model = new DeleteModel();
        model.setChapterNumber(chapterNumber);
        model.setStoryId(storyModel.getId());
        infoInertOrupdate(model);
    }

    //close
    public void goToBackChapterAdminList(View view) {
        finish();
    }
}