package xyz.nekomilky.mcmod.statsscoreboard.utils.command;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;

public class RCommandContext implements CommandContext {
	final private com.mojang.brigadier.context.CommandContext<net.minecraft.commands.CommandSourceStack> core;

	public RCommandContext(com.mojang.brigadier.context.CommandContext<net.minecraft.commands.CommandSourceStack> core) {
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
