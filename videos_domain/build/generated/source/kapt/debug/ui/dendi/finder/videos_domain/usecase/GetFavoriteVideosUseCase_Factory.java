// Generated by Dagger (https://dagger.dev).
package ui.dendi.finder.videos_domain.usecase;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;
import ui.dendi.finder.videos_domain.VideosRepository;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class GetFavoriteVideosUseCase_Factory implements Factory<GetFavoriteVideosUseCase> {
  private final Provider<VideosRepository> repositoryProvider;

  public GetFavoriteVideosUseCase_Factory(Provider<VideosRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetFavoriteVideosUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetFavoriteVideosUseCase_Factory create(
      Provider<VideosRepository> repositoryProvider) {
    return new GetFavoriteVideosUseCase_Factory(repositoryProvider);
  }

  public static GetFavoriteVideosUseCase newInstance(VideosRepository repository) {
    return new GetFavoriteVideosUseCase(repository);
  }
}
