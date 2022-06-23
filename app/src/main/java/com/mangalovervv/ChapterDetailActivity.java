package com.mangalovervv;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lib.model.ChapterModel;
import com.mangalovervv.adapter.ChapterRecycleViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChapterDetailActivity extends AppCompatActivity {

    //chapter
    ChapterModel model;
    ChapterRecycleViewAdapter adapter;
    RecyclerView rcvChapterDetail;
    List<ChapterModel> modelList = new ArrayList<>();
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_detail);

        Intent intent = getIntent();
        model = (ChapterModel) intent.getSerializableExtra("model");

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(model.getStoryName());

        rcvChapterDetail = findViewById(R.id.rcvChapterDetail);
        adapter = new ChapterRecycleViewAdapter();

        rcvChapterDetail.setLayoutManager(new LinearLayoutManager(this));

        modelList.add(model);
        adapter.setData(modelList);
        rcvChapterDetail.setAdapter(adapter);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvChapterDetail.addItemDecoration(itemDecoration);
    }

    public void goToBackChapterDetail(View view) {
        finish();
    }
}