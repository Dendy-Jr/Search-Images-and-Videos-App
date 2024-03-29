// Generated by Dagger (https://dagger.dev).
package ui.dendi.finder.favorites_presentation.viewmodel;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;
import ui.dendi.finder.core.core.ErrorHandler;
import ui.dendi.finder.core.core.ResourceProvider;
import ui.dendi.finder.core.core.managers.DialogManager;
import ui.dendi.finder.favorites_domain.images.usecase.ClearFavoriteImagesUseCase;
import ui.dendi.finder.favorites_domain.images.usecase.DeleteFavoriteImageUseCase;
import ui.dendi.finder.favorites_domain.images.usecase.GetFavoriteImagesUseCase;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class FavoritesImageViewModel_Factory implements Factory<FavoritesImageViewModel> {
  private final Provider<ClearFavoriteImagesUseCase> clearAllFavoriteImagesUseCaseProvider;

  private final Provider<DeleteFavoriteImageUseCase> deleteFavoriteImageUseCaseProvider;

  private final Provider<DialogManager> dialogManagerProvider;

  private final Provider<ErrorHandler> errorHandlerProvider;

  private final Provider<GetFavoriteImagesUseCase> getFavoriteImagesUseCaseProvider;

  private final Provider<ResourceProvider> resourceProvider;

  public FavoritesImageViewModel_Factory(
      Provider<ClearFavoriteImagesUseCase> clearAllFavoriteImagesUseCaseProvider,
      Provider<DeleteFavoriteImageUseCase> deleteFavoriteImageUseCaseProvider,
      Provider<DialogManager> dialogManagerProvider, Provider<ErrorHandler> errorHandlerProvider,
      Provider<GetFavoriteImagesUseCase> getFavoriteImagesUseCaseProvider,
      Provider<ResourceProvider> resourceProvider) {
    this.clearAllFavoriteImagesUseCaseProvider = clearAllFavoriteImagesUseCaseProvider;
    this.deleteFavoriteImageUseCaseProvider = deleteFavoriteImageUseCaseProvider;
    this.dialogManagerProvider = dialogManagerProvider;
    this.errorHandlerProvider = errorHandlerProvider;
    this.getFavoriteImagesUseCaseProvider = getFavoriteImagesUseCaseProvider;
    this.resourceProvider = resourceProvider;
  }

  @Override
  public FavoritesImageViewModel get() {
    return newInstance(clearAllFavoriteImagesUseCaseProvider.get(), deleteFavoriteImageUseCaseProvider.get(), dialogManagerProvider.get(), errorHandlerProvider.get(), getFavoriteImagesUseCaseProvider.get(), resourceProvider.get());
  }

  public static FavoritesImageViewModel_Factory create(
      Provider<ClearFavoriteImagesUseCase> clearAllFavoriteImagesUseCaseProvider,
      Provider<DeleteFavoriteImageUseCase> deleteFavoriteImageUseCaseProvider,
      Provider<DialogManager> dialogManagerProvider, Provider<ErrorHandler> errorHandlerProvider,
      Provider<GetFavoriteImagesUseCase> getFavoriteImagesUseCaseProvider,
      Provider<ResourceProvider> resourceProvider) {
    return new FavoritesImageViewModel_Factory(clearAllFavoriteImagesUseCaseProvider, deleteFavoriteImageUseCaseProvider, dialogManagerProvider, errorHandlerProvider, getFavoriteImagesUseCaseProvider, resourceProvider);
  }

  public static FavoritesImageViewModel newInstance(
      ClearFavoriteImagesUseCase clearAllFavoriteImagesUseCase,
      DeleteFavoriteImageUseCase deleteFavoriteImageUseCase, DialogManager dialogManager,
      ErrorHandler errorHandler, GetFavoriteImagesUseCase getFavoriteImagesUseCase,
      ResourceProvider resourceProvider) {
    return new FavoritesImageViewModel(clearAllFavoriteImagesUseCase, deleteFavoriteImageUseCase, dialogManager, errorHandler, getFavoriteImagesUseCase, resourceProvider);
  }
}
