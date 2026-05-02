package xyz.nekomilky.mcmod.statsscoreboard.events;

public class SSBEvents {
	public static void register() {
		// config
		ConfigRelated.init();
		// player
		PlayerRelated.init();
	}
}
