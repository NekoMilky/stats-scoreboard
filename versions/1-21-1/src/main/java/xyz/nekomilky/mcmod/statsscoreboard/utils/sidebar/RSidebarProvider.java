package xyz.nekomilky.mcmod.statsscoreboard.utils.sidebar;

import eu.pb4.sidebars.api.Sidebar;
import eu.pb4.sidebars.api.lines.SidebarLine;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import xyz.nekomilky.mcmod.statsscoreboard.utils.chat.MutableComponent;
import xyz.nekomilky.mcmod.statsscoreboard.utils.chat.RMutableComponent;
import xyz.nekomilky.mcmod.statsscoreboard.utils.server.Player;

public class RSidebarProvider implements SidebarProvider {
	final private Sidebar sidebar = new Sidebar(Sidebar.Priority.HIGH);

	@Override
	public SidebarProvider create() {
		return new RSidebarProvider();
	}

	@Override
	public void addLine(int value, String text) {
		sidebar.addLines(SidebarLine.create(value, Text.literal(text)));
	}

	@Override
	public void addPlayer(Player player) {
		sidebar.addPlayer((ServerPlayerEntity) player.getProvider().getCore());
	}

	@Override
	public void clear() {
		sidebar.clearLines();
	}

	@Override
	public void removePlayer(Player player) {
		sidebar.removePlayer((ServerPlayerEntity) player.getProvider().getCore());
	}

	@Override
	public void setTitle(MutableComponent title) {
		sidebar.setTitle(((RMutableComponent) title).getCore());
	}

	@Override
	public void show() {
		sidebar.show();
	}
}
