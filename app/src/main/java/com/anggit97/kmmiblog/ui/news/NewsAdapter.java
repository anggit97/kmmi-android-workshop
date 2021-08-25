package com.anggit97.kmmiblog.ui.news;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anggit97.kmmiblog.R;
import com.anggit97.kmmiblog.api.BlogServiceGenerator;
import com.anggit97.kmmiblog.api.model.Post;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anggit Prayogo on 25,August,2021
 * GitHub : https://github.com/anggit97
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.PostViewHolder> {

    private final List<Post> postList = new ArrayList<>();

    void setPostList(List<Post> postList) {
        this.postList.addAll(postList);
        this.notifyDataSetChanged();
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {

        private final ImageView ivThumbnail = itemView.findViewById(R.id.ivThumbnailNews);
        private final TextView tvTitle = itemView.findViewById(R.id.tvTitle);
        private final TextView tvBody = itemView.findViewById(R.id.tvBody);
        private final TextView tvDate = itemView.findViewById(R.id.tvDateNews);

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bindItem(Post post) {
            tvTitle.setText(post.getTitle());
            tvBody.setText(post.getBody());
            tvDate.setText(post.getCreatedAt());
            new BlogServiceGenerator();
            Glide.with(itemView.getContext())
                    .load(post.getThumbnailUrl().replace("localhost", BlogServiceGenerator.IP))
                    .into(ivThumbnail);
        }
    }

    @NonNull
    @Override
    public NewsAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_news, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.PostViewHolder holder, int position) {
        holder.bindItem(postList.get(position));
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }
}
