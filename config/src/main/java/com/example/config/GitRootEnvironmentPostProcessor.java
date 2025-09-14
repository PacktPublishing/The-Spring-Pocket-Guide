package com.example.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.io.File;
import java.util.Map;

@SuppressWarnings("unused")
class GitRootEnvironmentPostProcessor implements EnvironmentPostProcessor {

	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
		var gitRoot = findGitRoot();
		var mapPropertySource = new MapPropertySource("gitRoot", Map.of("git.root", gitRoot.getAbsolutePath()));
		environment.getPropertySources().addFirst(mapPropertySource);
	}

	private File findGitRoot() {
		var cwd = new File(System.getProperty("user.dir"));
		var gitRoot = (File) null;
		while (!(gitRoot = new File(cwd, ".git")).exists()) {
			cwd = cwd.getParentFile();
		}
		return gitRoot.getParentFile();
	}

}
