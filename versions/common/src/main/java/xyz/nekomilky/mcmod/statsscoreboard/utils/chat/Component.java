package xyz.nekomilky.mcmod.statsscoreboard.utils.chat;

public class Component {
	private static ComponentProvider componentProvider = null;

	public static void setComponentProvider(ComponentProvider provider) {
		componentProvider = provider;
	}

	public static MutableComponent empty() {
		return componentProvider.empty();
	}

	public static MutableComponent literal(String text) {
		return componentProvider.literal(text);
	}

	public static MutableComponent translate(String key) {
		return componentProvider.translate(key);
	}

	public static MutableComponent translate(String key, Object... args) {
		return componentProvider.translate(key, args);
	}
}
