package com.mangalovervv.fragment;

import static com.example.lib.RetrofitClient.getRetrofit;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.lib.interfaceRepository.Methods;
import com.example.lib.model.CategoryModel;
import com.example.lib.model.DeleteModel;
import com.example.story_reading_app.R;
import com.example.story_reading_app.adapter.CategoryAdapter;
import com.example.story_reading_app.admin.CategoryInsertOrUpdate;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListCategoryFragment extends Fragment {

    //category
    CategoryAdapter adapter;
    List<CategoryModel> list = new ArrayList<>();
    ListView lsvAdminCategory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_list_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        lsvAdminCategory = getView().findViewById(R.id.lsvAdminCategory);


        lsvAdminCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //model into detail cate
                CategoryModel model = (CategoryModel) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(getActivity(), CategoryInsertOrUpdate.class);
                intent.putExtra("model", model);
                startActivity(intent);
            }
        });

        // insert button
        Button btnLsvInsertCate = view.findViewById(R.id.btnLsvInsertCate);
        btnLsvInsertCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CategoryInsertOrUpdate.class);
                startActivity(intent);
            }
        });

        // delete button
        Button btnDeleteCate = getView().findViewById(R.id.btnDeleteCate);
        btnDeleteCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Integer> arrayList = adapter.getSelectedChecckedCategory();
                //Integer categoryId[] = arrayList.toArray(new Integer[arrayList.size()]);
                int[] categoryId = arrayList.stream().mapToInt(i->i).toArray();
                DeleteModel model = new DeleteModel();
                model.setCategoryIds(categoryId);
                infoInertOrupdate(model);
            }
        });

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //category
        adapter = new CategoryAdapter(getActivity(), R.layout.admin_item_list_category);
        getActivity().setTitle("Danh Sách Thể Loại");
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.clear();
        lsvAdminCategory.setAdapter(null);
        GetCategoryName();
    }

    // get list category
    public void GetCategoryName(){

        Methods methods = getRetrofit().create(Methods.class);
        Call<List<CategoryModel>> call = methods.getCategory();
        call.enqueue(new Callback<List<CategoryModel>>() {
            @Override
            public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {
                list = response.body();

                for(CategoryModel i: list){
                    adapter.add(i);
                }
                lsvAdminCategory.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<CategoryModel>> call, Throwable t) {
            }
        });
    }

    //tomorrow fix
    //delete
    private void deleteCategory(DeleteModel model){
       Methods methods = getRetrofit().create(Methods.class);
       Call<DeleteModel> callDelete = methods.deleteCategory(model);
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
            lsvAdminCategory.deferNotifyDataSetChanged();
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
                try{
                    deleteCategory(model);
                }catch (Exception e){infoWaring();};
            }
        });
        alert.show();
    }

    //info alert
    //alertDialog
    private void infoWaring() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Thông báo!");
        alert.setIcon(R.drawable.ic_baseline_info_24);
        alert.setMessage("Không thể xóa vì đã có truyện!");
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        alert.show();
    }
}
