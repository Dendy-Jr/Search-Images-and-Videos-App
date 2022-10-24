// Generated by Dagger (https://dagger.dev).
package ui.dendi.finder.images_data.local;

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
  private final Provider<ImageDao> imageDaoProvider;

  public ImagesLocalDataSource_Factory(Provider<ImageDao> imageDaoProvider) {
    this.imageDaoProvider = imageDaoProvider;
  }

  @Override
  public ImagesLocalDataSource get() {
    return newInstance(imageDaoProvider.get());
  }

  public static ImagesLocalDataSource_Factory create(Provider<ImageDao> imageDaoProvider) {
    return new ImagesLocalDataSource_Factory(imageDaoProvider);
  }

  public static ImagesLocalDataSource newInstance(ImageDao imageDao) {
    return new ImagesLocalDataSource(imageDao);
  }
}