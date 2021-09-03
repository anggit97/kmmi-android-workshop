package com.anggit97.kmmiblog.ui.favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.anggit97.kmmiblog.R;
import com.anggit97.kmmiblog.db.BlogDatabase;
import com.anggit97.kmmiblog.db.entity.PostEntity;
import com.anggit97.kmmiblog.util.AppExecutors;

import java.util.List;

public class FavoriteFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rvFavorite;
    private ProgressBar pbLoading;
    private TextView tvFavoriteEmpty;

    private BlogDatabase db;

    private FavoriteAdapter adapter;

    public static FavoriteFragment newInstance() {
        return new FavoriteFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        rvFavorite = view.findViewById(R.id.rvFavorite);
        pbLoading = view.findViewById(R.id.pbLoading);
        tvFavoriteEmpty = view.findViewById(R.id.tvFavoriteEmpty);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new FavoriteAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvFavorite.setLayoutManager(linearLayoutManager);
        rvFavorite.setAdapter(adapter);

        db = BlogDatabase.getInstance(getContext());

        onActionListener();
    }

    private void onActionListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchData();
    }

    private void fetchData() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<PostEntity> postList = db.postDao().getFavoritePost();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setListPost(postList);
                        if (postList.isEmpty()){
                            tvFavoriteEmpty.setVisibility(View.VISIBLE);
                            rvFavorite.setVisibility(View.GONE);
                        }else{
                            tvFavoriteEmpty.setVisibility(View.GONE);
                            rvFavorite.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });
    }
}