package xyz.nekomilky.mcmod.statsscoreboard.commands.suggestions;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandSourceStack;
import xyz.nekomilky.mcmod.statsscoreboard.utils.Criteria;

import java.util.concurrent.CompletableFuture;

public class CriteriaSuggestionProvider implements SuggestionProvider<CommandSourceStack> {
	@Override
	public CompletableFuture<Suggestions> getSuggestions(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
		String remaining = builder.getRemaining();
		for (var criteria : Criteria.get()) {
			if (criteria.startsWith(remaining)) {
				builder.suggest(criteria);
			}
		}
		return builder.buildFuture();
	}
}
