package xyz.nekomilky.mcmod.statsscoreboard.utils.command;

import java.util.function.Function;

public class CommandArgumentNode extends CommandNode {
	public String argumentType = null;
	public SuggestionProvider suggester = null;

	public CommandArgumentNode execute(Function<CommandContext, Integer> c) {
		this.callback = c;
		return this;
	}

	public CommandArgumentNode suggest(SuggestionProvider s) {
		this.suggester = s;
		return this;
	}

	public CommandArgumentNode then(CommandNode child) {
		this.children.add(child);
		return this;
	}

	public static CommandArgumentNode create(String key, String type) {
		CommandArgumentNode node = new CommandArgumentNode();
		node.nodeName = key;
		node.argumentType = type;
		return node;
	}
}
