package com.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saymyname.R;
import com.types.RecordingMessage;

public class RecordingMessageTypeAdapter extends RecyclerView.Adapter<com.adapters.RecordingMessageTypeAdapter.RecordingMessageTypeViewHolder> {

    private RecordingMessage[] allTypes;
    private OnItemListClick onItemListClick;
    //private boolean isSelected = false;

    public RecordingMessageTypeAdapter(RecordingMessage[] alltypes, OnItemListClick onItemListClick) {
        this.allTypes = alltypes;
        this.onItemListClick = onItemListClick;
    }

    @NonNull
    @Override
    public RecordingMessageTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_type, parent, false);
        return new RecordingMessageTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordingMessageTypeViewHolder holder, int position) {
        holder.list_title.setText(allTypes[position].getName());
        holder.list_message.setText(allTypes[position].getMessage());
    }

    @Override
    public int getItemCount() {
        return allTypes.length;
    }

    public class RecordingMessageTypeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView list_image;
        private TextView list_title;
        private TextView list_message;

        public RecordingMessageTypeViewHolder(@NonNull View itemView) {
            super(itemView);

            list_image = itemView.findViewById(R.id.list_image_view);
            list_title = itemView.findViewById(R.id.list_title);
            list_message = itemView.findViewById(R.id.list_date);

            //checkBox.setOnClickListener(this);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemListClick.onClickListener(allTypes[getAdapterPosition()], getAdapterPosition());
        }
    }

    public interface OnItemListClick {
        void onClickListener(RecordingMessage messageType, int position);
    }
}
