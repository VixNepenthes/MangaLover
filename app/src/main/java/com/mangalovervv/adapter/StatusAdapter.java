package com.mangalovervv.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mangalovervv.model.CategoryModel;
import com.mangalovervv.model.StatusModel;
import com.mangalovervv.R;

import java.util.ArrayList;
import java.util.List;

public class StatusAdapter extends ArrayAdapter<StatusModel> {
    Activity context;
    int resource;
    private ArrayList<Integer> categoryIds = new ArrayList<Integer>();
    private List<CategoryModel> modelList = new ArrayList<>();

    public StatusAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.context = (Activity) context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = this.context.getLayoutInflater();
        View view = layoutInflater.inflate(this.resource, null);

        StatusModel model = getItem(position);


        if(this.resource == R.layout.admin_spinner_item){
            TextView name = view.findViewById(R.id.txtNameCateItemSpinner);

            name.setText(model.getName());
        }
        return view;
    }

    //dropdown
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = this.context.getLayoutInflater();
        View view = layoutInflater.inflate(this.resource, null);

        StatusModel model = getItem(position);

        if(this.resource == R.layout.admin_spinner_item){
            TextView name = view.findViewById(R.id.txtNameCateItemSpinner);

            name.setText(model.getName());
        }
        return view;
    }
}
