package com.company.employeetracker.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val isAdmin: Boolean) : AuthState()
    data class Error(val message: String) : AuthState()
}

class FirebaseAuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun login(email: String, password: String) {
        _authState.value = AuthState.Loading

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    val uid = auth.currentUser?.uid ?: return@addOnCompleteListener

                    // 🔹 READ ROLE FROM REALTIME DATABASE
                    FirebaseDatabase.getInstance()
                        .getReference("users")
                        .child(uid)
                        .child("role")
                        .get()
                        .addOnSuccessListener { snapshot ->
                            val role = snapshot.getValue(String::class.java)

                            val isAdmin = role == "admin"
                            _authState.value = AuthState.Success(isAdmin)
                        }
                        .addOnFailureListener {
                            _authState.value = AuthState.Error("Failed to load user role")
                        }

                } else {
                    _authState.value = AuthState.Error(
                        task.exception?.localizedMessage ?: "Login failed"
                    )
                }
            }
    }


    fun logout() {
        auth.signOut()
        _authState.value = AuthState.Idle
    }
}
