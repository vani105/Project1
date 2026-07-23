package com.example.project1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project1.R;
import com.example.project1.models.User;
import com.example.project1.repository.AuthRepository;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {

    private TextInputEditText etFullName, etEmail, etMobile;
    private ProgressBar progressBar;
    private AuthRepository authRepository;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        authRepository = new AuthRepository();

        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etMobile = findViewById(R.id.etMobile);
        progressBar = findViewById(R.id.progressBar);

        loadUserData();

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
        findViewById(R.id.btnSave).setOnClickListener(v -> saveChanges());
        findViewById(R.id.btnLogout).setOnClickListener(v -> logout());
    }

    private void loadUserData() {
        progressBar.setVisibility(View.VISIBLE);
        authRepository.getUserProfile(new AuthRepository.UserCallback() {
            @Override
            public void onUserLoaded(User user) {
                currentUser = user;
                progressBar.setVisibility(View.GONE);
                etFullName.setText(user.getFullName());
                // Show real email for editing, or keep it masked if you prefer
                // But typically profile screens show the real data so you can check accuracy
                etEmail.setText(user.getEmail()); 
                etMobile.setText(user.getPhoneNumber());
            }

            @Override
            public void onFailure(String message) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ProfileActivity.this, "Failed to load: " + message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveChanges() {
        if (currentUser == null) return;

        String name = etFullName.getText().toString().trim();
        String mobile = etMobile.getText().toString().trim();

        if (name.isEmpty()) {
            etFullName.setError("Name cannot be empty");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        
        // Update Firestore surgically
        FirebaseFirestore.getInstance().collection("users")
                .document(currentUser.getUid())
                .update("fullName", name, "phoneNumber", mobile)
                .addOnSuccessListener(aVoid -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ProfileActivity.this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
                    // Optional: Return with result to refresh Kotlin side if needed
                })
                .addOnFailureListener(e -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ProfileActivity.this, "Update failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void logout() {
        authRepository.logout();
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
