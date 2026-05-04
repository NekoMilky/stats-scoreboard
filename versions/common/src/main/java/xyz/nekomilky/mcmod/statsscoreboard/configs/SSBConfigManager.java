package xyz.nekomilky.mcmod.statsscoreboard.configs;

import xyz.nekomilky.mcmod.statsscoreboard.StatsScoreboard;
import xyz.nekomilky.mcmod.statsscoreboard.utils.server.Server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SSBConfigManager {
	final private static String CONFIG_SAVE_PATH = "serverconfig";
	final private static Config<ModConfig> modConfigLoader = new Config<>("config.json", ModConfig.class);
	final private static Config<PlayersConfig> playersConfigLoader = new Config<>("players.json", PlayersConfig.class);
	private static int autoSave = 0;

	public static Path modDirectory;
	public static ModConfig modConfig = new ModConfig();
	public static PlayersConfig playersConfig = new PlayersConfig();

	public static void init(Server server) {
		try {
			modDirectory = server.getWorldPath().resolve(CONFIG_SAVE_PATH).resolve(StatsScoreboard.MOD_ID);
			if (!Files.exists(modDirectory)) {
				Files.createDirectories(modDirectory);
			}
			load();
			autoSave = 0;
		}
		catch (IOException e) {
			StatsScoreboard.LOGGER.error("An error occurred while loading config! ", e);
		}
	}

	public static void load() {
		modConfig = modConfigLoader.load(ModConfig::new);
		playersConfig = playersConfigLoader.load(PlayersConfig::new);
	}

	public static void save() {
		modConfigLoader.save(modConfig);
		playersConfigLoader.save(playersConfig);
	}

	public static void autoSaveTick() {
		autoSave++;
		if (autoSave >= modConfig.getConfigAutoSaveInterval() * 20) {
			autoSave = 0;
			save();
		}
	}
}
