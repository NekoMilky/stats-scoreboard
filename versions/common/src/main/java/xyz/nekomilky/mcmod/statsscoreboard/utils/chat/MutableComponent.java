package xyz.nekomilky.mcmod.statsscoreboard.utils.chat;

public interface MutableComponent {
	Object getCore();

	MutableComponent append(MutableComponent component);

	MutableComponent setStyle(String style);
}
