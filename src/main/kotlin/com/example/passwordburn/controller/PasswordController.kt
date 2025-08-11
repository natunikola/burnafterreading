package com.example.passwordburn.controller

import com.example.passwordburn.service.PasswordService
import kotlinx.coroutines.flow.toList
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

data class PasswordCreateRequest(val password: String)
data class PasswordResponse(val id: UUID, val password: String?)
data class PasswordListResponse(val passwords: List<UUID>)

@RestController
@RequestMapping("/api/passwords")
class PasswordController(
    private val passwordService: PasswordService
) {
    @PostMapping
    suspend fun create(@RequestBody req: PasswordCreateRequest): PasswordResponse {
        val entity = passwordService.createPassword(req.password)
        return PasswordResponse(entity.id!!, null)
    }

    @GetMapping("/{id}")
    suspend fun getOnce(@PathVariable id: String): ResponseEntity<PasswordResponse> {
        val password = passwordService.getPasswordOnce(UUID.fromString(id))
        return if (password != null) {
            ResponseEntity.ok(PasswordResponse(UUID.fromString(id), password))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    suspend fun delete(@PathVariable id: UUID): ResponseEntity<Unit> {
        val deleted = passwordService.deletePassword(id)
        return if (deleted) ResponseEntity.noContent().build()
        else ResponseEntity.notFound().build()
    }

    @GetMapping
    suspend fun list(): PasswordListResponse {
        val entities = passwordService.listPasswords().toList()
        return PasswordListResponse(entities.filter { !it.accessed }.mapNotNull { it.id })
    }
}