package xyz.nekomilky.mcmod.statsscoreboard.utils.event;

@FunctionalInterface
public interface TriConsumer<A, B, C> {
	void accept(A a, B b, C c);
}
