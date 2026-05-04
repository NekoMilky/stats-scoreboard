package xyz.nekomilky.mcmod.statsscoreboard.utils.command;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.server.command.ServerCommandSource;

public class RCommandContext implements CommandContext {
	final private com.mojang.brigadier.context.CommandContext<ServerCommandSource> core;

	public RCommandContext(com.mojang.brigadier.context.CommandContext<ServerCommandSource> core) {
		this.core = core;
	}

	@Override
	public boolean getBoolArgument(String key) {
		return BoolArgumentType.getBool(this.core, key);
	}

	@Override
	public int getIntegerArgument(String key) {
		return IntegerArgumentType.getInteger(this.core, key);
	}

	@Override
	public RCommandSourceStack getSource() {
		return new RCommandSourceStack(this.core.getSource());
	}

	@Override
	public String getStringArgument(String key) {
		return StringArgumentType.getString(this.core, key);
	}
}
