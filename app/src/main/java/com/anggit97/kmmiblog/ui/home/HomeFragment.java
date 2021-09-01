package com.anggit97.kmmiblog.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.anggit97.kmmiblog.R;
import com.anggit97.kmmiblog.api.BlogClient;
import com.anggit97.kmmiblog.api.BlogServiceGenerator;
import com.anggit97.kmmiblog.api.model.Post;
import com.anggit97.kmmiblog.api.model.PostList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private PostAdapter postAdapter;
    private RecyclerView rvPost;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar pbLoading;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //initialize view
        rvPost = view.findViewById(R.id.rvNews);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        pbLoading = view.findViewById(R.id.pbLoading);

        //Initialize recyclerview and adapter
        postAdapter = new PostAdapter();
        rvPost.setAdapter(postAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvPost.setLayoutManager(layoutManager);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPost();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        getPost();
    }

    private void getPost() {
        showLoading();
        BlogClient client = BlogServiceGenerator.createService(BlogClient.class);
        client.getListPost().enqueue(new Callback<PostList>() {
            @Override
            public void onResponse(Call<PostList> call, Response<PostList> response) {
                hideLoading();
                if (response.isSuccessful()) {
                    postAdapter.setListPost(response.body().getData());
                } else {
                    Toast.makeText(getActivity(), "Fetch Data Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PostList> call, Throwable t) {
                hideLoading();
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showLoading(){
        pbLoading.setVisibility(View.VISIBLE);
    }

    private void hideLoading(){
        pbLoading.setVisibility(View.GONE);
    }
}