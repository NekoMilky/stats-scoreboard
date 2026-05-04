package xyz.nekomilky.mcmod.statsscoreboard.utils.command;

import net.minecraft.server.level.ServerPlayer;
import xyz.nekomilky.mcmod.statsscoreboard.utils.chat.MutableComponent;
import xyz.nekomilky.mcmod.statsscoreboard.utils.chat.RMutableComponent;
import xyz.nekomilky.mcmod.statsscoreboard.utils.server.Player;

public class RCommandSourceStack implements CommandSourceStack {
	final private net.minecraft.commands.CommandSourceStack core;

	public RCommandSourceStack(net.minecraft.commands.CommandSourceStack core) {
		this.core = core;
	}

	@Override
	public Object getEntity() {
		var entity = this.core.getEntity();
		return entity instanceof ServerPlayer ? Player.getPlayer(entity) : entity;
	}

	@Override
	public void sendFailure(MutableComponent component) {
		this.core.sendFailure(((RMutableComponent) component).getCore());
	}

	@Override
	public void sendSuccess(MutableComponent component, boolean isOperation) {
		this.core.sendSuccess(() -> ((RMutableComponent) component).getCore(), isOperation);
	}
}
