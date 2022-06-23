package com.mangalovervv;

import static com.example.lib.RetrofitClient.getRetrofit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.lib.interfaceRepository.Methods;
import com.example.lib.model.CategoryModel;
import com.example.lib.model.StoryModel;
import com.mangalovervv.adapter.StoryAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindByStoryWithCategoryActivity extends AppCompatActivity {

    private Toolbar toolbarStoryWithCategory;
    //story
    StoryAdapter storyAdapter;
    ArrayAdapter<String> arrayAdapter;
    ListView lsvStoryWithCategory;
    SearchView search_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_by_story_with_category);

        //story
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        storyAdapter = new StoryAdapter(this, R.layout.item_search);
        lsvStoryWithCategory = findViewById(R.id.lsvStoryWithCategory);

        // toobar
        toolbarStoryWithCategory = findViewById(R.id.toolbarStoryWithCategory);

        //get model
        Intent intent = this.getIntent();
        CategoryModel model = (CategoryModel) intent.getSerializableExtra("model");

        //set title
        toolbarStoryWithCategory.setTitle(model.getName());

        searchStory(model.getId());

        lsvStoryWithCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //model into detail story
                StoryModel model = (StoryModel) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(FindByStoryWithCategoryActivity.this, StoryDetailActivity.class);
                intent.putExtra("model", model);
                startActivity(intent);
            }
        });
    }

    // find list search
    public void searchStory(Integer categoryId){
        Methods methods = getRetrofit().create(Methods.class);
        Call<List<StoryModel>> call = methods.searchStory(categoryId, "-1", "-1", "-1"); //-1 is skip
        call.enqueue(new Callback<List<StoryModel>>() {
            @Override
            public void onResponse(Call<List<StoryModel>> call, Response<List<StoryModel>> response) {
                for(StoryModel item: response.body()){
                    storyAdapter.add(item);
                }
                lsvStoryWithCategory.setAdapter(storyAdapter);
            }

            @Override
            public void onFailure(Call<List<StoryModel>> call, Throwable t) {

            }
        });
    }

    public void goToBackFindCate(View view) {
        finish();
    }
}