package com.example.musicapp.ui;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.musicapp.R;
import com.example.musicapp.ui.favorites.FavoritesFragment;
import com.example.musicapp.ui.home.HomeFragment;
import com.example.musicapp.ui.search.SearchFragment;
import com.example.musicapp.ui.top.TopFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Fragment homeFragment = new HomeFragment();
    Fragment topFragment = new TopFragment();
    Fragment favoritesFragment = new FavoritesFragment();
    Fragment searchFragment = new SearchFragment();
    Fragment active = homeFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        // Apply insets to fragment container only, not the entire layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.fragment_container), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        // Apply bottom inset to bottom navigation to handle navigation bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.bottom_navigation), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, 0, 0, systemBars.bottom);
            return insets;
        });


        bottomNavigationView = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, searchFragment, "4").hide(searchFragment)
                .add(R.id.fragment_container, favoritesFragment, "3").hide(favoritesFragment)
                .add(R.id.fragment_container, topFragment, "2").hide(topFragment)
                .add(R.id.fragment_container, homeFragment, "1")
                .commit();

        bottomNavigationView.setOnItemSelectedListener(item -> {

            int itemId = item.getItemId();

            if(itemId == R.id.navigation_home) {
                switchFragment(homeFragment);
                return true;
            } else if (itemId == R.id.navigation_top) {
                switchFragment(topFragment);
                return true;
            } else if (itemId == R.id.navigation_favorites) {
                switchFragment(favoritesFragment);
                return true;
            } else if (itemId == R.id.navigation_search) {
                switchFragment(searchFragment);
                return true;
            }
            return false;
        });
    }


    private void switchFragment(Fragment target) {
        getSupportFragmentManager().beginTransaction()
                .hide(active)
                .show(target)
                .addToBackStack(null)
                .commit();
        active = target;
    }
}