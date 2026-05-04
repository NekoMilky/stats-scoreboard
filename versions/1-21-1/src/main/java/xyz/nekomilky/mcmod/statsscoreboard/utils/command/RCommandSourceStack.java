package xyz.nekomilky.mcmod.statsscoreboard.utils.command;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import xyz.nekomilky.mcmod.statsscoreboard.utils.chat.MutableComponent;
import xyz.nekomilky.mcmod.statsscoreboard.utils.chat.RMutableComponent;
import xyz.nekomilky.mcmod.statsscoreboard.utils.server.Player;

public class RCommandSourceStack implements CommandSourceStack {
	final private ServerCommandSource core;

	public RCommandSourceStack(ServerCommandSource core) {
		this.core = core;
	}

	@Override
	public Object getEntity() {
		var entity = this.core.getEntity();
		return entity instanceof ServerPlayerEntity ? Player.getPlayer(entity) : entity;
	}

	@Override
	public void sendFailure(MutableComponent component) {
		this.core.sendError(((RMutableComponent) component).getCore());
	}

	@Override
	public void sendSuccess(MutableComponent component, boolean isOperation) {
		this.core.sendMessage(((RMutableComponent) component).getCore());
	}
}
