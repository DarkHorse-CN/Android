package com.darkhorse.android.lib.permission;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class AndroidPermission_Factory implements Factory<AndroidPermission> {
  private final Provider<Context> contextProvider;

  private AndroidPermission_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public AndroidPermission get() {
    return newInstance(contextProvider.get());
  }

  public static AndroidPermission_Factory create(Provider<Context> contextProvider) {
    return new AndroidPermission_Factory(contextProvider);
  }

  public static AndroidPermission newInstance(Context context) {
    return new AndroidPermission(context);
  }
}
