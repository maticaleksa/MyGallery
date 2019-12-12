package com.example.picture_taking_module;

import android.content.Context;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@PictureTakingSingleton
@Component(modules = {AndroidInjectionModule.class})
public interface PictureTakingComponent extends PictureTakingUnscopedComponent {

    void inject (EmptyPictureTakingActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder bindContext(Context context);

        PictureTakingComponent build();
    }


}
