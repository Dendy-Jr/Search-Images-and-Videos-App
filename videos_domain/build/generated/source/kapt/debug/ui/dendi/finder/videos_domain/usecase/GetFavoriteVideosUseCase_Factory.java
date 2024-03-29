// Generated by Dagger (https://dagger.dev).
package ui.dendi.finder.videos_domain.usecase;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;
import ui.dendi.finder.favorites_domain.videos.repository.FavoritesVideoRepository;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class GetFavoriteVideosUseCase_Factory implements Factory<GetFavoriteVideosUseCase> {
  private final Provider<FavoritesVideoRepository> repositoryProvider;

  public GetFavoriteVideosUseCase_Factory(Provider<FavoritesVideoRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetFavoriteVideosUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetFavoriteVideosUseCase_Factory create(
      Provider<FavoritesVideoRepository> repositoryProvider) {
    return new GetFavoriteVideosUseCase_Factory(repositoryProvider);
  }

  public static GetFavoriteVideosUseCase newInstance(FavoritesVideoRepository repository) {
    return new GetFavoriteVideosUseCase(repository);
  }
}
