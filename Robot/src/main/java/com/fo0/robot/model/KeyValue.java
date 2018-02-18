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
public class KeyValue implements Serializable, Cloneable {

	/**
	 *
	 */
	private static final long serialVersionUID = 7357932798701445853L;

	private String key;
	private String value;

}
