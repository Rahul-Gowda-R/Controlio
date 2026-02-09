package com.company.employeetracker.data.firebase

import com.company.employeetracker.data.database.entities.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class FirebaseUserRepository {

    private val db =
        FirebaseDatabase.getInstance().getReference("users")

    fun fetchUser(uid: String, onResult: (User?) -> Unit) {
        db.child(uid)
            .get()
            .addOnSuccessListener { snapshot ->
                onResult(snapshot.getValue(User::class.java))
            }
            .addOnFailureListener {
                onResult(null)
            }
    }
}

