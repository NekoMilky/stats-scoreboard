package xyz.nekomilky.mcmod.statsscoreboard.utils;

public class ExecutionResult<T> {
	final public boolean succeed;
	final public T result;

	public ExecutionResult(boolean s, T r) {
		this.succeed = s;
		this.result = r;
	}
}
