package com.example.jobis.presentation.signup.ui.signup

import android.content.Context
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.example.jobis.R
import com.example.jobis.databinding.FragmentSignupBinding
import com.example.jobis.presentation.login.UserActivity

class SignupFragment : Fragment() {
    private var userActivty: UserActivity? = null
    private lateinit var signupViewModel: SignupViewModel
    private var _binding: FragmentSignupBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signupViewModel = ViewModelProvider(this, SignupViewModelFactory())
            .get(SignupViewModel::class.java)

        val usernameEditText = binding.userEmail
        val passwordEditText = binding.userPassword
        val nicknameEditText = binding.userNickName
        val signupButton = binding.login
//        val loadingProgressBar = binding.loading
        signupViewModel.loginFormState.observe(viewLifecycleOwner,
            Observer { loginFormState ->
                if (loginFormState == null) {
                    return@Observer
                }
                signupButton.isEnabled = loginFormState.isDataValid
                loginFormState.usernameError?.let {
                    usernameEditText.error = getString(it)
                }
                loginFormState.passwordError?.let {
                    passwordEditText.error = getString(it)
                }
            })

        signupViewModel.loginResult.observe(viewLifecycleOwner,
            Observer { loginResult ->
                loginResult ?: return@Observer
                userActivty?.loadingOff()
//                loadingProgressBar.visibility = View.GONE
                loginResult.error?.let {
                    showSignupFailed(it)
                }
                loginResult.success?.let {
                    updateUiWithUser(it)
                }
            })

        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // ignore
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // ignore
            }

            override fun afterTextChanged(s: Editable) {
                signupViewModel.loginDataChanged(
                    usernameEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            }
        }
        usernameEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                signupViewModel.login(
                    usernameEditText.text.toString(),
                    nicknameEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            }
            false
        }

        signupButton.setOnClickListener {
            Log.d("test", "회원가입버튼 클릭 시 : ${userActivty}")
            userActivty?.loadingOn()
//            loadingProgressBar.visibility = View.VISIBLE
            signupViewModel.login(
                usernameEditText.text.toString(),
                nicknameEditText.text.toString(),
                passwordEditText.text.toString()
            )
        }

        // 뒤로가기 버튼
        binding.signupBackButton.setOnClickListener {
            userActivty?.goLogin()
        }
    }

    private fun updateUiWithUser(model: SignedUpUserView) {
        val welcome = model.displayName
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, welcome, Toast.LENGTH_LONG).show()
        userActivty?.goLogin()
    }

    private fun showSignupFailed(@StringRes errorString: Int) {
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is UserActivity ) {
            userActivty = context
        }
    }
}