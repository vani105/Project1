package com.example.project1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.project1.R;
import com.example.project1.adapters.OnboardingAdapter;
import com.example.project1.models.OnboardingItem;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class OnboardingActivity extends AppCompatActivity {

    private OnboardingAdapter onboardingAdapter;
    private ViewPager2 viewPager;
    private MaterialButton btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        setupOnboardingItems();

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(onboardingAdapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {}).attach();

        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(v -> {
            if (viewPager.getCurrentItem() + 1 < onboardingAdapter.getItemCount()) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            } else {
                navigateToLogin();
            }
        });

        findViewById(R.id.btnSkip).setOnClickListener(v -> navigateToLogin());

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == onboardingAdapter.getItemCount() - 1) {
                    btnNext.setText("Get Started");
                } else {
                    btnNext.setText("Next");
                }
            }
        });
    }

    private void setupOnboardingItems() {
        List<OnboardingItem> items = new ArrayList<>();

        items.add(new OnboardingItem(
                R.mipmap.ic_launcher_round,
                "Smart Tracking",
                "Monitor your investments with institutional-grade precision and real-time data."
        ));

        items.add(new OnboardingItem(
                R.mipmap.ic_launcher_round,
                "Inflation Adjusted",
                "See the real value of your portfolio after adjusting for current inflation rates."
        ));

        items.add(new OnboardingItem(
                R.mipmap.ic_launcher_round,
                "Safe & Secure",
                "Your financial data is protected by industry-leading security protocols."
        ));

        onboardingAdapter = new OnboardingAdapter(items);
    }

    private void navigateToLogin() {
        startActivity(new Intent(OnboardingActivity.this, LoginActivity.class));
        finish();
    }
}
