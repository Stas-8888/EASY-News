import com.example.newsapppp.domain.interactors.firebase.ValidateEmailUseCase
import com.example.newsapppp.domain.repository.FirebaseRepositoryContract
import com.google.common.truth.Truth
import org.junit.Test
import org.mockito.kotlin.mock


internal class FirebaseRepositoryImplTest {
    private val firebaseRepositoryContract = mock<FirebaseRepositoryContract>()

    @Test
    fun validateEmail() {
        val result = ValidateEmailUseCase(firebaseRepositoryContract).repo.validateEmail("sas")

        Truth.assertThat(result).matches("success")
    }
}
