package com.pay.link.data.repositories.auth

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(private val firebaseAuth: FirebaseAuth) {

    suspend fun loginUser(email: String, password: String) {
        try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun registerUser(email: String, password: String) {
        try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        } catch (e: Exception) {
            throw e
        }
    }

    fun logoutUser() {
        try {
            firebaseAuth.signOut()
        } catch (e: Exception) {
            throw e
        }
    }

    fun getCurrentUser() = firebaseAuth.currentUser

}

