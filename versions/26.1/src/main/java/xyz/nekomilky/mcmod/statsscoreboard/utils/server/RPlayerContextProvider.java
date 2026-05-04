package xyz.nekomilky.mcmod.statsscoreboard.utils.server;

import net.minecraft.server.level.ServerPlayer;

public class RPlayerContextProvider implements PlayerContextProvider {
	@Override
	public Player getPlayer(Object player) {
		return new Player(new RPlayerProvider((ServerPlayer) player));
	}
}
