package com.uber.rave.sample.storage;

import com.uber.rave.sample.RaveActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class StorageModule {

    @RaveActivityScope
    @Provides
    static DiskStorage diskStorage() {
        return new DiskStorage();
    }
}
