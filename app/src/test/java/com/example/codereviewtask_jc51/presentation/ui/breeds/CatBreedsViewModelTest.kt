package com.example.codereviewtask_jc51.presentation.ui.breeds

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.codereviewtask_jc51.presentation.entity.CatPreview
import com.example.codereviewtask_jc51.presentation.usecases.GetCatBreedsUseCase
import com.example.codereviewtask_jc51.presentation.usecases.GetCatFactsUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class)
internal class CatBreedsViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val dispatcherRule: TestRule = TestDispatcherRule()

    @MockK
    lateinit var getCatBreedsUseCase: GetCatBreedsUseCase
    @MockK
    private lateinit var getCatFactsUseCase: GetCatFactsUseCase

    private lateinit var viewModel: CatBreedsViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        coEvery { getCatFactsUseCase.invoke(any()) } returns flowOf("")
    }

    @After
    fun teardown() {
        unmockkAll()
    }

    @Test
    fun `Test whether the Breeds API data is loaded on viewModel init`() {
        // Mock the API response
        val expectedResponse = listOf(
            CatPreview(id = 1, breedName = "Test1", picture = "", rate = 0.0),
            CatPreview(id = 2, breedName = "Test2", picture = "", rate = 0.0)
        )
        val falseResult = listOf(
            CatPreview(id = 0, breedName = "Test1", picture = "", rate = 0.0),
        )

        coEvery { getCatBreedsUseCase.invoke(Unit) } returns flowOf(expectedResponse)

        viewModel = CatBreedsViewModel(getCatBreedsUseCase, mockk(), mockk(), getCatFactsUseCase)

        val observer = mockk<Observer<List<CatPreview>?>>(relaxed = true)
        viewModel.breedsList.observeForever(observer)
        verify { observer.onChanged(expectedResponse) }
        verify(exactly = 0) { observer.onChanged(falseResult) }
    }
}