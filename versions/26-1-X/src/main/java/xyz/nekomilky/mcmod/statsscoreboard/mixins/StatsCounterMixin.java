package xyz.nekomilky.mcmod.statsscoreboard.mixins;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatType;
import net.minecraft.stats.Stats;
import net.minecraft.stats.StatsCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.nekomilky.mcmod.statsscoreboard.utils.event.PlayerStatIncrease;
import xyz.nekomilky.mcmod.statsscoreboard.utils.server.Player;
import xyz.nekomilky.mcmod.statsscoreboard.utils.server.Server;

import java.util.List;

@Mixin(StatsCounter.class)
public class StatsCounterMixin {
	@Unique
	final private static List<StatType<?>> allowedStatTypes = List.of(new StatType<?>[] {
		Stats.BLOCK_MINED,
		Stats.CUSTOM,
		Stats.ENTITY_KILLED,
		Stats.ENTITY_KILLED_BY,
		Stats.ITEM_BROKEN,
		Stats.ITEM_CRAFTED,
		Stats.ITEM_DROPPED,
		Stats.ITEM_PICKED_UP,
		Stats.ITEM_USED
	});

	@Inject(
		method = "increment",
		at = @At("RETURN")
	)
	private void onIncreasing(net.minecraft.world.entity.player.Player player, Stat<?> stat, int count, CallbackInfo ci) {
		if (!allowedStatTypes.contains(stat.getType())) {
			return;
		}
		MinecraftServer server = (MinecraftServer) Server.getServerCore();
		ServerPlayer serverPlayer = server.getPlayerList().getPlayer(player.getUUID());
		PlayerStatIncrease.EVENT.invoker().interact(Player.getPlayer(serverPlayer), stat.getName(), count);
	}
}
