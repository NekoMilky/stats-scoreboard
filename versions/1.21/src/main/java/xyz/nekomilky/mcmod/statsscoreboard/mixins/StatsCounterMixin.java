package xyz.nekomilky.mcmod.statsscoreboard.mixins;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stat;
import net.minecraft.stat.StatHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.stat.StatType;
import net.minecraft.stat.Stats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.nekomilky.mcmod.statsscoreboard.utils.event.PlayerStatIncrease;
import xyz.nekomilky.mcmod.statsscoreboard.utils.server.Player;
import xyz.nekomilky.mcmod.statsscoreboard.utils.server.Server;

import java.util.List;

@Mixin(StatHandler.class)
public class StatsCounterMixin {
	@Unique
	final private static List<StatType<?>> allowedStatTypes = List.of(new StatType<?>[] {
		Stats.MINED,
		Stats.CUSTOM,
		Stats.KILLED,
		Stats.KILLED_BY,
		Stats.BROKEN,
		Stats.CRAFTED,
		Stats.DROPPED,
		Stats.PICKED_UP,
		Stats.USED
	});

	@Inject(
		method = "increaseStat",
		at = @At("RETURN")
	)
	public void onIncreasing(PlayerEntity player, Stat<?> stat, int count, CallbackInfo ci) {
		if (!allowedStatTypes.contains(stat.getType())) {
			return;
		}
		MinecraftServer server = (MinecraftServer) Server.getServerCore();
		ServerPlayerEntity serverPlayerEntity = server.getPlayerManager().getPlayer(player.getUuid());
		PlayerStatIncrease.EVENT.invoker().interact(Player.getPlayer(serverPlayerEntity), stat.getName(), count);
	}
}
