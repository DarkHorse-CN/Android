# core-network — 网络层抽象模块

[![Module](https://img.shields.io/badge/Module-core--network-blue)]()
[![Language](https://img.shields.io/badge/Language-Kotlin-purple)]()
[![Network](https://img.shields.io/badge/Network-Retrofit%20%7C%20OkHttp-orange)]()

## 目录

- [概述](#概述)
- [技术选型](#技术选型)
- [架构设计](#架构设计)
- [核心组件](#核心组件)
- [依赖注入](#依赖注入)
- [API 定义规范](#api-定义规范)
- [使用示例](#使用示例)
- [测试策略](#测试策略)
- [版本发布与依赖方式](#版本发布与依赖方式)
- [设计分析与改进建议](#设计分析与改进建议)
- [贡献指南](#贡献指南)

---

## 概述

`core-network` 是 Android 应用的**网络层抽象接口模块**，定义网络请求的基础契约。它只包含 Kotlin 接口（`interface`）和纯数据类型，**不包含任何具体实现**。具体实现由独立的 `lib:lib-network` 模块基于 Retrofit + OkHttp 提供，遵循「依赖倒置原则（DIP）」。

### 模块职责

| 职责 | 说明 |
|------|------|
| 定义 API 工厂契约 | `ApiFactory` — 创建 Retrofit API 接口实例 |
| 定义通用返回类型 | `ApiResult` — 统一 Success / Error / Exception 三种结果 |
| 定义 HTTP 客户端契约 | `IHttpClient` — 通用 GET/POST/PUT/DELETE 操作 |
| 定义网络状态监听 | `INetworkMonitor` — 网络连通性监听与状态变化 Flow |

### 约束

- **纯接口模块**：不依赖任何网络库（Retrofit、OkHttp、Ktor 等）
- **最小依赖**：仅依赖 `kotlinx-coroutines-core` 和 `kotlinx-serialization-json`
- **单向依赖**：业务层（feature）→ lib-network 实现 → core-network 接口

---

## 技术选型

| 类别 | 技术栈 | 版本 |
|------|--------|------|
| 语言 | Kotlin | 2.4.0 |
| 异步 | Coroutines + Flow | 1.11.0 |
| 序列化 | kotlinx.serialization | 1.11.0 |
| 构建工具 | Gradle KTS + Version Catalog | — |

> 具体网络实现层（`lib:lib-network`）使用：
> - **Retrofit 3.0.0** — 声明式 HTTP 客户端
> - **OkHttp 5.4.0** — 高效 HTTP 引擎
> - **okhttp-logging 5.4.0** — 日志拦截器
>
> 参见 `gradle/libs.versions.toml` 版本目录。

---

## 架构设计

### 包结构

```
core:core-network                          # 接口抽象层
└── src/main/kotlin/com/darkhorse/android/core/network/
    ├── ApiFactory.kt                      # Retrofit API 实例工厂契约
    ├── ApiRequest.kt                      # @ApiRequest 注解（标记自动生成入口）
    ├── ApiResult.kt                       # 通用网络请求结果密封类型
    ├── DhModels.kt                        # DhRequest<R> / DhResponse 基类
    ├── HttpMethods.kt                     # HTTP 方法常量
    ├── IHttpClient.kt                     # HTTP 客户端通用接口
    └── INetworkMonitor.kt                 # 网络状态监听接口

core:core-network-compiler                 # KSP 编译器（编译期代码生成）
└── src/main/kotlin/com/darkhorse/android/core/network/compiler/
    ├── ApiRequestProcessorProvider.kt     # KSP SymbolProcessorProvider
    ├── ApiRequestSymbolProcessor.kt       # KSP 注解处理器核心逻辑
    └── ApiServiceGenerator.kt             # KotlinPoet 代码生成器

lib:lib-network                            # 具体实现层
└── src/main/kotlin/com/darkhorse/android/lib/network/
    ├── OkHttpApiFactory.kt                # ApiFactory OkHttp + Retrofit 实现
    ├── NetworkModule.kt                   # Hilt DI 模块
    └── interceptor/
        └── LoggingInterceptor.kt          # 请求/响应日志拦截器
```

### 模块依赖关系

```
┌──────────────────────────────────────────────────────┐
│                   app (应用壳)                         │
└──────────────────┬───────────────────────────────────┘
                   │ depends on
┌──────────────────▼───────────────────────────────────┐
│              feature/* (业务模块)                      │
│  ksp(project(":core:core-network-compiler"))          │
└────┬─────────────┬─────────────────┬─────────────────┘
     │             │                 │
     │ compile      │ ksp             │ depends on
     │ (api接口)    │ (代码生成)       │
     │             │                 │
┌────▼─────────┐ ┌─▼─────────────┐ ┌─▼─────────────────┐
│core:core-    │ │core:core-     │ │lib:lib-network    │
│network       │ │network-       │ │(实现层)            │
│(接口层)       │ │compiler       │ │Retrofit·OkHttp   │
└──────────────┘ │(KSP处理器)     │ └────┬──────────────┘
                 └───────────────┘      │ implements
                                        │
                                 ┌──────▼──────────────┐
                                 │ core:core-network    │
                                 │ (接口层)              │
                                 └─────────────────────┘
```

### 设计原则

1. **依赖倒置（DIP）**：`core-network` 定义接口，`lib-network` 提供实现，业务代码依赖抽象而非具体实现。
2. **接口隔离（ISP）**：`ApiFactory`、`IHttpClient`、`INetworkMonitor` 职责清晰，互不耦合。
3. **单一职责（SRP）**：每个文件只关注一个抽象概念。
4. **可替换性**：网络实现可随时从 Retrofit 替换为 Ktor 或其他库，业务代码无需改动。
5. **编译期代码生成**：`core-network-compiler` 在编译期扫描 `@ApiRequest` 注解，自动生成 Retrofit API 接口和 Service 封装类，消除手写样板代码、避免运行时反射开销。

---

## 核心组件

### 1. ApiFactory — API 实例工厂

```kotlin
interface ApiFactory {
    fun <T : Any> create(apiClass: Class<T>, baseUrl: String? = null): T
}

// 便捷扩展：利用 reified 避免传 Class 参数
inline fun <reified T : Any> ApiFactory.create(baseUrl: String? = null): T {
    return create(T::class.java, baseUrl)
}
```

**职责**：创建 Retrofit 风格的声明式 API 接口实例。
- 支持自定义 `baseUrl`（默认使用全局配置）
- 通过 `@Inject` 注入到 ViewModel 或 Repository 中使用
- 实现类 `OkHttpApiFactory` 使用 `kotlinx.serialization` 作为 JSON 转换器

### 2. ApiResult — 通用返回类型

```kotlin
sealed interface ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>
    data class Error(val code: Int, val message: String) : ApiResult<Nothing>
    data class Exception(val throwable: Throwable) : ApiResult<Nothing>
}
```

**职责**：统一封装网络请求的三种结果状态。

| 分支 | 场景 | 包含信息 |
|------|------|----------|
| `Success` | 请求成功且解析成功 | 领域模型 `data: T` |
| `Error` | 请求完成但服务器返回错误（4xx/5xx） | HTTP 状态码 + 错误消息 |
| `Exception` | 请求过程中发生异常（网络断开、超时、解析失败） | `Throwable` |

**使用建议**：
- 业务层通过 `when` 表达式强制处理所有分支，编译安全
- `Error` 中可扩展 `body: String?` 字段以透传原始错误响应体

### 3. IHttpClient — HTTP 客户端接口

```kotlin
interface IHttpClient {
    suspend fun <T> get(url: String, params: Map<String, String> = emptyMap()): ApiResult<T>
    suspend fun <T> post(url: String, body: Any? = null): ApiResult<T>
    suspend fun <T> put(url: String, body: Any? = null): ApiResult<T>
    suspend fun <T> delete(url: String, params: Map<String, String> = emptyMap()): ApiResult<T>
}
```

**职责**：提供通用的 HTTP 方法抽象，适合简单请求或动态 URL 场景。
- 所有方法均为 `suspend` 函数，天然支持协程
- 返回值统一为 `ApiResult<T>`，与错误处理体系一致
- 对复杂业务场景，推荐直接使用 Retrofit 声明式 API + `ApiFactory`

### 4. INetworkMonitor — 网络状态监听

```kotlin
interface INetworkMonitor {
    val isOnline: Boolean
    val networkState: Flow<NetworkState>
}

sealed interface NetworkState {
    data object Connected : NetworkState
    data object Disconnected : NetworkState
    data class Metered(val type: String) : NetworkState
}
```

**职责**：监听设备网络连接状态，支持响应式监听。
- `isOnline` — 同步查询当前是否联网
- `networkState` — 响应式 `Flow`，实时发布网络状态变化
- `Metered` 状态用于区分移动数据 / Wi-Fi，指导数据预加载策略

---

## 依赖注入

### Hilt 模块配置（lib-network）

```kotlin
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true   // 忽略未知字段，增强向后兼容
        coerceInputValues = true   // 自动将非法值转为默认值
        isLenient = true           // 宽松解析
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(LoggingInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(json: Json, okHttpClient: OkHttpClient): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl("https://api.example.com/")
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }
}
```

### 如何使用 ApiFactory

```kotlin
@Singleton
class UserRepository @Inject constructor(
    private val apiFactory: ApiFactory,
) {
    suspend fun getProfile(): ApiResult<UserProfile> {
        return try {
            val api = apiFactory.create<UserApi>()
            val response = api.getProfile()
            ApiResult.Success(response)
        } catch (e: Exception) {
            ApiResult.Exception(e)
        }
    }
}
```

### 注解说明

| 注解 | 作用 |
|------|------|
| `@Module` | 标记为 Dagger Hilt 模块 |
| `@InstallIn(SingletonComponent::class)` | 绑定到应用全局单例生命周期 |
| `@Provides` | 声明实例提供方法 |
| `@Singleton` | 确保全局单例（OkHttpClient 复用连接池） |

---

## API 定义规范

### 声明式 API 接口示例

在业务模块中定义 Retrofit 风格的 API 接口：

```kotlin
// feature:feature-home/src/main/kotlin/.../api/HomeApi.kt
interface HomeApi {
    @GET("v1/home/banner")
    suspend fun getBanner(): List<Banner>

    @GET("v1/home/recommend")
    suspend fun getRecommendList(
        @Query("page") page: Int,
        @Query("size") size: Int = 20,
    ): RecommendResponse

    @POST("v1/user/login")
    suspend fun login(@Body body: LoginRequest): LoginResponse
}
```

### 命名规范

| 元素 | 规范 | 示例 |
|------|------|------|
| API 接口 | `*Api` | `UserApi`、`HomeApi` |
| 请求体 | `*Request` | `LoginRequest`、`UpdateProfileRequest` |
| 响应体 | `*Response` 或直接使用领域模型 | `UserResponse`、`Banner` |
| Repository | `*Repository` | `UserRepository`、`HomeRepository` |

### 接口设计原则

1. **使用 `suspend` 函数**，而非 `Call<T>` 或 `Observable<T>`
2. **返回领域模型**，而非包装的 `ApiResult`（由 Repository 层统一转换）
3. **路径版本化**：URL 路径包含 API 版本号（如 `/v1/`、`/v2/`）
4. **分页统一**：`@Query("page")` + `@Query("size")` 分页参数

---

## 使用示例

### 在 Repository 中使用

```kotlin
class HomeRepository @Inject constructor(
    private val apiFactory: ApiFactory,
    private val networkMonitor: INetworkMonitor,
) {
    private val homeApi = apiFactory.create<HomeApi>()

    // 返回 Flow<ApiResult<T>> 供 ViewModel 收集
    fun getBanners(): Flow<ApiResult<List<Banner>>> = flow {
        // 检查网络状态
        if (!networkMonitor.isOnline) {
            emit(ApiResult.Error(-1, "网络不可用，请检查连接"))
            return@flow
        }

        emit(
            try {
                ApiResult.Success(homeApi.getBanner())
            } catch (e: java.net.ConnectException) {
                ApiResult.Exception(e)
            } catch (e: java.net.SocketTimeoutException) {
                ApiResult.Error(408, "请求超时，请稍后重试")
            } catch (e: Exception) {
                ApiResult.Exception(e)
            }
        )
    }
}
```

### 在 ViewModel 中消费

```kotlin
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
) : ViewModel() {

    private val _banners = MutableStateFlow<ApiResult<List<Banner>>>(ApiResult.Loading)
    val banners: StateFlow<ApiResult<List<Banner>>> = _banners.asStateFlow()

    init {
        loadBanners()
    }

    fun loadBanners() {
        viewModelScope.launch {
            homeRepository.getBanners().collect { result ->
                _banners.value = result
            }
        }
    }
}
```

### 在 Compose UI 中处理

```kotlin
@Composable
fun BannerSection(viewModel: HomeViewModel = hiltViewModel()) {
    val banners by viewModel.banners.collectAsStateWithLifecycle()

    when (val result = banners) {
        is ApiResult.Loading -> ShimmerLoading()
        is ApiResult.Success -> BannerCarousel(banners = result.data)
        is ApiResult.Error -> ErrorBanner(message = result.message, onRetry = viewModel::loadBanners)
        is ApiResult.Exception -> ErrorBanner(message = result.throwable.message ?: "未知错误")
    }
}
```

> 💡 **提示**：如果 `ApiResult` 需要 Loading 状态，建议添加 `data object Loading : ApiResult<Nothing>` 分支。

---

## KSP 编译器 — core-network-compiler

`core:core-network-compiler` 是一个 **KSP（Kotlin Symbol Processing）编译器**，在编译期扫描 `@ApiRequest` 注解并自动生成 Retrofit API 接口和 Service 封装类。

### 工作流程

```
[源代码]                          [编译期]                              [生成代码]
                                                                 
@ApiRequest(url="v1/user/login") ──▶  core-network-compiler ──▶  LoginRequestApi (Retrofit 接口)
class LoginRequest :                   (KSP Processor)            LoginRequestService (注入封装)
    DhRequest<LoginResponse>                                   └── ApiResult<LoginResponse>
```

### 如何使用

**Step 1** — 在业务模块的 `build.gradle.kts` 中启用 KSP 并添加依赖：

```kotlin
plugins {
    // ... 其他插件
    id("com.google.devtools.ksp")             // 启用 KSP
}

dependencies {
    implementation(project(":core:core-network"))
    implementation(project(":lib:lib-network"))
    ksp(project(":core:core-network-compiler")) // KSP 编译器
}
```

**Step 2** — 定义 Request 类并添加 `@ApiRequest` 注解：

```kotlin
@ApiRequest(url = "v1/user/login", method = HttpMethods.POST)
data class LoginRequest(
    val username: String,
    val password: String,
) : DhRequest<LoginResponse>()
```

**Step 3** — 使用生成的 Service 发起请求（无需手写 Retrofit 接口）：

```kotlin
@Singleton
class LoginRepository @Inject constructor(
    private val loginService: LoginRequestService,   // ← 自动生成的 Service
) {
    suspend fun login(username: String, password: String): ApiResult<LoginResponse> {
        return loginService.execute(LoginRequest(username, password))
    }
}
```

### 生成的代码示例

对于上面的 `LoginRequest`，编译器自动生成两个类型：

**`LoginRequestApi`** — Retrofit 声明式接口：

```kotlin
internal interface LoginRequestApi {
    @POST("v1/user/login")
    suspend fun execute(@Body body: LoginRequest): LoginResponse
}
```

**`LoginRequestService`** — 可注入的 Service 封装：

```kotlin
@Singleton
internal class LoginRequestService @Inject constructor(
    private val apiFactory: ApiFactory,
) {
    suspend fun execute(request: LoginRequest): ApiResult<LoginResponse> {
        val api = apiFactory.create<LoginRequestApi>()
        return try {
            ApiResult.Success(api.execute(request))
        } catch (e: Exception) {
            ApiResult.Exception(e)
        }
    }
}
```

### 注解参考：`@ApiRequest`

| 参数 | 类型 | 必需 | 默认值 | 说明 |
|------|------|------|--------|------|
| `url` | `String` | ✅ | — | API 路径（如 `v1/user/login`） |
| `method` | `String` | ❌ | `HttpMethods.POST` | HTTP 方法（GET/POST/PUT/DELETE） |

### 约束

- Request 类必须实现 `DhRequest<R : DhResponse>` 接口，`R` 为响应类型
- `@ApiRequest` 只能用于具体类（非 abstract、非 interface）
- 生成的代码位于与 Request 类相同的包中，并使用 `internal` 可见性
- 使用方模块需同时依赖 `core:core-network`（提供 ApiFactory、ApiResult 等类型）

---

## 测试策略

### 单元测试 — 使用 MockWebServer

```kotlin
class HomeApiTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: HomeApi

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(LoggingInterceptor())
            .build()

        val json = Json { ignoreUnknownKeys = true; coerceInputValues = true }
        val contentType = "application/json".toMediaType()

        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(HomeApi::class.java)
    }

    @Test
    fun `getBanner - should return banner list on 200`() = runTest {
        // 准备 Mock 响应
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody("""[{"id":1,"title":"Banner 1","imageUrl":"https://..."}]""")
        )

        val result = api.getBanner()

        assertEquals(1, result.size)
        assertEquals("Banner 1", result[0].title)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }
}
```

### 单元测试 — Repository 层

```kotlin
class HomeRepositoryTest {
    private val apiFactory = mockk<ApiFactory>()
    private val homeApi = mockk<HomeApi>()
    private val repository = HomeRepository(apiFactory)

    @Test
    fun `getBanners - should return Success on valid response`() = runTest {
        every { apiFactory.create<HomeApi>() } returns homeApi
        coEvery { homeApi.getBanner() } returns listOf(Banner(1, "Test", "..."))

        val result = repository.getBanners().first()

        assertTrue(result is ApiResult.Success)
        assertEquals(1, (result as ApiResult.Success).data.size)
    }

    @Test
    fun `getBanners - should return Exception on network error`() = runTest {
        every { apiFactory.create<HomeApi>() } returns homeApi
        coEvery { homeApi.getBanner() } throws IOException("No network")

        val result = repository.getBanners().first()

        assertTrue(result is ApiResult.Exception)
    }
}
```

### 测试依赖

```toml
# 在 libs.versions.toml 中已定义
mockk = "1.14.11"
turbine = "1.2.1"               # 用于测试 Flow
kotlinx-coroutines-test = "1.11.0"
```

---

## 版本发布与依赖方式

### 添加依赖

```kotlin
// 在需要使用的模块中（如 feature）添加：
plugins {
    id("com.google.devtools.ksp")   // 如需 KSP 代码生成
}

dependencies {
    // 依赖接口层
    implementation(project(":core:core-network"))

    // 依赖实现层
    implementation(project(":lib:lib-network"))

    // KSP 编译器（可选，用于 @ApiRequest 自动代码生成）
    ksp(project(":core:core-network-compiler"))
}
```

### 版本兼容性

| core-network | core-network-compiler | lib-network | Retrofit | OkHttp | KSP | 最低 SDK |
|--------------|----------------------|-------------|----------|--------|-----|----------|
| 1.0.x | 1.0.x | 1.0.x | 3.0.x | 5.4.x | 2.3.x | 26 |

### 发布策略

- 版本号遵循 [SemVer](https://semver.org/) 规范
- 接口变更（`core-network`）→ 主版本号递增
- 实现变更（`lib-network`）→ 次版本号递增
- 修复/优化 → 修订版本号递增

---

## 设计分析与改进建议

基于对当前代码的审查，以下是架构设计和可改进的方面：

### ✅ 现有优势

| 优势 | 说明 |
|------|------|
| 接口/实现分离 | core-network 定义契约、lib-network 提供实现，符合 DIP 原则 |
| 密封返回类型 | `ApiResult` 强制调用方处理所有分支，编译安全 |
| Kotlinx Serialization | 性能优于 Gson，且与 Kotlin 原生互操作性更好 |
| Flow 响应式监听 | `INetworkMonitor` 使用 Flow 实时推送网络状态变化 |
| Hilt DI 集成 | 模块化的依赖注入，便于替换实现 |

### 🔧 可改进点

| 领域 | 当前状态 | 改进建议 | 收益 |
|------|---------|----------|------|
| **拦截器** | 只有基础日志拦截器 | 添加 `AuthInterceptor`（自动注入 Token）、`RetryInterceptor`（自动重试） | 安全性 + 鲁棒性 |
| **超时配置** | 未设置 connect/read/write 超时 | 添加 `OkHttpClient.Builder` 超时配置：`connectTimeout(15, TimeUnit.SECONDS)` 等 | 避免无限等待 |
| **缓存策略** | 无缓存配置 | 添加 `Cache` 目录 + `Cache-Control` 拦截器 | 离线支持 + 减少流量 |
| **SSL Pinning** | 无 | 添加 `CertificatePinner` 或自定义 `HostnameVerifier` | 防止中间人攻击 |
| **错误体解析** | `ApiResult.Error` 仅包含 code+message | 支持解析服务器错误 JSON body（如 `{ "error_code": 1001, "error_msg": "..." }`） | 更精准的错误处理 |
| **Base URL** | 代码中硬编码 | 移至 `BuildConfig` 或通过 `@Module` 注入提供 | 支持多环境配置 |
| **日志拦截器** | 仅打印 URL 和状态码 | 添加 body 打印（debug 模式下）、curl 格式输出 | 方便调试 |
| **Mock 支持** | 无 | 添加 Debug 环境下 `MockInterceptor` 或启用 `mockwebserver` | 加速开发测试 |
| **统一分页** | 无 | 定义 `PageRequest` / `PageResponse` 通用模型 | 复用分页逻辑 |
| **Hilt 限定符** | 无 | 添加 `@BaseUrl`、`@AuthOkHttpClient`、`@NormalOkHttpClient` 等自定义限定符 | 更细粒度的 DI 控制 |

### 🚀 推荐演进路线

```
Phase 1 ─ 基础增强
├── 添加 connect/read/write 超时配置
├── AuthInterceptor + Token 刷新机制
├── BaseUrl 移至 BuildConfig / DI 注入
└── 增强日志拦截器（Debug body 打印）

Phase 2 ─ 稳定性与安全
├── SSL Pinning 配置
├── 自动重试拦截器（指数退避）
├── 错误响应体解析增强
└── 缓存策略（Cache + Cache-Control）

Phase 3 ─ 开发者体验
├── Debug Mock 拦截器
├── 统一分页模型
├── 请求/响应审计日志
└── 网络性能监控（请求耗时、成功率）
```

---

## 贡献指南

### 开发环境

- Android Studio 最新稳定版
- JDK 17
- Gradle 9.2.1（通过 Gradle Wrapper 使用）

### 分支策略

- `main` — 稳定发布分支
- `develop` — 开发集成分支
- `feature/*` — 特性分支
- `fix/*` — 修复分支

### 提交规范

遵循 [Conventional Commits](https://www.conventionalcommits.org/)：

```
feat(network): 添加 AuthInterceptor 自动注入 Token
fix(network): 修复 ApiResult.Error 序列化异常
docs(network): 更新 README 使用示例
refactor(network): 统一超时时间配置
test(network): 添加 MockWebServer 测试用例
```

### PR 流程

1. 从 `develop` 创建特性分支
2. 实现修改 + 单元测试
3. 确保 `./gradlew :core:core-network:check` 通过
4. 创建 PR 至 `develop`，请求代码审查
5. 合并后发布版本

### 代码风格

- 遵循 [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- 使用 `kotlinx.serialization` 替代 Gson
- 所有公开 API 必须有 KDoc 注释
- 接口变更需更新 ADR（架构决策记录）

---

## 相关模块

| 模块 | 路径 | 职责 |
|------|------|------|
| `core:core-network` | `core/core-network/` | **网络层接口抽象（本模块）** |
| `core:core-network-compiler` | `core/core-network-compiler/` | KSP 编译器：由 `@ApiRequest` 注解生成 API 接口 + Service |
| `lib:lib-network` | `lib/lib-network/` | Retrofit + OkHttp 实现层 |
| `core:core-common` | `core/core-common/` | 通用工具与扩展函数 |

---

*最后更新：2026-06-18*
*维护者：Android 架构组*
