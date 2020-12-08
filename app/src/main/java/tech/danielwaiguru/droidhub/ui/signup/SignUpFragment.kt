package tech.danielwaiguru.droidhub.ui.signup

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import tech.danielwaiguru.droidhub.R
import tech.danielwaiguru.droidhub.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        with(binding) {
            navToSignIn.setOnClickListener { startSignInUi() }
        }
    }
    private fun signUp() {
        if (showError()){
            with(binding){
                val name = userName.text.toString()
                val email = userEmail.text.toString()
                val password = userPassword.text.toString()
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