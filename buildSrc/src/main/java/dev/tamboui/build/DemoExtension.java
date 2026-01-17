package dev.tamboui.build;

import org.gradle.api.provider.Property;
import org.gradle.api.provider.SetProperty;

/**
 * Extension interface for demo configuration.
 */
public interface DemoExtension {
    Property<String> getDisplayName();
    Property<String> getDescription();
    Property<String> getModule();
    SetProperty<String> getTags();

    /**
     * If true, this demo is internal and should not appear in the gallery.
     */
    Property<Boolean> getInternal();
}
