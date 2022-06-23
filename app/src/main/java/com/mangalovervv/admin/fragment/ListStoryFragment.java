package com.mangalovervv.admin.fragment;

import static com.mangalovervv.RetrofitClient.getRetrofit;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.mangalovervv.interfaceRepository.Methods;
import com.mangalovervv.model.DeleteModel;
import com.mangalovervv.model.StoryModel;
import com.mangalovervv.R;
import com.mangalovervv.adapter.StoryAdapter;
import com.mangalovervv.admin.StoryInsertOrUpdate;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListStoryFragment extends Fragment {
    //story
    StoryAdapter adapter;
    List<StoryModel> list = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    ListView lsvAdminStory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_list_story, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        lsvAdminStory = getView().findViewById(R.id.lsvAdminStory);


        lsvAdminStory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //model into detail story
                StoryModel model = (StoryModel) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(getActivity(), StoryInsertOrUpdate.class);
                intent.putExtra("model", model);
                startActivity(intent);
            }
        });

        // insert button
        Button btnLsvInsertCate = view.findViewById(R.id.btnLsvInsertStory);
        btnLsvInsertCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), StoryInsertOrUpdate.class);
                startActivity(intent);
            }
        });

        // delete button
        Button btnDeleteCate = getView().findViewById(R.id.btnDeleteStory);
        btnDeleteCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Long> arrayList = adapter.getSelectedChecckedCategory();
                //Integer categoryId[] = arrayList.toArray(new Integer[arrayList.size()]);
                long[] storyId = arrayList.stream().mapToLong(i->i).toArray();
                DeleteModel model = new DeleteModel();
                model.setIds(storyId);
                infoInertOrupdate(model);
            }
        });

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //category
        arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1);
        adapter = new StoryAdapter(getActivity(), R.layout.admin_item_list_story);
        getActivity().setTitle("Danh Sách Truyện");
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.clear();
        lsvAdminStory.setAdapter(null);
        GetCategoryName();
    }

    // get list category
    private void GetCategoryName(){

        Methods methods = getRetrofit().create(Methods.class);
        Call<List<StoryModel>> call = methods.getStory();
        call.enqueue(new Callback<List<StoryModel>>() {
            @Override
            public void onResponse(Call<List<StoryModel>> call, Response<List<StoryModel>> response) {
                list = response.body();

                for(StoryModel i: list){
                    adapter.add(i);
                }
                lsvAdminStory.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<StoryModel>> call, Throwable t) {
            }
        });
    }

    //delete
    private void deleteStory(DeleteModel model){
        Methods methods = getRetrofit().create(Methods.class);
        Call<DeleteModel> callDelete = methods.deleteStory(model);
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

        //clear model on list
        if(callDelete.isExecuted()){
            adapter.clearModelList();
            lsvAdminStory.deferNotifyDataSetChanged();
        }
    }


    //alertDialog
    private void infoInertOrupdate(DeleteModel model){
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
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
                deleteStory(model);
            }
        });
        alert.show();
    }
}
