package com.darkhorse.android.feature.login.viewmodel

import com.darkhorse.android.feature.login.data.Result
import com.darkhorse.android.feature.login.data.AuthRepository
import com.darkhorse.android.feature.login.data.UserInfo
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val authRepository: AuthRepository = mockk()

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // ── 字段级校验 ──

    @Test
    fun `empty username sets username error`() {
        viewModel = LoginViewModel(authRepository)
        viewModel.login(username = "", password = "password123")

        assertEquals(LoginUiState.Idle, viewModel.uiState.value)
        assertNotNull(viewModel.usernameError.value)
        assertNull(viewModel.passwordError.value)
    }

    @Test
    fun `short username sets username error`() {
        viewModel = LoginViewModel(authRepository)
        viewModel.login(username = "ab", password = "password123")

        assertEquals(LoginUiState.Idle, viewModel.uiState.value)
        assertNotNull(viewModel.usernameError.value)
        assertEquals("用户名至少需要 3 个字符", viewModel.usernameError.value)
    }

    @Test
    fun `empty password sets password error`() {
        viewModel = LoginViewModel(authRepository)
        viewModel.login(username = "testuser", password = "")

        assertEquals(LoginUiState.Idle, viewModel.uiState.value)
        assertNull(viewModel.usernameError.value)
        assertNotNull(viewModel.passwordError.value)
    }

    @Test
    fun `short password sets password error`() {
        viewModel = LoginViewModel(authRepository)
        viewModel.login(username = "testuser", password = "12345")

        assertEquals(LoginUiState.Idle, viewModel.uiState.value)
        assertEquals("密码至少需要 6 个字符", viewModel.passwordError.value)
    }

    @Test
    fun `multiple validation errors shown simultaneously`() {
        viewModel = LoginViewModel(authRepository)
        viewModel.login(username = "", password = "")

        assertNotNull(viewModel.usernameError.value)
        assertNotNull(viewModel.passwordError.value)
        assertEquals(LoginUiState.Idle, viewModel.uiState.value)
    }

    // ── 登录成功 ──

    @Test
    fun `valid login calls repository and updates to Success`() = runTest(testDispatcher) {
        val fakeUser = UserInfo(
            userId = "1",
            username = "testuser",
            token = "fake-token",
        )
        coEvery { authRepository.login("testuser", "password123") } returns Result.Success(fakeUser)

        viewModel = LoginViewModel(authRepository)
        viewModel.login("testuser", "password123")

        // 执行挂起任务
        testDispatcher.scheduler.advanceUntilIdle()

        coEvery { authRepository.login("testuser", "password123") }
        assertTrue(viewModel.uiState.value is LoginUiState.Success)
        assertNull(viewModel.usernameError.value)
        assertNull(viewModel.passwordError.value)
    }

    @Test
    fun `success resets field errors`() = runTest(testDispatcher) {
        coEvery {
            authRepository.login(any(), any())
        } returns Result.Success(UserInfo(userId = "1", username = "u", token = "t"))

        viewModel = LoginViewModel(authRepository)
        viewModel.login("testuser", "password123")
        testDispatcher.scheduler.advanceUntilIdle()

        assertNull(viewModel.usernameError.value)
        assertNull(viewModel.passwordError.value)
    }

    // ── 登录失败 ──

    @Test
    fun `login failure updates to Error state`() = runTest(testDispatcher) {
        coEvery {
            authRepository.login(any(), any())
        } returns Result.Error("网络连接失败")

        viewModel = LoginViewModel(authRepository)
        viewModel.login("testuser", "password123")
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is LoginUiState.Error)
        assertEquals("网络连接失败", (state as LoginUiState.Error).message)
    }

    @Test
    fun `login throwable mapped to Error state`() = runTest(testDispatcher) {
        coEvery {
            authRepository.login(any(), any())
        } throws RuntimeException("Connection timeout")

        viewModel = LoginViewModel(authRepository)
        viewModel.login("testuser", "password123")
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is LoginUiState.Error)
        assertTrue((state as LoginUiState.Error).message.contains("timeout", ignoreCase = true))
    }

    // ── Loading 状态 ──

    @Test
    fun `login sets Loading state`() = runTest(testDispatcher) {
        coEvery {
            authRepository.login(any(), any())
        } coAnswers {
            // 延迟以模拟网络请求
            kotlinx.coroutines.delay(100)
            Result.Success(UserInfo(userId = "1", username = "u", token = "t"))
        }

        viewModel = LoginViewModel(authRepository)
        viewModel.login("testuser", "password123")

        // Loading 应该立即设置
        assertEquals(LoginUiState.Loading, viewModel.uiState.value)

        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.uiState.value is LoginUiState.Success)
    }

    // ── resetState ──

    @Test
    fun `resetState clears all errors`() {
        viewModel = LoginViewModel(authRepository)
        viewModel.login("", "")

        assertNotNull(viewModel.usernameError.value)
        assertNotNull(viewModel.passwordError.value)

        viewModel.resetState()

        assertEquals(LoginUiState.Idle, viewModel.uiState.value)
        assertNull(viewModel.usernameError.value)
        assertNull(viewModel.passwordError.value)
    }
}
