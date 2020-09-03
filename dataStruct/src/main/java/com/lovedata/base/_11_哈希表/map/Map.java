package com.lovedata.base._11_哈希表.map;

public interface Map<K, V> {
	int size();
	boolean isEmpty();
	void clear();
	V put(K key, V value);
	V get(K key);
	V remove(K key);
	boolean containsKey(K key);
	boolean containsValue(V value);
	void traversal(Visitor<K, V> visitor);

	/**
	 * 提供给外部的遍历
	 * @param <K>
	 * @param <V>
	 */
	public static abstract class Visitor<K, V> {
		boolean stop;
		public abstract boolean visit(K key, V value);
	}
}
