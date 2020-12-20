package tech.danielwaiguru.droidhub.ui.signup

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.launch
import tech.danielwaiguru.droidhub.R
import tech.danielwaiguru.droidhub.common.gone
import tech.danielwaiguru.droidhub.common.toast
import tech.danielwaiguru.droidhub.common.visible
import tech.danielwaiguru.droidhub.databinding.FragmentSignUpBinding
import tech.danielwaiguru.droidhub.model.ResultWrapper
import tech.danielwaiguru.droidhub.model.User
import tech.danielwaiguru.droidhub.repository.MainRepositoryImpl
import tech.danielwaiguru.droidhub.ui.viewmodel.MainViewModelFactory

class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val signUpViewModel: SignUpViewModel by viewModels {
        MainViewModelFactory(MainRepositoryImpl())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribers()
        initListeners()
    }

    private fun initListeners() {
        with(binding) {
            navToSignIn.setOnClickListener { startSignInUi() }
            signUpButton.setOnClickListener { userSignUp() }
        }
    }
    private fun subscribers() {
        signUpViewModel.message.observe(viewLifecycleOwner, {
            Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
        })
        signUpViewModel.loading.observe(viewLifecycleOwner, {loading ->
            if (loading){
                binding.signUpProgress.visible()
            }
            else
            {
                binding.signUpProgress.gone()
            }
        })
    }
    private fun userSignUp(){
        if (showError()) {
            with(binding){
                val name = userName.text.toString()
                val email = userEmail.text.toString()
                val password = userPassword.text.toString()
                val user = User(name, email)
                lifecycleScope.launch {
                    binding.signUpProgress.visible()
                    val state = signUpViewModel.userSignUp(user, password)
                    handleState(state)
                }
            }
        }
    }
    private fun handleState(state: ResultWrapper<AuthResult>){
        when (state) {
            is ResultWrapper.Success -> {
                binding.signUpProgress.gone()
                binding.root.toast("User registered successfully!")
                startHomeUi()
            }
            is  ResultWrapper.Failure -> {
                binding.signUpProgress.gone()
                binding.root.toast(state.message.toString())
            }

        }
    }

    private fun startHomeUi() {
        val action = SignUpFragmentDirections.actionSignUpFragmentToHomeFragment()
        findNavController().navigate(action)
    }

    private fun signUp() {
        if (showError()){
            with(binding){
                val name = userName.text.toString()
                val email = userEmail.text.toString()
                val password = userPassword.text.toString()
                val user = User(name, email)
                signUpViewModel.signUp(user, password)
            }
        }
    }
    private fun showError(): Boolean {
        with(binding){
            if (userName.text.isEmpty()){
                userName.error = getString(R.string.name_error)
                userName.requestFocus()
                return false
            }
            val isValidEmail = Patterns.EMAIL_ADDRESS.matcher(userEmail.text.toString()).matches()
            if (!isValidEmail) {
                userEmail.error = getString(R.string.email_error)
                userEmail.requestFocus()
                return false
            }
            userPassword.text?.length?.let {
                if (it < 6){
                    userPassword.error = getString(R.string.password_error)
                    userPassword.requestFocus()
                    return false
                }
            }
        }
        return true
    }
    private fun startSignInUi() {
        findNavController().navigate(
            SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}