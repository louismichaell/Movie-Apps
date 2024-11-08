package com.dicoding.movieexpert.ui.settings

import com.dicoding.core.utils.DATASTORE_NAME
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dicoding.movieexpert.databinding.FragmentSettingsBinding
import kotlin.system.exitProcess

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)
    private val binding get() = _binding!!

    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val pref = SettingPreferences.getInstance(requireContext().dataStore)
        val factory = SettingViewModelFactory(pref)
        settingsViewModel = ViewModelProvider(this, factory)[SettingsViewModel::class.java]

        val switchTheme = binding.switchTheme

        settingsViewModel.getThemeSettings()
            .observe(viewLifecycleOwner) { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    switchTheme.isChecked = true
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    switchTheme.isChecked = false
                }
            }
        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            settingsViewModel.saveThemeSetting(isChecked)
        }

        binding.btnLogout.setOnClickListener {
            requireActivity().finish()
            exitProcess(0)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}