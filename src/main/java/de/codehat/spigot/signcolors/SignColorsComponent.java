package de.codehat.spigot.signcolors;

import dagger.BindsInstance;
import dagger.Component;
import de.codehat.spigot.signcolors.module.DatabaseModule;
import de.codehat.spigot.signcolors.module.SignColorsModule;

import javax.inject.Singleton;

@Singleton
@Component(modules = { SignColorsModule.class, DatabaseModule.class })
public interface SignColorsComponent {

  @Component.Builder
  interface Builder {
    @BindsInstance
    Builder signColors(SignColors plugin);

    SignColorsComponent build();
  }

}
