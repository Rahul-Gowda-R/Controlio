package com.company.employeetracker.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(

    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,

    var name: String = "",
    var email: String = "",
    var password: String = "",

    var role: String = "",            // admin / employee
    var designation: String = "",

    // 👇 fields your app already uses
    var department: String = "",
    var joiningDate: String = "",
    var contact: String = "",
    var profileImage: String = ""

) {
    // 🔑 REQUIRED for Firebase deserialization
    constructor() : this(
        0, "", "", "", "", "",
        "", "", "", ""
    )
}
