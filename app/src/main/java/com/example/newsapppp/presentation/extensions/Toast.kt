package com.example.newsapppp.presentation.extensions

import android.widget.Toast
import androidx.fragment.app.Fragment


fun Fragment.toast(name: String){
    Toast.makeText(requireContext(), name, Toast.LENGTH_SHORT).show()
}
