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
    public void classloader(@NotNull PluginClasspathBuilder classpathBuilder) {
        record PaperLibraries(Map<String, String> repositories, List<String> dependencies) {
            public Stream<RemoteRepository> getRepositories() {
                return repositories.entrySet().stream().map(it -> new RemoteRepository.Builder(
                        it.getKey(), "default", it.getValue()
                ).build());
            }
            public Stream<Dependency> getDependencies() {
                return dependencies.stream().map(it -> new Dependency(new DefaultArtifact(it), null));
            }
        }
        PaperLibraries paperLibraries = new Gson().fromJson(new InputStreamReader(
                Objects.requireNonNull(getClass().getResourceAsStream("/paper-libraries.json")), StandardCharsets.UTF_8
        ), PaperLibraries.class);
        MavenLibraryResolver resolver = new MavenLibraryResolver();
        paperLibraries.getRepositories().forEach(resolver::addRepository);
        paperLibraries.getDependencies().forEach(resolver::addDependency);
        classpathBuilder.addLibrary(resolver);
    }
}
