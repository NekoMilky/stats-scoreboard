package xyz.nekomilky.mcmod.statsscoreboard.utils.command;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CommandNode {
	final public List<CommandNode> children = new ArrayList<>();
	public Function<CommandContext, Integer> callback = null;
	public String nodeName = null;
}
