/*
 * Copyright (c) 2025 TamboUI Contributors
 * SPDX-License-Identifier: MIT
 */
package dev.tamboui.terminal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Factory for creating {@link Backend} instances using the {@link ServiceLoader} mechanism.
 * <p>
 * This factory discovers {@link BackendProvider} implementations on the classpath
 * and uses them to create backend instances. Applications should include exactly
 * one backend provider on the classpath (e.g., tamboui-jline).
 *
 * @see BackendProvider
 * @see Backend
 */
public final class BackendFactory {

    private BackendFactory() {
        // Utility class
    }

    /**
     * Creates a new backend instance using the discovered provider.
     * <p>
     * This method discovers {@link BackendProvider} implementations on the classpath
     * and selects one based on the following priority:
     * <ol>
     *   <li>System property {@code tamboui.backend} (if set)</li>
     *   <li>Environment variable {@code TAMBOUI_BACKEND} (if set)</li>
     *   <li>Auto-discovery via ServiceLoader (if exactly one provider is found)</li>
     * </ol>
     * <p>
     * The provider can be specified by:
     * <ul>
     *   <li>Simple name (e.g., "jline", "panama") - matches the provider's {@link BackendProvider#name()}</li>
     *   <li>Fully qualified class name (e.g., "dev.tamboui.backend.jline.JLineBackendProvider")</li>
     * </ul>
     * <p>
     * If multiple providers are found and none is explicitly specified, an exception is thrown
     * with a list of available providers.
     *
     * @return a new backend instance
     * @throws IOException if backend creation fails
     * @throws IllegalStateException if no provider is found, multiple providers are found without explicit selection,
     *                               or the specified provider cannot be found or instantiated
     */
    public static Backend create() throws IOException {
        // Check system property first, then environment variable
        String userSelectedProvider = System.getProperty("tamboui.backend");
        if (userSelectedProvider == null || userSelectedProvider.isEmpty()) {
            userSelectedProvider = System.getenv("TAMBOUI_BACKEND");
        }
        String providerSpec = userSelectedProvider;
        boolean isClassName = providerSpec != null && providerSpec.contains(".");

        // Load all available providers (needed for simple name matching or auto-discovery)
        ServiceLoader<BackendProvider> loader = ServiceLoader.load(BackendProvider.class);
        List<BackendProvider> providers = StreamSupport.stream(loader.spliterator(), false)
                .filter(p -> {
                    if (providerSpec == null || providerSpec.isEmpty()) {
                        return true;
                    }
                    if (isClassName) {
                        // If a class name is specified, filter to only that provider
                        return p.getClass().getName().equals(providerSpec);
                    }
                    return p.name().equals(providerSpec);
                })
                .collect(Collectors.toList());

        if (providers.isEmpty()) {
            String details = "";
            if (isClassName) {
                details = " for class name '" + providerSpec + "'";
            } else if (providerSpec != null && !providerSpec.isEmpty()) {
                details = " for provider name '" + providerSpec + "'";
            }
            throw new IllegalStateException(
                "No BackendProvider found on classpath" + details + ".\n" +
                "Add a backend dependency such as tamboui-jline."
            );
        }

        // No explicit selection - check if we have exactly one
        if (providers.size() > 1) {
            String availableProviders = formatAvailableProviders(providers);
            throw new IllegalStateException(
                "Multiple backend providers found on classpath.\n"+
                "Include only one backend or specify which provider to use by setting the tamboui.backend system property " +
                "or TAMBOUI_BACKEND environment variable to one of:\n" + availableProviders
            );
        }

        return providers.get(0).create();
    }

    /**
     * Formats a list of providers into a user-friendly string showing both names and class names.
     *
     * @param providers the list of providers
     * @return a formatted string listing available providers
     */
    private static String formatAvailableProviders(List<BackendProvider> providers) {
        return providers.stream()
            .map(p -> p.name() + " (" + p.getClass().getName() + ")")
            .collect(Collectors.joining("\n"));
    }
}

