package com.example.caravanasfutebol.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _isLoggedIn = MutableStateFlow(auth.currentUser != null)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    private val _authError = MutableStateFlow<String?>(null)
    val authError: StateFlow<String?> = _authError

    init {
        // Escuta mudanças de estado da autenticação (salva sessão de login)
        auth.addAuthStateListener { firebaseAuth ->
            _isLoggedIn.value = firebaseAuth.currentUser != null
        }
    }

    fun login(email: String, pass: String) {
        if (email.isBlank() || pass.isBlank()) {
            _authError.value = "Preencha e-mail e senha."
            return
        }
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _isLoggedIn.value = true
                    _authError.value = null
                } else {
                    _authError.value = task.exception?.localizedMessage ?: "Erro ao entrar."
                }
            }
    }

    fun cadastrar(email: String, pass: String) {
        if (email.isBlank() || pass.isBlank()) {
            _authError.value = "Preencha e-mail e senha."
            return
        }
        if (pass.length < 6) {
            _authError.value = "A senha deve ter no mínimo 6 caracteres."
            return
        }

        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid ?: ""
                    val userMap = mapOf(
                        "email" to email,
                        "createdAt" to System.currentTimeMillis()
                    )
                    db.collection("users").document(uid).set(userMap)
                        .addOnCompleteListener {
                            _isLoggedIn.value = true
                            _authError.value = null
                        }
                } else {
                    _authError.value = task.exception?.localizedMessage ?: "Erro ao cadastrar."
                }
            }
    }

    fun logout() {
        auth.signOut()
        _isLoggedIn.value = false
    }
}