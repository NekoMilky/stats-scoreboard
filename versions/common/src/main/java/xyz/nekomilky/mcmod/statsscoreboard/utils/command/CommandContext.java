package xyz.nekomilky.mcmod.statsscoreboard.utils.command;

public interface CommandContext {
	boolean getBoolArgument(String key);

	int getIntegerArgument(String key);

	CommandSourceStack getSource();

	String getStringArgument(String key);
}
