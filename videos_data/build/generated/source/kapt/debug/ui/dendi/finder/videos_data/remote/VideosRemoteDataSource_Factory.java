// Generated by Dagger (https://dagger.dev).
package ui.dendi.finder.videos_data.remote;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class VideosRemoteDataSource_Factory implements Factory<VideosRemoteDataSource> {
  private final Provider<VideosApi> apiProvider;

  public VideosRemoteDataSource_Factory(Provider<VideosApi> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public VideosRemoteDataSource get() {
    return newInstance(apiProvider.get());
  }

  public static VideosRemoteDataSource_Factory create(Provider<VideosApi> apiProvider) {
    return new VideosRemoteDataSource_Factory(apiProvider);
  }

  public static VideosRemoteDataSource newInstance(VideosApi api) {
    return new VideosRemoteDataSource(api);
  }
}
