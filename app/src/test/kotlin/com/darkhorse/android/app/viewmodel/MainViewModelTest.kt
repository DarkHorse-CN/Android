package com.darkhorse.android.app.viewmodel

import com.darkhorse.android.feature.login.data.AuthRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * MainViewModel 测试
 *
 * 使用 SharingStarted.Eagerly，stateIn 会在 ViewModel 初始化时立即开始收集，
 * 因此 .value 无需额外订阅即可读取最新状态。
 */
@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val authRepository: AuthRepository = mockk()
    private val isLoggedInFlow = MutableStateFlow(false)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        every { authRepository.isLoggedIn } returns isLoggedInFlow
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `isLoggedIn defaults to false`() = runTest(testDispatcher) {
        val viewModel = MainViewModel(authRepository)
        advanceUntilIdle()

        assertFalse(viewModel.isLoggedIn.value)
    }

    @Test
    fun `isLoggedIn reflects true when auth state changes`() = runTest(testDispatcher) {
        val viewModel = MainViewModel(authRepository)
        advanceUntilIdle()

        isLoggedInFlow.value = true
        advanceUntilIdle()

        assertTrue(viewModel.isLoggedIn.value)
    }

    @Test
    fun `isLoggedIn reflects false after logout`() = runTest(testDispatcher) {
        isLoggedInFlow.value = true
        val viewModel = MainViewModel(authRepository)
        advanceUntilIdle()

        assertTrue(viewModel.isLoggedIn.value)

        isLoggedInFlow.value = false
        advanceUntilIdle()

        assertFalse(viewModel.isLoggedIn.value)
    }
}
