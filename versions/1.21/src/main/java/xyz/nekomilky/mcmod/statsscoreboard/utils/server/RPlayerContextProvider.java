package xyz.nekomilky.mcmod.statsscoreboard.utils.server;

import net.minecraft.server.network.ServerPlayerEntity;

public class RPlayerContextProvider implements PlayerContextProvider {
	@Override
	public Player getPlayer(Object player) {
		return new Player(new RPlayerProvider((ServerPlayerEntity) player));
	}
}
