package com.softeer.cartalog.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.softeer.cartalog.R
import com.softeer.cartalog.data.local.MyCarDatabase
import com.softeer.cartalog.data.remote.api.RetrofitClient
import com.softeer.cartalog.data.repository.CarRepositoryImpl
import com.softeer.cartalog.data.repository.local.CarLocalDataSource
import com.softeer.cartalog.data.repository.remote.CarRemoteDataSource
import com.softeer.cartalog.databinding.FragmentTypeBinding
import com.softeer.cartalog.ui.activity.MainActivity
import com.softeer.cartalog.viewmodel.CommonViewModelFactory
import com.softeer.cartalog.viewmodel.TypeViewModel

class TypeFragment : Fragment() {
    private var _binding: FragmentTypeBinding? = null
    private val binding get() = _binding!!

    private val carRepositoryImpl by lazy {
        CarRepositoryImpl(
            CarLocalDataSource(MyCarDatabase.getInstance(requireContext())!!),
            CarRemoteDataSource(RetrofitClient.carApi)
        )
    }
    val typeViewModel: TypeViewModel by viewModels {
        CommonViewModelFactory(carRepositoryImpl)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTypeBinding.inflate(inflater, container, false)
        typeViewModel.navController.value = findNavController()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = typeViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.btnNext.setOnClickListener {
            (activity as MainActivity).changeTab(2)
        }
        binding.btnPrev.setOnClickListener {
            (activity as MainActivity).changeTab(0)
        }
        binding.btnPriceSummary.setOnClickListener {
            findNavController().navigate(R.id.action_typeFragment_to_priceSummaryBottomSheetFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}