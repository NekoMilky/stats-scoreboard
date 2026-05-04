package xyz.nekomilky.mcmod.statsscoreboard.utils.command;

import java.util.function.Function;

public class CommandLiteralNode extends CommandNode {
	public boolean isOpOnly = false;

	public CommandLiteralNode execute(Function<CommandContext, Integer> c) {
		this.callback = c;
		return this;
	}

	public CommandLiteralNode requireOp() {
		this.isOpOnly = true;
		return this;
	}

	public CommandLiteralNode then(CommandNode child) {
		this.children.add(child);
		return this;
	}

	public static CommandLiteralNode create(String text) {
		CommandLiteralNode node = new CommandLiteralNode();
		node.nodeName = text;
		return node;
	}
}
