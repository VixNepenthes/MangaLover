package com.mangalovervv.fragment;

import static com.example.lib.RetrofitClient.getRetrofit;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lib.interfaceRepository.Methods;
import com.example.lib.model.StoryModel;
import com.example.story_reading_app.R;
import com.example.story_reading_app.StoryDetailActivity;
import com.example.story_reading_app.adapter.StoryAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    //story
    StoryAdapter storyAdapter;
    List<StoryModel> list = new ArrayList<>();
    GridView gdvListStory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        gdvListStory = getView().findViewById(R.id.gdvListStory);

        gdvListStory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //model into detail story
                StoryModel model = (StoryModel) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(getActivity(), StoryDetailActivity.class);
                intent.putExtra("model", model);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //story
        storyAdapter = new StoryAdapter(getActivity(), R.layout.item_story);
        try {
            GetStory();
        } catch (IOException e) {
            e.printStackTrace();
        }
        getActivity().setTitle("App Truyện");
    }


    // SHow list story
    private void GetStory() throws IOException {

        Methods methods = getRetrofit().create(Methods.class);
        Call<List<StoryModel>> call = methods.getStory();
        call.enqueue(new Callback<List<StoryModel>>() {
            @Override
            public void onResponse(Call<List<StoryModel>> call, Response<List<StoryModel>> response) {
                list = response.body();
                for(StoryModel i: response.body()){
                    storyAdapter.add(i);
                }
                gdvListStory.setAdapter(storyAdapter);
            }

            @Override
            public void onFailure(Call<List<StoryModel>> call, Throwable t) {

            }
        });
    }
}
