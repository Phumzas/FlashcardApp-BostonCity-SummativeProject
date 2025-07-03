package com.example.androidsummative1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<String> title_id, description_id;
    private OnItemClickListener listener;

    public MyAdapter(Context context, ArrayList<String> title_id, ArrayList<String> description_id) {
        this.context = context;
        this.title_id = title_id;
        this.description_id = description_id;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.userentry, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title_id.setText(title_id.get(position));
        holder.description_id.setText(description_id.get(position));
    }

    @Override
    public int getItemCount() {
        return title_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title_id, description_id;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title_id = itemView.findViewById(R.id.texttitle);
            description_id = itemView.findViewById(R.id.textdescription);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
