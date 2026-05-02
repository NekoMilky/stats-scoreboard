package xyz.nekomilky.mcmod.statsscoreboard.configs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import xyz.nekomilky.mcmod.statsscoreboard.StatsScoreboard;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Supplier;

public class Config<T> {
	final private static Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	final private String fileName;
	final private Class<T> instanceClass;

	public Config(String name, Class<T> instanceClass) {
		this.fileName = name;
		this.instanceClass = instanceClass;
	}

	public T load(Supplier<T> defaultSupplier) {
		try {
			Path filePath = SSBConfigManager.modDirectory.resolve(this.fileName);
			if (!Files.exists(filePath)) {
				return useDefault(defaultSupplier);
			}
			try (Reader reader = Files.newBufferedReader(filePath)) {
				return GSON.fromJson(reader, instanceClass);
			}
		}
		catch (IOException e) {
			StatsScoreboard.LOGGER.error("An Error occurred while loading " + this.fileName + "! ", e);
			return useDefault(defaultSupplier);
		}
	}

	public void save(T config) {
		Path filePath = SSBConfigManager.modDirectory.resolve(fileName);
		try (Writer writer = Files.newBufferedWriter(filePath)) {
			GSON.toJson(config, writer);
		}
		catch (IOException e) {
			StatsScoreboard.LOGGER.error("An Error occurred while saving " + fileName + "! ", e);
		}
	}

	private T useDefault(Supplier<T> defaultSupplier) {
		T defaultConfig = defaultSupplier.get();
		save(defaultConfig);
		return defaultConfig;
	}
}
