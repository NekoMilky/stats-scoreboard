package xyz.nekomilky.mcmod.statsscoreboard.utils.chat;

import net.minecraft.network.chat.Component;

import java.util.Arrays;

public class RComponentProvider implements ComponentProvider {
	@Override
	public RMutableComponent empty() {
		return new RMutableComponent(Component.empty());
	}

	@Override
	public RMutableComponent literal(String text) {
		return new RMutableComponent(Component.literal(text));
	}

	@Override
	public RMutableComponent translate(String key) {
		return new RMutableComponent(Component.translatable(key));
	}

	@Override
	public RMutableComponent translate(String key, Object... args) {
		Object[] parsedArgs = Arrays.stream(args)
		    .map(this::unwrap)
		    .toArray();
		return new RMutableComponent(Component.translatable(key, parsedArgs));
	}

	private Object unwrap(Object arg) {
		if (arg instanceof RMutableComponent component) {
			return unwrap(component.getCore());
		}
		return arg;
	}
}
