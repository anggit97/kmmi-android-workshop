package com.anggit97.kmmiblog.ui.detail;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.anggit97.kmmiblog.R;
import com.anggit97.kmmiblog.api.model.Post;
import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    public static String DATA_EXTRA_KEY = "data_extra_key";

    private Post post;

    private TextView tvTitleNews;
    private TextView tvBody;
    private ImageView ivThumbnailNews;
    private ImageView ivBack;
    private ImageView ivFavorite;
    private TextView tvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvTitleNews = findViewById(R.id.tvTitleNews);
        tvBody = findViewById(R.id.tvBody);
        ivThumbnailNews = findViewById(R.id.ivThumbnailNews);
        tvDate = findViewById(R.id.tvDateNews);
        ivBack = findViewById(R.id.ivBack);
        ivFavorite = findViewById(R.id.ivFavorite);

        handleIntent();

        onActionClick();
    }

    private void onActionClick() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void handleIntent() {
        post = getIntent().getParcelableExtra(DATA_EXTRA_KEY);
        if (post != null) {
            tvTitleNews.setText(post.getTitle());
            tvDate.setText(post.getCreatedAt());
            tvBody.setText(post.getBody());
            Glide.with(this)
                    .load(post.getThumbnailUrl())
                    .into(ivThumbnailNews);

            if (post.isFavorite()) {
                ivFavorite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_24));
            } else {
                ivFavorite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_border_24));
            }
        }
    }
}