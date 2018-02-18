package com.fo0.robot.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypedValue<K, V> implements Serializable, Cloneable {

	private static final long serialVersionUID = 6558027168858922506L;

	private K key;
	private V value;

}
