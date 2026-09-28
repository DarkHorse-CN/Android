package com.darkhorse.android.feature.login.data;

import android.content.Context;
import com.darkhorse.android.feature.login.di.LoginApi;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class AuthRepository_Factory implements Factory<AuthRepository> {
  private final Provider<LoginApi> authApiProvider;

  private final Provider<Context> contextProvider;

  private AuthRepository_Factory(Provider<LoginApi> authApiProvider,
      Provider<Context> contextProvider) {
    this.authApiProvider = authApiProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public AuthRepository get() {
    return newInstance(authApiProvider.get(), contextProvider.get());
  }

  public static AuthRepository_Factory create(Provider<LoginApi> authApiProvider,
      Provider<Context> contextProvider) {
    return new AuthRepository_Factory(authApiProvider, contextProvider);
  }

  public static AuthRepository newInstance(LoginApi authApi, Context context) {
    return new AuthRepository(authApi, context);
  }
}
