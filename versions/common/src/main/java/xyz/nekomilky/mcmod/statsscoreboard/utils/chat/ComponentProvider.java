package xyz.nekomilky.mcmod.statsscoreboard.utils.chat;

public interface ComponentProvider {
	MutableComponent empty();

	MutableComponent literal(String text);

	MutableComponent translate(String key);

	MutableComponent translate(String key, Object... args);
}
