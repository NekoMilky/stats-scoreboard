package xyz.nekomilky.mcmod.statsscoreboard.utils.sidebar;

import xyz.nekomilky.mcmod.statsscoreboard.utils.server.Player;
import xyz.nekomilky.mcmod.statsscoreboard.utils.chat.MutableComponent;

public interface SidebarProvider {
	SidebarProvider create();

	void addLine(int value, String text);

	void addPlayer(Player player);

	void clear();

	void removePlayer(Player player);

	void setTitle(MutableComponent title);

	void show();
}
