package com.gumis.recipeholder

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.gumis.recipeholder.data.User
import com.gumis.recipeholder.databinding.FragmentRegisterBinding
import com.gumis.recipeholder.viewModels.RegisterViewModel
import com.gumis.recipeholder.viewModels.RegisterViewModelFactory
import com.gumis.recipeholder.viewModels.ValidationCheck
import kotlinx.coroutines.launch


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var registerViewModel: RegisterViewModel
//    private val registerViewModel: RegisterViewModel by viewModels {
//        RegisterViewModelFactory((activity?.application as RecipeHolderApplication).recipesRepository)
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerViewModel = ViewModelProvider(this,
            RegisterViewModelFactory((activity?.application as RecipeHolderApplication).recipesRepository)
        ).get(RegisterViewModel::class.java)

        val registerButton = binding.registerRegisterButton
        val emailEditText = binding.registerEmailEdit
        val passwordEditText = binding.registerPasswordEdit
        val confirmPasswordEditText = binding.registerConfirmPasswordEdit
        val progressionBar = binding.progressBar2

        var emailOk = false
        var passwordsOk = false

        //TODO: Jedziemy dalej

        val emailValidationListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // ignore
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // ignore
            }
            override fun afterTextChanged(s: Editable?) {
                val isEmailCorrect = ValidationCheck.isEmailValid(emailEditText.text.toString())
                emailOk = isEmailCorrect
            }
        }

        val passwordsValidationListener = object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // ignore
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // ignore
            }
            override fun afterTextChanged(s: Editable?) {
                val password1 = passwordEditText.text.toString()
                val password2 = confirmPasswordEditText.text.toString()

                val isPasswordCorrect = ValidationCheck.isPasswordValid(password1)
                println("password >= 5: $isPasswordCorrect")
                if (isPasswordCorrect) {
                    val arePasswordsSame = ValidationCheck.arePasswordsSame(password1, password2)
                    passwordsOk = arePasswordsSame
                    if (emailOk) {
                        registerButton.isEnabled = passwordsOk
                    }
                }
                println("Email is ok: $emailOk")
                println("Passwords are ok: $passwordsOk")
            }
        }

        emailEditText.addTextChangedListener(emailValidationListener)
        passwordEditText.addTextChangedListener(passwordsValidationListener)
        confirmPasswordEditText.addTextChangedListener(passwordsValidationListener)

        registerButton.setOnClickListener {
            val newUser = User(emailEditText.text.toString(), passwordEditText.text.toString(), null)
            registerButton.visibility = View.GONE
            progressionBar.visibility = View.VISIBLE
            lifecycleScope.launch {
                val success = registerViewModel.register(newUser, requireActivity().applicationContext)
                if (success) {
                    findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
                }
                else {
                    progressionBar.visibility = View.GONE
                    registerButton.visibility = View.VISIBLE
                    passwordEditText.text.clear()
                    confirmPasswordEditText.text.clear()
                    registerButton.isEnabled = false
                }
            }
        }

    }

}