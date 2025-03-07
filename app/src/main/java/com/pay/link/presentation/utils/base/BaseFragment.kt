package com.pay.link.presentation.utils.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import kotlin.reflect.KClass

open class BaseFragment<VB : ViewBinding, VM : ViewModel>(
    private val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB,
    private val viewModelClass: KClass<VM>
) : Fragment() {

    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding ?: throw IllegalStateException("ViewBinding is only available between onCreateView and onDestroyView")

    protected val viewModel: VM by lazy {
        ViewModelProvider(this)[viewModelClass.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater(inflater, container, false)
        Log.d("BaseFragment", "onCreateView: Binding initialized.")
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d("BaseFragment", "onDestroyView: Binding cleared.")
    }

    protected inline fun <reified VM : ViewModel> getSharedViewModel(): VM {
        return ViewModelProvider(requireActivity())[VM::class.java]
    }
}
