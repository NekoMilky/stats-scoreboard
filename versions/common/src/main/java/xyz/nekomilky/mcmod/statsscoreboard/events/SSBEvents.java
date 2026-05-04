package xyz.nekomilky.mcmod.statsscoreboard.events;

public class SSBEvents {
	public static void register() {
		// config
		ServerEvents.init();
		// player
		PlayerEvents.init();
	}
}
