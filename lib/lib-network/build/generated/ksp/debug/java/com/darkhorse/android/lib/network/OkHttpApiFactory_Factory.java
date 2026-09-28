package com.darkhorse.android.lib.network;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import okhttp3.OkHttpClient;

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
public final class OkHttpApiFactory_Factory implements Factory<OkHttpApiFactory> {
  private final Provider<OkHttpClient> okHttpClientProvider;

  private OkHttpApiFactory_Factory(Provider<OkHttpClient> okHttpClientProvider) {
    this.okHttpClientProvider = okHttpClientProvider;
  }

  @Override
  public OkHttpApiFactory get() {
    return newInstance(okHttpClientProvider.get());
  }

  public static OkHttpApiFactory_Factory create(Provider<OkHttpClient> okHttpClientProvider) {
    return new OkHttpApiFactory_Factory(okHttpClientProvider);
  }

  public static OkHttpApiFactory newInstance(OkHttpClient okHttpClient) {
    return new OkHttpApiFactory(okHttpClient);
  }
}
