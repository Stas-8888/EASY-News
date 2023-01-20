import com.example.newsapppp.domain.interactors.firebase.ValidateEmailUseCase
import com.example.newsapppp.domain.repository.FirebaseRepositoryContract
import com.google.common.truth.Truth
import org.junit.Test
import org.mockito.kotlin.mock

//package com.example.newsapppp
//
//import com.example.newsapppp.domain.interactors.registration.ValidateEmailUseCase
//import com.example.newsapppp.domain.repository.RegistrationRepository
//import com.google.common.truth.Truth.assertThat
//
//import org.junit.Test
//import org.mockito.kotlin.mock
//
//class RegistrationRepositoryImplTest {
//    //    val getNewsUseCase = GetNewsUseCase(articleRepository)
////    val actual = getNewsUseCase.getNews("s","s")
////    val expected = articleRepository.getNews("s","s")
//    private val registrationRepository = mock<RegistrationRepository>()
//
//    @Test
//    fun login() {
//    }
//
//    @Test
//    fun signup() {
//    }
//
//    @Test
//    fun validateEmail() {
//        val result = ValidateEmailUseCase(registrationRepository).repo.validateEmail("sas")
//        assertThat(result).isFalse()
//    }
//
//    @Test
//    fun validatePassword() {
//    }
//
//    @Test
//    fun validateRepeatedPassword() {
//    }
//
////    @Test
////    fun `empty username returns false`() {
////        val result = RegistrationUtil.validateRegistrationInput(
////            "",
////            "123",
////            "123"
////        )
////        assertThat(result).isFalse()
////    }
//
////    @Test
////    fun `valid username and correctly repeated password returns true`() {
////        val result = RegistrationUtil.validateRegistrationInput(
////            "Philipp",
////            "123",
////            "123"
////        )
////        assertThat(result).isTrue()
////    }
////
////    @Test
////    fun `username already exists returns false`() {
////        val result = RegistrationUtil.validateRegistrationInput(
////            "Carl",
////            "123",
////            "123"
////        )
////        assertThat(result).isFalse()
////    }
////
////    @Test
////    fun `incorrectly confirmed password returns false`() {
////        val result = RegistrationUtil.validateRegistrationInput(
////            "Philipp",
////            "123456",
////            "abcdefg"
////        )
////        assertThat(result).isFalse()
////    }
////
////    @Test
////    fun `empty password returns false`() {
////        val result = RegistrationUtil.validateRegistrationInput(
////            "Philipp",
////            "",
////            ""
////        )
////        assertThat(result).isFalse()
////    }
////
////    @Test
////    fun `less than 2 digit password returns false`() {
////        val result = RegistrationUtil.validateRegistrationInput(
////            "Philipp",
////            "abcdefg5",
////            "abcdefg5"
////        )
////        assertThat(result).isFalse()
////    }
//}
internal class FirbaseRepositoryImplTest {
    private val firebaseRepositoryContract = mock<FirebaseRepositoryContract>()


    @Test
    fun validateEmail() {
        val result = ValidateEmailUseCase(firebaseRepositoryContract).repo.validateEmail("sas")

        Truth.assertThat(result).matches("success")
    }
}
