package com.example.passwordburn.repository

import com.example.passwordburn.model.PasswordEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.util.*

interface PasswordRepository : CoroutineCrudRepository<PasswordEntity, UUID>
