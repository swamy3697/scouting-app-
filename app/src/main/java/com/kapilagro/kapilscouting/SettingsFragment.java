package com.kapilagro.kapilscouting;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

public class SettingsFragment extends Fragment {

    private TextView profileName;
    private TextView profileRole;
    private MaterialButton editProfileButton;
    private MaterialButton logoutButton;
    private LinearLayout shareOption;
    private LinearLayout settingsOption;
    private LinearLayout helpSupportOption;
    private LinearLayout aboutCompanyOption;
    private ImageView btnBack;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Initialize views
        profileName = view.findViewById(R.id.profile_name);
        profileRole = view.findViewById(R.id.profile_role);
        editProfileButton = view.findViewById(R.id.edit_profile_button);
        logoutButton = view.findViewById(R.id.logout_button);
        shareOption = view.findViewById(R.id.share_option);
        settingsOption = view.findViewById(R.id.settings_option);
        helpSupportOption = view.findViewById(R.id.help_support_option);
        aboutCompanyOption = view.findViewById(R.id.about_company_option);




        // Set up click listeners
        editProfileButton.setOnClickListener(v -> {
            // Handle edit profile click
            Toast.makeText(getContext(), "Edit Profile clicked", Toast.LENGTH_SHORT).show();
            // Launch profile edit activity
            // Intent intent = new Intent(getActivity(), EditProfileActivity.class);
            // startActivity(intent);
        });

        shareOption.setOnClickListener(v -> {
            // Handle share option click
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Kapil Agro");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out Kapil Agro app: https://play.google.com/store/apps/details?id=com.kapilagro.kapilscouting");
            startActivity(Intent.createChooser(shareIntent, "Share via"));
        });

        settingsOption.setOnClickListener(v -> {
            // Handle settings option click
            Toast.makeText(getContext(), "Settings clicked", Toast.LENGTH_SHORT).show();
            // Launch app settings
            // Intent intent = new Intent(getActivity(), AppSettingsActivity.class);
            // startActivity(intent);
        });

        helpSupportOption.setOnClickListener(v -> {
            // Handle help and support option click
            Toast.makeText(getContext(), "Help and Support clicked", Toast.LENGTH_SHORT).show();
            // Launch help and support
            // Intent intent = new Intent(getActivity(), HelpSupportActivity.class);
            // startActivity(intent);
        });

        aboutCompanyOption.setOnClickListener(v -> {
            // Handle about company option click
            Toast.makeText(getContext(), "About Company clicked", Toast.LENGTH_SHORT).show();
            // Launch about company
            // Intent intent = new Intent(getActivity(), AboutCompanyActivity.class);
            // startActivity(intent);
        });

        logoutButton.setOnClickListener(v -> {
            // Handle logout click
            Toast.makeText(getContext(), "Logout clicked", Toast.LENGTH_SHORT).show();
            // Perform logout operations
            // Clear user session
            // Redirect to login screen
            // Intent intent = new Intent(getActivity(), LoginActivity.class);
            // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // startActivity(intent);
        });

        return view;
    }
}