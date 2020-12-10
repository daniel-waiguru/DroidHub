package tech.danielwaiguru.droidhub.ui.signin

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.launch
import tech.danielwaiguru.droidhub.R
import tech.danielwaiguru.droidhub.common.gone
import tech.danielwaiguru.droidhub.common.snackBar
import tech.danielwaiguru.droidhub.common.visible
import tech.danielwaiguru.droidhub.databinding.FragmentSignInBinding
import tech.danielwaiguru.droidhub.model.ResultWrapper

class SignInFragment : Fragment() {
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private val signInViewModel: SignInViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        with(binding){
            navToSignUp.setOnClickListener {
                startSignUpUi()
            }
            signInButton.setOnClickListener { signIn() }
        }
    }
    private fun signIn() {
        if (validInputs()){
            val email = binding.userEmail.text.toString()
            val password = binding.userPassword.text.toString()
            lifecycleScope.launch {
                binding.signInProgress.visible()
                val state = signInViewModel.userSignIn(email, password)
                handleState(state)
            }
        }
    }

    private fun handleState(state: ResultWrapper<AuthResult>) {
        when(state) {
            is ResultWrapper.Success -> {
                view?.snackBar(getString(R.string.login_success))
                binding.signInProgress.gone()
                startHomeUi()
            }
            is ResultWrapper.Failure -> {
                binding.root.snackBar(state.message.toString())
                binding.signInProgress.gone()
            }
        }
    }

    private fun startHomeUi() {
        val action = SignInFragmentDirections.actionSignInFragmentToHomeFragment()
        findNavController().navigate(action)
        //Navigation.findNavController(requireView()).popBackStack(R.id.signInFragment, true)
    }

    private fun validInputs(): Boolean {
        with(binding){
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
    private fun startSignUpUi() {
        val action = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
        findNavController().navigate(action)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}