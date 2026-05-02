package xyz.nekomilky.mcmod.statsscoreboard.mixins;

import net.minecraft.stats.Stat;
import net.minecraft.stats.StatsCounter;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.nekomilky.mcmod.statsscoreboard.events.PlayerStatChangeCallback;

@Mixin(StatsCounter.class)
public class StatsCounterMixin {
	@Inject(
		method = "increment",
		at = @At("RETURN")
	)
	private void onIncreasing(Player player, Stat<?> stat, int count, CallbackInfo ci) {
		PlayerStatChangeCallback.EVENT.invoker().interact(player, stat, count);
	}
}
