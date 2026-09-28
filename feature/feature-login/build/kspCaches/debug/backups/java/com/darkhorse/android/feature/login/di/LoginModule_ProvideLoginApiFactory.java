package com.darkhorse.android.feature.login.di;

import com.darkhorse.android.core.network.ApiFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class LoginModule_ProvideLoginApiFactory implements Factory<LoginApi> {
  private final Provider<ApiFactory> apiFactoryProvider;

  private LoginModule_ProvideLoginApiFactory(Provider<ApiFactory> apiFactoryProvider) {
    this.apiFactoryProvider = apiFactoryProvider;
  }

  @Override
  public LoginApi get() {
    return provideLoginApi(apiFactoryProvider.get());
  }

  public static LoginModule_ProvideLoginApiFactory create(Provider<ApiFactory> apiFactoryProvider) {
    return new LoginModule_ProvideLoginApiFactory(apiFactoryProvider);
  }

  public static LoginApi provideLoginApi(ApiFactory apiFactory) {
    return Preconditions.checkNotNullFromProvides(LoginModule.INSTANCE.provideLoginApi(apiFactory));
  }
}
