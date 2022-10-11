// Generated by Dagger (https://dagger.dev).
package ui.dendi.finder.images_data.local;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class ImagesFilterStorage_Factory implements Factory<ImagesFilterStorage> {
  private final Provider<Context> contextProvider;

  public ImagesFilterStorage_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public ImagesFilterStorage get() {
    return newInstance(contextProvider.get());
  }

  public static ImagesFilterStorage_Factory create(Provider<Context> contextProvider) {
    return new ImagesFilterStorage_Factory(contextProvider);
  }

  public static ImagesFilterStorage newInstance(Context context) {
    return new ImagesFilterStorage(context);
  }
}
