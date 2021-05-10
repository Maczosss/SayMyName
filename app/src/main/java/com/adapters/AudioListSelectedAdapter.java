package com.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saymyname.R;
import com.types.TimeAgo;

import java.io.File;

public class AudioListSelectedAdapter extends RecyclerView.Adapter<AudioListSelectedAdapter.AudioListSelectedViewHolder> {

    private File[] allFiles;
    private TimeAgo timeAgo;
    private OnItemListClick onItemListClick;
    //private Boolean isSelected = false;

    public AudioListSelectedAdapter(File[] allFiles, OnItemListClick onItemListClick) {
        this.allFiles = allFiles;
        this.onItemListClick = onItemListClick;
    }

    @NonNull
    @Override
    public AudioListSelectedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_transaction_list_item, parent, false);
        timeAgo = new TimeAgo();
        return new AudioListSelectedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AudioListSelectedViewHolder holder, int position) {
        holder.list_title.setText(allFiles[position].getName());
        holder.list_date.setText(timeAgo.getTimeAgo(allFiles[position].lastModified()));
    }

    @Override
    public int getItemCount() {
        return allFiles.length;
    }

    public class AudioListSelectedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView list_title;
        private TextView list_date;
        private CheckBox selector;

        public AudioListSelectedViewHolder(@NonNull View itemView) {
            super(itemView);

            list_title = itemView.findViewById(R.id.transaction_list_title);
            list_date = itemView.findViewById(R.id.transaction_list_date);

            selector = itemView.findViewById(R.id.transaction_item_checkbox);
            selector.setOnClickListener(this);

            //itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemListClick.onClickListener(allFiles[getAdapterPosition()], getAdapterPosition());
        }
    }

    public interface OnItemListClick {
        void onClickListener(File file, int position);
    }
}
