// Generated by Dagger (https://dagger.dev).
package ui.dendi.finder.images_data.local;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;
import ui.dendi.finder.core.core.storage.StorageProvider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class ImagesStorage_Factory implements Factory<ImagesStorage> {
  private final Provider<StorageProvider> storageProvider;

  public ImagesStorage_Factory(Provider<StorageProvider> storageProvider) {
    this.storageProvider = storageProvider;
  }

  @Override
  public ImagesStorage get() {
    return newInstance(storageProvider.get());
  }

  public static ImagesStorage_Factory create(Provider<StorageProvider> storageProvider) {
    return new ImagesStorage_Factory(storageProvider);
  }

  public static ImagesStorage newInstance(StorageProvider storageProvider) {
    return new ImagesStorage(storageProvider);
  }
}
