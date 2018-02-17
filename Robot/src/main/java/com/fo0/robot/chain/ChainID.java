package com.fo0.robot.chain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder
@Data
@EqualsAndHashCode(of = { "id" })
@NoArgsConstructor
@AllArgsConstructor
public class ChainID implements Comparable<ChainID>, Serializable {

	private static final long serialVersionUID = 7866533922068192942L;

	@Builder.Default
	private long id = 0;

	@Builder.Default
	private String name = "";

	@Builder.Default
	private String description = "";

	public String info() {
		return String.format("ID: %s, Name: %s", id, name);
	}

	@Override
	public int compareTo(ChainID o) {
		return Long.compare(id, o.getId());
	}

}
