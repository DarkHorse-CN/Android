package com.darkhorse.android.feature.splash;

import android.content.Context;
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
public final class AppInitializer_Factory implements Factory<AppInitializer> {
  private final Provider<Context> contextProvider;

  private AppInitializer_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public AppInitializer get() {
    return newInstance(contextProvider.get());
  }

  public static AppInitializer_Factory create(Provider<Context> contextProvider) {
    return new AppInitializer_Factory(contextProvider);
  }

  public static AppInitializer newInstance(Context context) {
    return new AppInitializer(context);
  }
}
