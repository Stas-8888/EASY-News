package com.example.newsapppp.presentation.ui.registration.forgotPassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.newsapppp.databinding.FragmentForgotPasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment : Fragment() {

    lateinit var binding: FragmentForgotPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgotPasswordBinding.inflate(layoutInflater)
        return binding.root
    }
}
