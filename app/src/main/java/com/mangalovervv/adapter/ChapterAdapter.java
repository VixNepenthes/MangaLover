package com.mangalovervv.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mangalovervv.model.ChapterModel;
import com.mangalovervv.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ChapterAdapter extends ArrayAdapter<ChapterModel> {

    Activity context;
    int resource;
    //
    ArrayList<Integer> chapterNumber = new ArrayList<>();
    List<ChapterModel> modelList = new ArrayList<>();

    public ChapterAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.context = (Activity) context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = this.context.getLayoutInflater();
        View view = layoutInflater.inflate(this.resource, null);

        ChapterModel model = getItem(position);

        if(this.resource == R.layout.item_chapter_name){
            //khai bao
            TextView txtChapterItem = view.findViewById(R.id.txtChapterItem);

            //gan gia tri
            txtChapterItem.setText("Chương "+ model.getChapterNumber() + ": " + model.getName());

            //chapter of story read
            SharedPreferences sharedPref = this.context.getSharedPreferences("ChapterSave", context.MODE_PRIVATE);
            int chapterNumber = sharedPref.getInt(model.getStoryId().toString(),-1);
            if(chapterNumber == model.getChapterNumber()){
                txtChapterItem.setTypeface(Typeface.DEFAULT_BOLD);
            }
        } else if (this.resource == R.layout.item_chapter_detail){

            TextView txtChapterNameNumber = view.findViewById(R.id.txtChapterNameNumber);
            TextView txtChapterContent = view.findViewById(R.id.txtChapterContent);

            txtChapterNameNumber.setText("Chương "+ model.getChapterNumber() + ": " + model.getName());
            txtChapterContent.setText(model.getContent());

        } else if(this.resource == R.layout.admin_item_list_chater){
            TextView name = view.findViewById(R.id.txtAdminChapterNameItem);
            TextView dateCrate = view.findViewById(R.id.txtAdminChapterDateItem);
            TextView numberChap = view.findViewById(R.id.txtAdminChapterNumberItem);
            CheckBox cbChapterDeleteItem = view.findViewById(R.id.cbChapterDeleteItem);

            //format date
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String today = formatter.format(model.getDateCreate());
            dateCrate.setText(today);

            //set name
            name.setText(model.getName());
            numberChap.setText(model.getChapterNumber().toString());

            //delete
            //check box delete
            cbChapterDeleteItem.setTag(position);
            cbChapterDeleteItem.setChecked(chapterNumber.contains(model.getChapterNumber()));
            cbChapterDeleteItem.setChecked(modelList.contains(model));
            cbChapterDeleteItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (!compoundButton.isChecked()) {
                        chapterNumber.remove(model.getChapterNumber());
                        modelList.remove(model);
                    } else if (compoundButton.isChecked()) {
                        if (!chapterNumber.contains(model.getChapterNumber())) {
                            chapterNumber.add(model.getChapterNumber());
                            modelList.add(model);
                        }
                    }
                }
            });
        }
        return view;
    }

    // get category id choose
    public ArrayList<Integer> getSelectedCheccked() {
        return chapterNumber;
    }

    public void clearSelectedChecked() {
        chapterNumber.clear();
    }

    //clear model on adapter
    public void clearModelList(){
        for (ChapterModel item: modelList){
            this.remove(item);
        }
    }
}
