package tech.danielwaiguru.droidhub.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import tech.danielwaiguru.droidhub.repository.MainRepository
import tech.danielwaiguru.droidhub.ui.home.HomeViewModel

@RunWith(AndroidJUnit4::class)
class HomeViewModelTest {
    @Mock
    private lateinit var homeViewModel: HomeViewModel
    @Mock
    private lateinit var repository: MainRepository
    @Mock
    private lateinit var isLoading: LiveData<Boolean>
    @Mock
    private lateinit var observer: Observer<in Boolean>
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        //homeViewModel = spy()
    }

}