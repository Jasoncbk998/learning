package com.lovedata.base._10_映射.set;

public interface Set<E> {
	int size();
	boolean isEmpty();
	void clear();
	boolean contains(E element);
	void add(E element);
	void remove(E element);
	void traversal(Visitor<E> visitor);

	/**
	 * 提供遍历接口
	 * @param <E>
	 */
	public static abstract class Visitor<E> {
		boolean stop;
		public abstract boolean visit(E element);
	}
}
