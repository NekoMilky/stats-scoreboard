package xyz.nekomilky.mcmod.statsscoreboard.commands.suggestions;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import xyz.nekomilky.mcmod.statsscoreboard.configs.SSBConfigManager;

import java.util.concurrent.CompletableFuture;

public class SelectedCriteriaSuggestionProvider implements SuggestionProvider<CommandSourceStack> {
	@Override
	public CompletableFuture<Suggestions> getSuggestions(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
		var player = context.getSource().getEntity();
		if (!(player instanceof ServerPlayer)) {
			return builder.buildFuture();
		}
		String remaining = builder.getRemaining();
		for (var criteria : SSBConfigManager.playersConfig.getSelectedCriteria((ServerPlayer) player)) {
			if (criteria.startsWith(remaining)) {
				builder.suggest(criteria);
			}
		}
		return builder.buildFuture();
	}
}
