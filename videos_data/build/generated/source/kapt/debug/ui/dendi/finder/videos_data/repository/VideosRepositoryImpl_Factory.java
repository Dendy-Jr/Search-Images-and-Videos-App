// Generated by Dagger (https://dagger.dev).
package ui.dendi.finder.videos_data.repository;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;
import ui.dendi.finder.videos_data.remote.VideosRemoteDataSource;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class VideosRepositoryImpl_Factory implements Factory<VideosRepositoryImpl> {
  private final Provider<VideosRemoteDataSource> remoteDataSourceProvider;

  public VideosRepositoryImpl_Factory(Provider<VideosRemoteDataSource> remoteDataSourceProvider) {
    this.remoteDataSourceProvider = remoteDataSourceProvider;
  }

  @Override
  public VideosRepositoryImpl get() {
    return newInstance(remoteDataSourceProvider.get());
  }

  public static VideosRepositoryImpl_Factory create(
      Provider<VideosRemoteDataSource> remoteDataSourceProvider) {
    return new VideosRepositoryImpl_Factory(remoteDataSourceProvider);
  }

  public static VideosRepositoryImpl newInstance(VideosRemoteDataSource remoteDataSource) {
    return new VideosRepositoryImpl(remoteDataSource);
  }
}
