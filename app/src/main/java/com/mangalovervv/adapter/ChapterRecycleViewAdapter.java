package com.mangalovervv.adapter;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mangalovervv.model.ChapterModel;
import com.mangalovervv.R;

import java.util.List;

public class ChapterRecycleViewAdapter extends RecyclerView.Adapter<ChapterRecycleViewAdapter.ChapterViewHolder> {

    private List<ChapterModel> modelList;

    public void setData(List<ChapterModel> modelList){
        this.modelList = modelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chapter_detail, parent, false);
        //view.setVerticalScrollbarPosition(View.SCROLLBAR_POSITION_RIGHT);
        return new ChapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterViewHolder holder, int position) {
        ChapterModel model = modelList.get(position);
        holder.txtChapterNameNumber.setText("Chương "+model.getChapterNumber()+": "+model.getName());
        holder.txtChapterContent.setText(Html.fromHtml(model.getContent(), HtmlCompat.FROM_HTML_MODE_LEGACY));
    }

    @Override
    public int getItemCount() {
        if(modelList != null){
           return modelList.size();
        }
        return 0;
    }


    public class ChapterViewHolder extends RecyclerView.ViewHolder{

        private TextView txtChapterNameNumber, txtChapterContent;

        public ChapterViewHolder(@NonNull View itemView) {
            super(itemView);
            txtChapterContent = itemView.findViewById(R.id.txtChapterContent);
            txtChapterNameNumber = itemView.findViewById(R.id.txtChapterNameNumber);
        }
    }
}
