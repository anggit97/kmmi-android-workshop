package com.anggit97.kmmiblog.ui.main;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.anggit97.kmmiblog.R;
import com.anggit97.kmmiblog.ui.favorite.FavoriteFragment;
import com.anggit97.kmmiblog.ui.home.HomeFragment;
import com.anggit97.kmmiblog.ui.news.NewsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    private ViewPager viewPager;

    HomeFragment homeFragment;
    NewsFragment newsFragment;
    FavoriteFragment favoriteFragment;

    MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize view
        viewPager = findViewById(R.id.mainViewPager);
        bottomNavigationView = findViewById(R.id.mainBottomNavMenu);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.action_news:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.action_favorite:
                        viewPager.setCurrentItem(2);
                        break;
                }
                return false;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager());
        homeFragment = HomeFragment.newInstance();
        newsFragment = NewsFragment.newInstance();
        favoriteFragment = FavoriteFragment.newInstance();

        adapter.addFragment(homeFragment);
        adapter.addFragment(newsFragment);
        adapter.addFragment(favoriteFragment);

        viewPager.setAdapter(adapter);
    }
}