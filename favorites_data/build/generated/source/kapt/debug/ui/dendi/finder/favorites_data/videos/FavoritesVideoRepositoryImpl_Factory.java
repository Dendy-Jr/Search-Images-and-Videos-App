// Generated by Dagger (https://dagger.dev).
package ui.dendi.finder.favorites_data.videos;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class FavoritesVideoRepositoryImpl_Factory implements Factory<FavoritesVideoRepositoryImpl> {
  private final Provider<VideosLocalDataSource> localDataSourceProvider;

  public FavoritesVideoRepositoryImpl_Factory(
      Provider<VideosLocalDataSource> localDataSourceProvider) {
    this.localDataSourceProvider = localDataSourceProvider;
  }

  @Override
  public FavoritesVideoRepositoryImpl get() {
    return newInstance(localDataSourceProvider.get());
  }

  public static FavoritesVideoRepositoryImpl_Factory create(
      Provider<VideosLocalDataSource> localDataSourceProvider) {
    return new FavoritesVideoRepositoryImpl_Factory(localDataSourceProvider);
  }

  public static FavoritesVideoRepositoryImpl newInstance(VideosLocalDataSource localDataSource) {
    return new FavoritesVideoRepositoryImpl(localDataSource);
  }
}
