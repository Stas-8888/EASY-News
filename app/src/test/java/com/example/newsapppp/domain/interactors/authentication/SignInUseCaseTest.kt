package com.example.newsapppp.domain.interactors.authentication

import com.example.newsapppp.domain.model.UserModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class SignInUseCaseTest {

    private val mockRepository = mock<AuthenticationRepositoryContract>()
    private val useCase = SignInUseCase(mockRepository)


    @Test
    fun `invoke should call signIn method from repository`() = runBlocking {
        val mockUserModel = UserModel(user = "testuser", password = "testpassword")

        useCase(mockUserModel)
        verify(mockRepository).signIn(mockUserModel)
    }

    @Test
    fun `invoke should return result from repository`() = runBlocking {
        val mockUserModel = UserModel(user = "testuser", password = "testpassword")

        Mockito.`when`(mockRepository.signIn(mockUserModel)).thenReturn(Unit)

        val result = useCase(mockUserModel)
        assertEquals(Unit, result)
    }
}
