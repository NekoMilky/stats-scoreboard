package xyz.nekomilky.mcmod.statsscoreboard.utils.chat;

import net.minecraft.ChatFormatting;

public class RMutableComponent implements MutableComponent {
	private net.minecraft.network.chat.MutableComponent core;

	public RMutableComponent(net.minecraft.network.chat.MutableComponent core) {
		this.core = core;
	}

	@Override
	public net.minecraft.network.chat.MutableComponent getCore() {
		return this.core;
	}

	@Override
	public RMutableComponent append(MutableComponent component) {
		this.core = this.core.append(((RMutableComponent) component).getCore());
		return this;
	}

	@Override
	public RMutableComponent setStyle(String style) {
		for (ChatFormatting s : ChatFormatting.values()) {
			if (s.getName().equals(style.toLowerCase())) {
				this.core.withStyle(s);
				break;
			}
		}
		return this;
	}
}
