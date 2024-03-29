// Generated by Dagger (https://dagger.dev).
package ui.dendi.finder.favorites_data.images;

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
public final class ImagesLocalDataSource_Factory implements Factory<ImagesLocalDataSource> {
  private final Provider<FavoritesImageDao> daoProvider;

  public ImagesLocalDataSource_Factory(Provider<FavoritesImageDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public ImagesLocalDataSource get() {
    return newInstance(daoProvider.get());
  }

  public static ImagesLocalDataSource_Factory create(Provider<FavoritesImageDao> daoProvider) {
    return new ImagesLocalDataSource_Factory(daoProvider);
  }

  public static ImagesLocalDataSource newInstance(FavoritesImageDao dao) {
    return new ImagesLocalDataSource(dao);
  }
}
