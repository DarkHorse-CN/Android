package com.darkhorse.android.feature.splash;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
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
public final class SplashViewModel_Factory implements Factory<SplashViewModel> {
  private final Provider<AppInitializer> appInitializerProvider;

  private SplashViewModel_Factory(Provider<AppInitializer> appInitializerProvider) {
    this.appInitializerProvider = appInitializerProvider;
  }

  @Override
  public SplashViewModel get() {
    return newInstance(appInitializerProvider.get());
  }

  public static SplashViewModel_Factory create(Provider<AppInitializer> appInitializerProvider) {
    return new SplashViewModel_Factory(appInitializerProvider);
  }

  public static SplashViewModel newInstance(AppInitializer appInitializer) {
    return new SplashViewModel(appInitializer);
  }
}
