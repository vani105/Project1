package com.example.project1.data

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

data class UserProfile(
    val uid: String = "",
    val fullName: String = "Invest Manager",
    val email: String = "",
    val phoneNumber: String = "",
    val createdAt: Long = 0,
    val isPro: Boolean = true,
    val currency: String = "Indian Rupee",
    val region: String = "India",
    val language: String = "English (India)",
    val priceAlerts: Boolean = true,
    val volAlerts: Boolean = false
)

data class UserHolding(
    val id: String = "",
    val name: String = "",
    val type: String = "",
    val value: String = "0",
    val change: String = "0"
)

data class HistoryItem(
    val id: String = "",
    val title: String = "",
    val date: String = "",
    val duration: String = "",
    val realValue: String = "",
    val nominalValue: String = "",
    val type: String = ""
)

object FirebaseManager {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun signUp(fullName: String, email: String, phoneNumber: String, password: String): Result<Unit> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user
            if (user != null) {
                // Send verification email
                user.sendEmailVerification().await()
                
                val userData = UserProfile(
                    uid = user.uid,
                    fullName = fullName,
                    email = email,
                    phoneNumber = phoneNumber,
                    createdAt = System.currentTimeMillis(),
                    isPro = true
                )
                firestore.collection("users").document(user.uid).set(userData).await()
                Result.success(Unit)
            } else {
                Result.failure(Exception("User creation failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun sendPasswordResetEmail(email: String): Result<Unit> {
        return try {
            auth.sendPasswordResetEmail(email).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val user = result.user
            if (user != null && !user.isEmailVerified) {
                Result.failure(Exception("Please verify your email address before logging in. Check your inbox for the verification link."))
            } else {
                Result.success(Unit)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun logout() {
        auth.signOut()
    }

    suspend fun getUserProfile(): UserProfile? {
        val uid = auth.currentUser?.uid ?: return null
        return try {
            val doc = firestore.collection("users").document(uid).get().await()
            doc.toObject<UserProfile>()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun saveUserProfile(profile: UserProfile): Result<Unit> {
        val uid = auth.currentUser?.uid ?: return Result.failure(Exception("Not logged in"))
        return try {
            firestore.collection("users").document(uid).set(profile).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getHoldings(): List<UserHolding> {
        val uid = auth.currentUser?.uid ?: return emptyList()
        return try {
            val snapshot = firestore.collection("users").document(uid).collection("holdings").get().await()
            snapshot.documents.mapNotNull { it.toObject<UserHolding>()?.copy(id = it.id) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun saveHolding(holding: UserHolding): Result<Unit> {
        val uid = auth.currentUser?.uid ?: return Result.failure(Exception("Not logged in"))
        return try {
            if (holding.id.isEmpty()) {
                firestore.collection("users").document(uid).collection("holdings").add(holding).await()
            } else {
                firestore.collection("users").document(uid).collection("holdings").document(holding.id).set(holding).await()
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getHistory(): List<HistoryItem> {
        val uid = auth.currentUser?.uid ?: return emptyList()
        return try {
            val snapshot = firestore.collection("users").document(uid).collection("history")
                .orderBy("date", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .get().await()
            snapshot.documents.mapNotNull { it.toObject<HistoryItem>()?.copy(id = it.id) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun saveHistoryItem(item: HistoryItem): Result<Unit> {
        val uid = auth.currentUser?.uid ?: return Result.failure(Exception("Not logged in"))
        return try {
            firestore.collection("users").document(uid).collection("history").add(item).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteHistoryItem(id: String): Result<Unit> {
        val uid = auth.currentUser?.uid ?: return Result.failure(Exception("Not logged in"))
        return try {
            firestore.collection("users").document(uid).collection("history").document(id).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updatePassword(newPassword: String, currentPassword: String = ""): Result<Unit> {
        val user = auth.currentUser ?: return Result.failure(Exception("Not logged in"))
        return try {
            if (currentPassword.isNotEmpty()) {
                val email = user.email ?: return Result.failure(Exception("User email not found"))
                val credential = EmailAuthProvider.getCredential(email, currentPassword)
                user.reauthenticate(credential).await()
            }
            user.updatePassword(newPassword).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getCurrentUser() = auth.currentUser

    suspend fun sendEmailVerification(): Result<Unit> {
        val user = auth.currentUser ?: return Result.failure(Exception("Not logged in"))
        return try {
            user.sendEmailVerification().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
