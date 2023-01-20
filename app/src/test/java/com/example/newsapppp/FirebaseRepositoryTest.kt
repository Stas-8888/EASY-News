import com.example.newsapppp.domain.interactors.firebase.validation.ValidateEmailUseCase
import com.example.newsapppp.domain.interactors.firebase.validation.ValidationRepositoryContract
import com.google.common.truth.Truth
import org.junit.Test
import org.mockito.kotlin.mock


internal class FirebaseRepositoryTest {
    private val firebaseRepositoryContract = mock<ValidationRepositoryContract>()

    @Test
    fun validateEmail() {
        val result = ValidateEmailUseCase(firebaseRepositoryContract).repository.validateEmail("sas")

        Truth.assertThat(result).matches("success")
    }
}
