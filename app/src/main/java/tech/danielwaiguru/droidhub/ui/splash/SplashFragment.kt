package tech.danielwaiguru.droidhub.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import tech.danielwaiguru.droidhub.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //getCurrentSession()
        Handler(Looper.getMainLooper()).postDelayed({
            getCurrentSession()
        }, 1000)
        
    }
    private fun getCurrentSession() {
        val currentUser = Firebase.auth.currentUser
        if (currentUser == null) {
            startSignInUi()
        }else {
            startHomeUi()
        }
    }

    private fun startSignInUi() {
        findNavController().navigate(
                SplashFragmentDirections.actionSplashFragmentToSignInFragment()
        )
    }

    private fun startHomeUi() {
        findNavController().navigate(
                SplashFragmentDirections.actionSplashFragmentToHomeFragment()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}