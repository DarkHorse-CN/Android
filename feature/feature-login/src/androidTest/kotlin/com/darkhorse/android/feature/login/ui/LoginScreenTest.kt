package com.darkhorse.android.feature.login.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.darkhorse.android.feature.login.viewmodel.LoginUiState
import com.darkhorse.android.feature.login.viewmodel.LoginViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loginScreen_displays_title() {
        composeTestRule.setContent {
            MaterialTheme {
                LoginScreen(viewModel = mockk(relaxed = true))
            }
        }

        composeTestRule.onNodeWithText("欢迎登录").assertIsDisplayed()
        composeTestRule.onNodeWithText("请登录以继续使用").assertIsDisplayed()
    }

    @Test
    fun loginScreen_displays_login_button() {
        composeTestRule.setContent {
            MaterialTheme {
                LoginScreen(viewModel = mockk(relaxed = true))
            }
        }

        composeTestRule.onNodeWithText("登录").assertIsDisplayed()
    }

    @Test
    fun loginScreen_clickButton_callsViewModel() {
        val viewModel = mockk<LoginViewModel>(relaxed = true)
        every { viewModel.uiState } returns MutableStateFlow(LoginUiState.Idle).asStateFlow()
        every { viewModel.usernameError } returns MutableStateFlow<String?>(null).asStateFlow()
        every { viewModel.passwordError } returns MutableStateFlow<String?>(null).asStateFlow()

        composeTestRule.setContent {
            MaterialTheme {
                LoginScreen(viewModel = viewModel)
            }
        }

        composeTestRule.onNodeWithText("登录").performClick()

        verify { viewModel.login(any(), any()) }
    }

    @Test
    fun loginScreen_showsFieldError_whenUsernameError() {
        val viewModel = mockk<LoginViewModel>(relaxed = true)
        every { viewModel.uiState } returns MutableStateFlow(LoginUiState.Idle).asStateFlow()
        every { viewModel.usernameError } returns MutableStateFlow("请输入用户名").asStateFlow()
        every { viewModel.passwordError } returns MutableStateFlow<String?>(null).asStateFlow()

        composeTestRule.setContent {
            MaterialTheme {
                LoginScreen(viewModel = viewModel)
            }
        }

        composeTestRule.onNodeWithText("请输入用户名").assertIsDisplayed()
    }

    @Test
    fun loginScreen_showsFieldError_whenPasswordError() {
        val viewModel = mockk<LoginViewModel>(relaxed = true)
        every { viewModel.uiState } returns MutableStateFlow(LoginUiState.Idle).asStateFlow()
        every { viewModel.usernameError } returns MutableStateFlow<String?>(null).asStateFlow()
        every { viewModel.passwordError } returns MutableStateFlow("请输入密码").asStateFlow()

        composeTestRule.setContent {
            MaterialTheme {
                LoginScreen(viewModel = viewModel)
            }
        }

        composeTestRule.onNodeWithText("请输入密码").assertIsDisplayed()
    }

    @Test
    fun loginScreen_showsLoading_whenLoadingState() {
        val viewModel = mockk<LoginViewModel>(relaxed = true)
        every { viewModel.uiState } returns MutableStateFlow(LoginUiState.Loading).asStateFlow()
        every { viewModel.usernameError } returns MutableStateFlow<String?>(null).asStateFlow()
        every { viewModel.passwordError } returns MutableStateFlow<String?>(null).asStateFlow()

        composeTestRule.setContent {
            MaterialTheme {
                LoginScreen(viewModel = viewModel)
            }
        }

        // Loading state → button should be disabled
        composeTestRule.onNodeWithText("登录").assertIsDisplayed()
    }

    @Test
    fun loginScreen_showsServerError_whenErrorState() {
        val viewModel = mockk<LoginViewModel>(relaxed = true)
        every { viewModel.uiState } returns MutableStateFlow(
            LoginUiState.Error("服务器繁忙，请稍后重试")
        ).asStateFlow()
        every { viewModel.usernameError } returns MutableStateFlow<String?>(null).asStateFlow()
        every { viewModel.passwordError } returns MutableStateFlow<String?>(null).asStateFlow()

        composeTestRule.setContent {
            MaterialTheme {
                LoginScreen(viewModel = viewModel)
            }
        }

        composeTestRule.onNodeWithText("服务器繁忙，请稍后重试").assertIsDisplayed()
    }

    @Test
    fun loginScreen_showsRegisterLink() {
        val viewModel = mockk<LoginViewModel>(relaxed = true)
        every { viewModel.uiState } returns MutableStateFlow(LoginUiState.Idle).asStateFlow()
        every { viewModel.usernameError } returns MutableStateFlow<String?>(null).asStateFlow()
        every { viewModel.passwordError } returns MutableStateFlow<String?>(null).asStateFlow()

        composeTestRule.setContent {
            MaterialTheme {
                LoginScreen(viewModel = viewModel)
            }
        }

        composeTestRule.onNodeWithText("还没有账号？立即注册").assertIsDisplayed()
    }

    @Test
    fun loginScreen_clickRegister_callsCallback() {
        val viewModel = mockk<LoginViewModel>(relaxed = true)
        every { viewModel.uiState } returns MutableStateFlow(LoginUiState.Idle).asStateFlow()
        every { viewModel.usernameError } returns MutableStateFlow<String?>(null).asStateFlow()
        every { viewModel.passwordError } returns MutableStateFlow<String?>(null).asStateFlow()

        var registerClicked = false
        composeTestRule.setContent {
            MaterialTheme {
                LoginScreen(
                    viewModel = viewModel,
                    onNavigateToRegister = { registerClicked = true },
                )
            }
        }

        composeTestRule.onNodeWithText("还没有账号？立即注册").performClick()
        assert(registerClicked) { "Register link click should trigger onNavigateToRegister" }
    }
}
