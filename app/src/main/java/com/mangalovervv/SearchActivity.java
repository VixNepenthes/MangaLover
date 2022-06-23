package com.mangalovervv;

import static com.mangalovervv.RetrofitClient.getRetrofit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mangalovervv.interfaceRepository.Methods;
import com.mangalovervv.model.StoryModel;
import com.mangalovervv.adapter.StoryAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    //story
    StoryAdapter storyAdapter;
    ArrayAdapter<String> arrayAdapter;
    ListView lsvSearch;
    SearchView search_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //story
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        storyAdapter = new StoryAdapter(this, R.layout.item_search);

        search_activity = findViewById(R.id.search_activity);
        lsvSearch = findViewById(R.id.lsvSearch);

        // click choose item
        lsvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //model into detail story
                StoryModel model = (StoryModel) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(SearchActivity.this, StoryDetailActivity.class);
                intent.putExtra("model", model);
                startActivity(intent);
            }
        });

        // text show search keyword
        TextView txtLabelContentSearch = findViewById(R.id.txtLabelContentSearch);

        //search value
        search_activity.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                storyAdapter.clear();
                search_activity.clearFocus();
                txtLabelContentSearch.setText("Nội dung tìm kiếm: "+query);
                searchStory(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    // find list search story
    public void searchStory(String text){
        Methods methods = getRetrofit().create(Methods.class);
        Call<List<StoryModel>> call = methods.searchStory(-1, text, text, text);
        call.enqueue(new Callback<List<StoryModel>>() {
            @Override
            public void onResponse(Call<List<StoryModel>> call, Response<List<StoryModel>> response) {
                for(StoryModel item: response.body()){
                    storyAdapter.add(item);
                }
                lsvSearch.setAdapter(storyAdapter);
            }

            @Override
            public void onFailure(Call<List<StoryModel>> call, Throwable t) {

            }
        });
    }

    //back
    public void goToBackSearch(View view) {
        finish();
    }
}