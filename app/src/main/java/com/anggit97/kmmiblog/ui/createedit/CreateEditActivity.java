package com.anggit97.kmmiblog.ui.createedit;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.anggit97.kmmiblog.R;
import com.anggit97.kmmiblog.api.BlogClient;
import com.anggit97.kmmiblog.api.BlogServiceGenerator;
import com.anggit97.kmmiblog.api.model.CreatePostRequest;
import com.anggit97.kmmiblog.api.model.CreatePostResponse;
import com.anggit97.kmmiblog.api.model.EditPostRequest;
import com.anggit97.kmmiblog.api.model.EditPostResponse;
import com.anggit97.kmmiblog.api.model.Post;
import com.anggit97.kmmiblog.util.ImageBase64Converter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.textfield.TextInputEditText;

import java.io.FileNotFoundException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateEditActivity extends AppCompatActivity {

    private ImageView ivAddPhoto;
    private ImageView ivThumbnailNews;
    private ImageView ivBack;
    private TextInputEditText inputTitle;
    private TextInputEditText inputBody;
    private ProgressBar pbLoading;
    private Button btnSave;

    public static String POST_KEY = "POST_KEY";
    private static final int PICK_IMAGE = 100;
    public static String RESULT_CREATE_UPDATE_SUCCESS_KEY = "result_create_update_success_key";
    private boolean editMode = false;

    private Post post;
    private String imageBase64;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit);

        ivAddPhoto = findViewById(R.id.ivAddPhoto);
        ivThumbnailNews = findViewById(R.id.ivThumbnailNews);
        inputTitle = findViewById(R.id.inputTitle);
        inputBody = findViewById(R.id.inputBody);
        pbLoading = findViewById(R.id.pbLoading);
        btnSave = findViewById(R.id.btnSave);
        ivBack = findViewById(R.id.ivBack);

        handleIntent();

        pickImageFromGallery();

        saveListener();

        backListener();
    }

    private void backListener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToPreviousScreen(false);
            }
        });
    }

    private void handleIntent() {
        post = getIntent().getParcelableExtra(POST_KEY);
        if (post != null) {
            editMode = true;
            inputTitle.setText(post.getTitle());
            inputBody.setText(post.getBody());

            Glide.with(this)
                    .asBitmap()
                    .load(post.getThumbnailUrl())
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            ivThumbnailNews.setImageBitmap(resource);
                            imageBase64 = ImageBase64Converter.bitmapToBase64(CreateEditActivity.this, resource);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });
        }
    }

    private void saveListener() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });
    }

    private void validate() {
        if (imageBase64.isEmpty()) {
            Toast.makeText(this, "Image must be selected", Toast.LENGTH_SHORT).show();
        } else if (inputTitle.getText().toString().length() < 5) {
            Toast.makeText(this, "Title minimum 5 character long", Toast.LENGTH_SHORT).show();
        } else if (inputBody.getText().toString().length() < 10) {
            Toast.makeText(this, "Body minimum 10 character long", Toast.LENGTH_SHORT).show();
        } else {
            sendDataToServer();
        }
    }

    private void sendDataToServer() {
        if (editMode) {
            editPost();
        } else {
            createPost();
        }
    }

    private void editPost() {
        EditPostRequest request = new EditPostRequest();
        request.setImageBase64(imageBase64);
        request.setTitle(inputTitle.getText().toString());
        request.setBody(inputBody.getText().toString());

        showLoading();

        BlogClient client = BlogServiceGenerator.createService(BlogClient.class);
        client.editPost(request, String.valueOf(post.getId())).enqueue(new Callback<EditPostResponse>() {
            @Override
            public void onResponse(Call<EditPostResponse> call, Response<EditPostResponse> response) {
                hideLoading();
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    backToPreviousScreen(true);
                } else {
                    Toast.makeText(getApplicationContext(), "Failed send data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EditPostResponse> call, Throwable t) {
                hideLoading();
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createPost() {
        CreatePostRequest request = new CreatePostRequest();
        request.setImageBase64(imageBase64);
        request.setTitle(inputTitle.getText().toString());
        request.setBody(inputBody.getText().toString());

        showLoading();

        BlogClient client = BlogServiceGenerator.createService(BlogClient.class);
        client.createPostRequest(request).enqueue(new Callback<CreatePostResponse>() {
            @Override
            public void onResponse(Call<CreatePostResponse> call, Response<CreatePostResponse> response) {
                hideLoading();
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    backToPreviousScreen(true);
                } else {
                    Toast.makeText(getApplicationContext(), "Failed send data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CreatePostResponse> call, Throwable t) {
                hideLoading();
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void backToPreviousScreen(boolean success) {
        if (success){
            Intent intent = new Intent();
            intent.putExtra(RESULT_CREATE_UPDATE_SUCCESS_KEY, true);
            setResult(Activity.RESULT_OK, intent);
        }
        finish();
    }

    private void showLoading() {
        pbLoading.setVisibility(View.VISIBLE);
        btnSave.setEnabled(false);
    }

    private void hideLoading() {
        pbLoading.setVisibility(View.GONE);
        btnSave.setEnabled(true);
    }

    private void pickImageFromGallery() {
        ivAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Uri imageUri = data.getData();
            ivThumbnailNews.setImageURI(imageUri);
            try {
                imageBase64 = ImageBase64Converter.uriToBase64(this, imageUri);
            } catch (FileNotFoundException e) {
                Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}