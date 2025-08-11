package com.example.passwordburn.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("passwords")
data class PasswordEntity(
    @Id
    val id: UUID? = null,

    val password: String,
    val accessed: Boolean = false
)