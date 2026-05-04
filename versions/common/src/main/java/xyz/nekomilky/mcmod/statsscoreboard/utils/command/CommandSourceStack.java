package xyz.nekomilky.mcmod.statsscoreboard.utils.command;

import xyz.nekomilky.mcmod.statsscoreboard.utils.chat.MutableComponent;

public interface CommandSourceStack {
	Object getEntity();

	void sendFailure(MutableComponent component);

	void sendSuccess(MutableComponent component, boolean isOperation);
}
