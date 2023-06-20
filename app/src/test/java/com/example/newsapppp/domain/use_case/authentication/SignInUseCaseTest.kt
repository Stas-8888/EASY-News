package com.example.newsapppp.domain.use_case.authentication

import com.example.newsapppp.domain.model.UserModel
import com.example.newsapppp.domain.repository.AuthenticationRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock

class SignInUseCaseTest {

    private val mockRepository = mock<AuthenticationRepository>()
    private val testUser = UserModel("test@example.com", "password")
    private val signInUseCase = SignInUseCase(mockRepository)

    @Test
    fun `invoke signs in user`() = runBlocking {
        val authResult = mock<Task<AuthResult>>()
        `when`(mockRepository.signIn(testUser)).thenReturn(authResult)

        val result = signInUseCase(testUser)

        assertEquals(authResult, result)
    }
}
