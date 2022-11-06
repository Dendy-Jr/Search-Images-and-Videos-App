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
  private final Provider<DialogManager> dialogManagerProvider;

  private final Provider<GetFavoriteImagesUseCase> getFavoriteImagesUseCaseProvider;

  private final Provider<DeleteFavoriteImageUseCase> deleteFavoriteImageUseCaseProvider;

  private final Provider<ClearFavoriteImagesUseCase> clearAllFavoriteImagesUseCaseProvider;

  private final Provider<ResourceProvider> resourceProvider;

  private final Provider<ErrorHandler> errorHandlerProvider;

  public FavoritesImageViewModel_Factory(Provider<DialogManager> dialogManagerProvider,
      Provider<GetFavoriteImagesUseCase> getFavoriteImagesUseCaseProvider,
      Provider<DeleteFavoriteImageUseCase> deleteFavoriteImageUseCaseProvider,
      Provider<ClearFavoriteImagesUseCase> clearAllFavoriteImagesUseCaseProvider,
      Provider<ResourceProvider> resourceProvider, Provider<ErrorHandler> errorHandlerProvider) {
    this.dialogManagerProvider = dialogManagerProvider;
    this.getFavoriteImagesUseCaseProvider = getFavoriteImagesUseCaseProvider;
    this.deleteFavoriteImageUseCaseProvider = deleteFavoriteImageUseCaseProvider;
    this.clearAllFavoriteImagesUseCaseProvider = clearAllFavoriteImagesUseCaseProvider;
    this.resourceProvider = resourceProvider;
    this.errorHandlerProvider = errorHandlerProvider;
  }

  @Override
  public FavoritesImageViewModel get() {
    return newInstance(dialogManagerProvider.get(), getFavoriteImagesUseCaseProvider.get(), deleteFavoriteImageUseCaseProvider.get(), clearAllFavoriteImagesUseCaseProvider.get(), resourceProvider.get(), errorHandlerProvider.get());
  }

  public static FavoritesImageViewModel_Factory create(
      Provider<DialogManager> dialogManagerProvider,
      Provider<GetFavoriteImagesUseCase> getFavoriteImagesUseCaseProvider,
      Provider<DeleteFavoriteImageUseCase> deleteFavoriteImageUseCaseProvider,
      Provider<ClearFavoriteImagesUseCase> clearAllFavoriteImagesUseCaseProvider,
      Provider<ResourceProvider> resourceProvider, Provider<ErrorHandler> errorHandlerProvider) {
    return new FavoritesImageViewModel_Factory(dialogManagerProvider, getFavoriteImagesUseCaseProvider, deleteFavoriteImageUseCaseProvider, clearAllFavoriteImagesUseCaseProvider, resourceProvider, errorHandlerProvider);
  }

  public static FavoritesImageViewModel newInstance(DialogManager dialogManager,
      GetFavoriteImagesUseCase getFavoriteImagesUseCase,
      DeleteFavoriteImageUseCase deleteFavoriteImageUseCase,
      ClearFavoriteImagesUseCase clearAllFavoriteImagesUseCase, ResourceProvider resourceProvider,
      ErrorHandler errorHandler) {
    return new FavoritesImageViewModel(dialogManager, getFavoriteImagesUseCase, deleteFavoriteImageUseCase, clearAllFavoriteImagesUseCase, resourceProvider, errorHandler);
  }
}
