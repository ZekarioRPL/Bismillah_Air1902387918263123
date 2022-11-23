package com.example.bismillah_air.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bismillah_air.API.History;
import com.example.bismillah_air.HistoryActivity;
import com.example.bismillah_air.R;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>{

    List<History> postList;
    Context context;

    public HistoryAdapter(Context context, List<History> posts){
        this.context = context;
        postList = posts;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_item , parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        History post = postList.get(position);

//        holder.id.setText("id: " + post.getId());
//        holder.userId.setText("userId: " + post.getUserId());
//        holder.title.setText("title : " + post.getTitle());
//        holder.body.setText("body : " + post.getBody());

        holder.date.setText(post.getDate().toString());
        holder.debu_after.setText(post.getDebu_after());
        holder.debu_before.setText(post.getDebu_before());
        holder.waktu.setText(post.getTime());
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder{
        TextView date , waktu , debu_before, debu_after;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.date);
            waktu = itemView.findViewById(R.id.waktu);
            debu_before = itemView.findViewById(R.id.debu_before);
            debu_after = itemView.findViewById(R.id.debu_after);

//            body = itemView.findViewById(R.id.body_tv);
        }
    }

}
