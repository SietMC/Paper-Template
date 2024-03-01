package me.xsiet.template.plugin.loader;

import com.google.gson.Gson;
import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;
import org.jetbrains.annotations.NotNull;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

@SuppressWarnings({"unused", "UnstableApiUsage"})
public class TemplatePluginLoader implements PluginLoader {
    @Override
    public void classloader(@NotNull PluginClasspathBuilder pluginClasspathBuilder) {
        record PaperLibraries(Map<String, String> repositories, List<String> dependencies) {
            public Stream<RemoteRepository> getRepositories() {
                Stream<Map.Entry<String, String>> stream = repositories.entrySet().stream();
                return stream.map(it -> new RemoteRepository.Builder(it.getKey(), "default", it.getValue()).build());
            }
            public Stream<Dependency> getDependencies() {
                return dependencies.stream().map(it -> new Dependency(new DefaultArtifact(it), null));
            }
        }
        InputStream inputStream = Objects.requireNonNull(getClass().getResourceAsStream("/paper-libraries.json"));
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        PaperLibraries paperLibraries = new Gson().fromJson(inputStreamReader, PaperLibraries.class);
        MavenLibraryResolver mavenLibraryResolver = new MavenLibraryResolver();
        paperLibraries.getRepositories().forEach(mavenLibraryResolver::addRepository);
        paperLibraries.getDependencies().forEach(mavenLibraryResolver::addDependency);
        pluginClasspathBuilder.addLibrary(mavenLibraryResolver);
    }
}
