package com.anggit97.kmmiblog.ui.news;

import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.anggit97.kmmiblog.R;
import com.anggit97.kmmiblog.api.BlogServiceGenerator;
import com.anggit97.kmmiblog.api.model.Post;
import com.anggit97.kmmiblog.ui.createedit.CreateEditActivity;
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
        this.postList.clear();
        this.postList.addAll(postList);
        this.notifyDataSetChanged();
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {

        private final ImageView ivThumbnail = itemView.findViewById(R.id.ivThumbnailNews);
        private final TextView tvTitle = itemView.findViewById(R.id.tvTitle);
        private final TextView tvBody = itemView.findViewById(R.id.tvBody);
        private final TextView tvDate = itemView.findViewById(R.id.tvDateNews);
        private final ImageView ivManage = itemView.findViewById(R.id.ivManage);

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bindItem(Post post) {
            tvTitle.setText(post.getTitle());
            tvBody.setText(post.getBody());
            tvDate.setText(post.getCreatedAt());
            Glide.with(itemView.getContext())
                    .load(post.getThumbnailUrl().replace("localhost", BlogServiceGenerator.IP))
                    .into(ivThumbnail);

            ivManage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(ivManage.getContext(), ivManage);

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch(item.getItemId()){
                                case R.id.action_edit:
                                    Intent intent = new Intent(itemView.getContext(), CreateEditActivity.class);
                                    itemView.getContext().startActivity(intent);
                                    return true;
                                case R.id.action_delete:
                                    //TODO::SHOW POPUP TO DELETE
                                    return true;
                                default:
                                    return false;
                            }
                        }
                    });

                    //inflate your menu
                    popupMenu.inflate(R.menu.my_news_list_menu);
                    popupMenu.setGravity(Gravity.RIGHT);

                    popupMenu.show();
                }
            });
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
