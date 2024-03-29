// Generated by Dagger (https://dagger.dev).
package ui.dendi.finder.favorites_domain.images.usecase;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;
import ui.dendi.finder.favorites_domain.images.repository.FavoritesImageRepository;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class ClearFavoriteImagesUseCase_Factory implements Factory<ClearFavoriteImagesUseCase> {
  private final Provider<FavoritesImageRepository> repositoryProvider;

  public ClearFavoriteImagesUseCase_Factory(Provider<FavoritesImageRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public ClearFavoriteImagesUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static ClearFavoriteImagesUseCase_Factory create(
      Provider<FavoritesImageRepository> repositoryProvider) {
    return new ClearFavoriteImagesUseCase_Factory(repositoryProvider);
  }

  public static ClearFavoriteImagesUseCase newInstance(FavoritesImageRepository repository) {
    return new ClearFavoriteImagesUseCase(repository);
  }
}
