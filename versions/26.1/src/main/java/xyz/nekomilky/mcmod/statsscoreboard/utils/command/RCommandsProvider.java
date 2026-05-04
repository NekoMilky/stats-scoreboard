package xyz.nekomilky.mcmod.statsscoreboard.utils.command;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.permissions.Permissions;

import java.util.Map;
import java.util.function.Supplier;

public class RCommandsProvider implements CommandsProvider {
	private static final Map<String, Supplier<ArgumentType<?>>> ARGUMENT_TYPES = Map.ofEntries(
		Map.entry("String", StringArgumentType::string),
		Map.entry("Integer", IntegerArgumentType::integer),
		Map.entry("Bool", BoolArgumentType::bool)
	);

	@Override
	public void register(CommandLiteralNode root, String alias) {
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			var rootNode = dispatcher.register(resolveLiteralNode(root));
			if (alias != null) {
				var redirectNode = Commands.literal(alias).redirect(rootNode);
				if (root.callback != null) {
					redirectNode = redirectNode.executes((context) -> root.callback.apply(new RCommandContext(context)));
				}
				dispatcher.register(redirectNode);
			}
		});
	}

	private LiteralArgumentBuilder<CommandSourceStack> resolveLiteralNode(CommandLiteralNode node) {
		LiteralArgumentBuilder<CommandSourceStack> result = Commands.literal(node.nodeName);
		if (node.isOpOnly) {
			result = result.requires((source) -> source.permissions().hasPermission(Permissions.COMMANDS_ADMIN));
		}
		result = resolveCommon(node, result);
		return result;
	}

	private RequiredArgumentBuilder<CommandSourceStack, ?> resolveArgumentNode(CommandArgumentNode node) throws IllegalArgumentException {
		String name = node.nodeName;
		Supplier<ArgumentType<?>> supplier = ARGUMENT_TYPES.get(node.argumentType);
		if (supplier == null) {
			throw new IllegalArgumentException("Unknown argument type: " + node.argumentType);
		}
		RequiredArgumentBuilder<CommandSourceStack, ?> result = Commands.argument(name, supplier.get());
		if (node.suggester != null) {
			result = result.suggests((context, builder) -> {
				String remaining = builder.getRemaining();
				for (var entry : node.suggester.getSuggestions(new RCommandContext(context))) {
					if (entry.startsWith(remaining)) {
						builder.suggest(entry);
					}
				}
				return builder.buildFuture();
			});
		}
		result = (RequiredArgumentBuilder<CommandSourceStack, ?>) resolveCommon(node, result);
		return result;
	}

	private <T extends ArgumentBuilder<CommandSourceStack, T>> T resolveCommon(CommandNode node, T result) {
		if (node.callback != null) {
			result = result.executes((context) -> node.callback.apply(new RCommandContext(context)));
		}
		if (!node.children.isEmpty()) {
			for (CommandNode child : node.children) {
				if (child instanceof CommandLiteralNode literal) {
					result = result.then(resolveLiteralNode(literal));
				}
				else if (child instanceof CommandArgumentNode argument) {
					result = result.then(resolveArgumentNode(argument));
				}
			}
		}
		return result;
	}
}
