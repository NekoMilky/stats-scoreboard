package xyz.nekomilky.mcmod.statsscoreboard;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.nekomilky.mcmod.statsscoreboard.commands.SSBCommands;
import xyz.nekomilky.mcmod.statsscoreboard.events.SSBEvents;
import xyz.nekomilky.mcmod.statsscoreboard.utils.SSBUtils;
import xyz.nekomilky.mcmod.statsscoreboard.utils.chat.Component;
import xyz.nekomilky.mcmod.statsscoreboard.utils.chat.MutableComponent;

public class StatsScoreboard implements ModInitializer {
	final public static String DEVELOPER = "NekoMilky";
	final public static String MOD_NAME = "Stats Scoreboard";
	final public static String MOD_ID = "StatsScoreboard";
	final public static Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static MutableComponent MOD_CHAT_PREFIX;

	@Override
	public void onInitialize() {
		// init utils
		SSBUtils.init();
		// init commands
		SSBCommands.register();
		// init events
		SSBEvents.register();
		// init others
		MOD_CHAT_PREFIX = Component.literal("[" + MOD_NAME + "] ").setStyle("LIGHT_PURPLE");
	}
}
