package com.example.passwordburn.service

import com.example.passwordburn.model.PasswordEntity
import com.example.passwordburn.repository.PasswordRepository
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service
import java.util.*

@Service
class PasswordService(
    private val passwordRepository: PasswordRepository
) {
    suspend fun createPassword(password: String): PasswordEntity =
        passwordRepository.save(PasswordEntity(
            password = password,
            accessed = false,
        ))

    suspend fun getPasswordOnce(id: UUID): String? {
        val entity = passwordRepository.findById(id)
        if (entity == null || entity.accessed) {
            return null // Already accessed or doesn't exist
        }
        passwordRepository.save(entity.copy(accessed = true))
        return entity.password
    }

    suspend fun deletePassword(id: UUID): Boolean {
        val entity = passwordRepository.findById(id) ?: return false
        passwordRepository.delete(entity)
        return true
    }

    suspend fun listPasswords(): Flow<PasswordEntity> = passwordRepository.findAll()
}