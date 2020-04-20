package com.example.csquaretest.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.csquaretest.R;
import com.example.csquaretest.model.UserList;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder> {

    private List<UserList> userLists = new ArrayList<>();
    private OnItemClickListener listener;
    public ListViewAdapter(List<UserList> userLists) {
        this.userLists = userLists;
    }
    public ListViewAdapter() { }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserList list = userLists.get(position);
        holder.title.setText(list.getEmail());
    }

    public void setPost(List<UserList> userList1) {
        this.userLists = userList1;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return userLists != null ? userLists.size() : 0 ;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null) {
                        listener.onItemClick(userLists.get(position));
                    }
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(UserList userList);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
