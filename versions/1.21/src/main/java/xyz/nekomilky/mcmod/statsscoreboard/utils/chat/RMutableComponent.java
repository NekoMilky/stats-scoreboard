package xyz.nekomilky.mcmod.statsscoreboard.utils.chat;

import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;

public class RMutableComponent implements MutableComponent {
	private MutableText core;

	public RMutableComponent(MutableText core) {
		this.core = core;
	}

	@Override
	public MutableText getCore() {
		return this.core;
	}

	@Override
	public RMutableComponent append(MutableComponent component) {
		this.core = this.core.append(((RMutableComponent) component).getCore());
		return this;
	}

	@Override
	public RMutableComponent setStyle(String style) {
		for (Formatting s : Formatting.values()) {
			if (s.getName().equals(style.toLowerCase())) {
				this.core.setStyle(Style.EMPTY.withFormatting(s));
				break;
			}
		}
		return this;
	}
}
