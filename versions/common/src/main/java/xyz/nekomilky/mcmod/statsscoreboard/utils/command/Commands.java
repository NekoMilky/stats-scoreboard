package xyz.nekomilky.mcmod.statsscoreboard.utils.command;

public class Commands {
	private static CommandsProvider commandsProvider = null;

	public static void setCommandProvider(CommandsProvider provider) {
		commandsProvider = provider;
	}

	public static void register(CommandLiteralNode root, String alias) {
		commandsProvider.register(root, alias);
	}
}
