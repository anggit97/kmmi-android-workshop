package com.anggit97.kmmiblog.ui.news;

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
import com.anggit97.kmmiblog.api.model.PostList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends Fragment {

    private NewsAdapter adapter;
    private RecyclerView rvNews;
    private ProgressBar pbLoading;
    private FloatingActionButton fabCreate;
    private SwipeRefreshLayout swipeRefreshLayout;

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        rvNews = view.findViewById(R.id.rvNews);
        pbLoading = view.findViewById(R.id.pbLoading);
        fabCreate = view.findViewById(R.id.fabCreate);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerview();
        fetchData();
    }

    private void fetchData() {
        pbLoading.setVisibility(View.VISIBLE);
        BlogClient client = BlogServiceGenerator.createService(BlogClient.class);
        client.getListPost().enqueue(new Callback<PostList>() {
            @Override
            public void onResponse(Call<PostList> call, Response<PostList> response) {
                pbLoading.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    adapter.setPostList(response.body().getData());
                } else {
                    Toast.makeText(getContext(), "Gagal fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PostList> call, Throwable t) {
                pbLoading.setVisibility(View.GONE);
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initRecyclerview() {
        adapter = new NewsAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvNews.setLayoutManager(linearLayoutManager);
        rvNews.setAdapter(adapter);
    }
}