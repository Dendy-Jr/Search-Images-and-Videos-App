// Generated by Dagger (https://dagger.dev).
package ui.dendi.finder.images_domain.usecase;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;
import ui.dendi.finder.core.core.DownloadManager;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class DownloadFileUseCase_Factory implements Factory<DownloadFileUseCase> {
  private final Provider<DownloadManager> downloadManagerProvider;

  public DownloadFileUseCase_Factory(Provider<DownloadManager> downloadManagerProvider) {
    this.downloadManagerProvider = downloadManagerProvider;
  }

  @Override
  public DownloadFileUseCase get() {
    return newInstance(downloadManagerProvider.get());
  }

  public static DownloadFileUseCase_Factory create(
      Provider<DownloadManager> downloadManagerProvider) {
    return new DownloadFileUseCase_Factory(downloadManagerProvider);
  }

  public static DownloadFileUseCase newInstance(DownloadManager downloadManager) {
    return new DownloadFileUseCase(downloadManager);
  }
}