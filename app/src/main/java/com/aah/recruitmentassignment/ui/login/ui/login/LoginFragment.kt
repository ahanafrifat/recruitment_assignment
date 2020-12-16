package com.aah.recruitmentassignment.ui.login.ui.login

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.aah.recruitmentassignment.R
import com.aah.recruitmentassignment.databinding.FragmentLoginBinding
import com.aah.recruitmentassignment.models.AuthModel
import com.aah.recruitmentassignment.utils.AUTH_MODEL
import com.aah.recruitmentassignment.utils.AppUtils

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel

    //    private lateinit var binding : FragmentLoginBinding
//    private lateinit var binding : LoginFragmentBinding
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController

    private lateinit var authModel:AuthModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.fragment_login, container, false)
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(binding.root)

        viewModel.setSuccessFull(false)
        initLoading()
        initMessage()
        initSuccessful()
        binding.login.setOnClickListener {
            viewModel.login(binding.username.text.toString(), binding.password.text.toString())
        }

        viewModel.authLiveData.observe(viewLifecycleOwner, Observer {
            if(it != null){
                authModel = it
//                gotoMainFragment(it)
            }
        })
    }

    private fun initSuccessful() {
        viewModel.isSuccessFull.observe(viewLifecycleOwner, Observer {isSuccessFull->
            isSuccessFull?.let {
                if(it){
                    gotoMainFragment(authModel)
                }
            }
        })
    }

    private fun initMessage() {
        viewModel.message.observe(viewLifecycleOwner, Observer {
            AppUtils.message(binding.root, it)
        })
    }

    private fun initLoading() {
        viewModel.loading.observe(viewLifecycleOwner, Observer {loading->
            loading?.let {
                if(it){
                    binding.group.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }
                else{
                    binding.group.visibility = View.VISIBLE
                    binding.progressBar.visibility =View.GONE
                }
            }
        })
    }

    private fun gotoMainFragment(authModel: AuthModel) {
        val bundle = bundleOf(AUTH_MODEL to authModel)
        navController.navigate(R.id.action_loginFragment_to_mainFragment, bundle)
    }

}