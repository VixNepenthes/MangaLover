package com.mangalovervv.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.mangalovervv.model.StoryModel;
import com.mangalovervv.admin.ListChapterAdminActivity;
import com.mangalovervv.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class StoryAdapter extends ArrayAdapter<StoryModel> {

    Activity context;
    int resource;

    private ArrayList<Long> storyIds = new ArrayList<Long>();
    private List<StoryModel> modelList = new ArrayList<>();

    public StoryAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.context = (Activity)context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = this.context.getLayoutInflater();
        View view = layoutInflater.inflate(this.resource, null);

        TextView nameStory;
        ImageView imageView;

        if(this.resource == R.layout.item_story){
            // create value
            nameStory = view.findViewById(R.id.txtNameStory);
            TextView nameChapterLast = view.findViewById(R.id.txtChapterNumberLast);
            imageView = view.findViewById(R.id.imgImageStory);

            //add view
            StoryModel model = getItem(position);
            nameStory.setText(model.getName());
            if(model.getChapterNumberLast() != null){
                nameChapterLast.setText("Chương "+model.getChapterNumberLast());
            }else {
                nameChapterLast.setText("Chưa tạo");
            }
            Glide.with(this.context).load(model.getImage()).into(imageView);
        } else if(this.resource == R.layout.item_search){

            //
            nameStory = view.findViewById(R.id.txtNameSearchItem);
            TextView author = view.findViewById(R.id.txtAuthorSearchItem);
            TextView numChap = view.findViewById(R.id.txtNumChapSearchItem);
            TextView summary = view.findViewById(R.id.txtSummarySearchItem);
            imageView = view.findViewById(R.id.imgSearchItem);

            //add view
            StoryModel model = getItem(position);
            nameStory.setText(model.getName());
            author.setText("Tác giả: " + model.getAuthor());

            if(model.getChapterNumberLast() != null){
                numChap.setText("Tổng số chương: " + model.getChapterNumberLast());
            }else {
                numChap.setText("Tổng số chương: 0");
            }
            summary.setText("Tóm tắt: " + model.getSummaryContent());
            Glide.with(this.context).load(model.getImage()).into(imageView);

        } else if(this.resource == R.layout.admin_item_list_story){
            //
            TextView name = view.findViewById(R.id.txtAdminStoryNameItem);
            TextView date = view.findViewById(R.id.txtAdminStoryDateItem);
            CheckBox cbStoryDeleteItem = view.findViewById(R.id.cbStoryDeleteItem);

            //
            StoryModel model = getItem(position);
            name.setText(model.getName());

            //format date
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String today = formatter.format(model.getDateCreate());
            date.setText(today);

            //check box delete
            cbStoryDeleteItem.setTag(position);
            cbStoryDeleteItem.setChecked(storyIds.contains(model.getId()));
            cbStoryDeleteItem.setChecked(modelList.contains(model));
            cbStoryDeleteItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (!compoundButton.isChecked()) {
                        storyIds.remove(model.getId());
                        modelList.remove(model);
                    } else if (compoundButton.isChecked()) {
                        if (!storyIds.contains(model.getId())) {
                            storyIds.add(model.getId());
                            modelList.add(model);
                        }
                    }
                }
            });

            //go to chapter
            Button btnGoToChapter = (Button) view.findViewById(R.id.btnGoToChapter);
            btnGoToChapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), ListChapterAdminActivity.class);
                    intent.putExtra("model", model);
                    context.startActivity(intent);
                }
            });

        }
        return view;
    }

    // get category id choose
    public ArrayList<Long> getSelectedChecckedCategory() {
        return storyIds;
    }

    public void clearSelectedCheckedCategory() {
        storyIds.clear();
    }

    //clear model on adapter
    public void clearModelList(){
        for (StoryModel item: modelList){
            this.remove(item);
        }
    }

}
