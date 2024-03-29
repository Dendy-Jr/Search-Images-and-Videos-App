// Generated by Dagger (https://dagger.dev).
package ui.dendi.finder.favorites_domain.videos.usecase;

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
public final class DeleteFavoriteVideoUseCase_Factory implements Factory<DeleteFavoriteVideoUseCase> {
  private final Provider<FavoritesVideoRepository> repositoryProvider;

  public DeleteFavoriteVideoUseCase_Factory(Provider<FavoritesVideoRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public DeleteFavoriteVideoUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static DeleteFavoriteVideoUseCase_Factory create(
      Provider<FavoritesVideoRepository> repositoryProvider) {
    return new DeleteFavoriteVideoUseCase_Factory(repositoryProvider);
  }

  public static DeleteFavoriteVideoUseCase newInstance(FavoritesVideoRepository repository) {
    return new DeleteFavoriteVideoUseCase(repository);
  }
}
