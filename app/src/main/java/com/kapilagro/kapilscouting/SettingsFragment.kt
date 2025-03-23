package com.kapilagro.kapilscouting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton

class SettingsFragment : Fragment() {
    private var profileName: TextView? = null
    private var profileRole: TextView? = null
    private var editProfileButton: MaterialButton? = null
    private var logoutButton: MaterialButton? = null
    private var shareOption: LinearLayout? = null
    private var settingsOption: LinearLayout? = null
    private var helpSupportOption: LinearLayout? = null
    private var aboutCompanyOption: LinearLayout? = null
    private val btnBack: ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        // Initialize views
        profileName = view.findViewById<TextView?>(R.id.profile_name)
        profileRole = view.findViewById<TextView?>(R.id.profile_role)
        editProfileButton = view.findViewById<MaterialButton>(R.id.edit_profile_button)
        logoutButton = view.findViewById<MaterialButton>(R.id.logout_button)
        shareOption = view.findViewById<LinearLayout>(R.id.share_option)
        settingsOption = view.findViewById<LinearLayout>(R.id.settings_option)
        helpSupportOption = view.findViewById<LinearLayout>(R.id.help_support_option)
        aboutCompanyOption = view.findViewById<LinearLayout>(R.id.about_company_option)


        // Set up click listeners
        editProfileButton!!.setOnClickListener(View.OnClickListener { v: View? ->
            // Handle edit profile click
            Toast.makeText(getContext(), "Edit Profile clicked", Toast.LENGTH_SHORT).show()
        })

        shareOption!!.setOnClickListener(View.OnClickListener { v: View? ->
            // Handle share option click
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.setType("text/plain")
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Kapil Agro")
            shareIntent.putExtra(
                Intent.EXTRA_TEXT,
                "Check out Kapil Agro app: https://play.google.com/store/apps/details?id=com.kapilagro.kapilscouting"
            )
            startActivity(Intent.createChooser(shareIntent, "Share via"))
        })

        settingsOption!!.setOnClickListener(View.OnClickListener { v: View? ->
            // Handle settings option click
            Toast.makeText(getContext(), "Settings clicked", Toast.LENGTH_SHORT).show()
        })

        helpSupportOption!!.setOnClickListener(View.OnClickListener { v: View? ->
            // Handle help and support option click
            Toast.makeText(getContext(), "Help and Support clicked", Toast.LENGTH_SHORT).show()
        })

        aboutCompanyOption!!.setOnClickListener(View.OnClickListener { v: View? ->
            // Handle about company option click
            Toast.makeText(getContext(), "About Company clicked", Toast.LENGTH_SHORT).show()
        })

        logoutButton!!.setOnClickListener(View.OnClickListener { v: View? ->
            // Handle logout click
            Toast.makeText(getContext(), "Logout clicked", Toast.LENGTH_SHORT).show()
        })

        return view
    }
}