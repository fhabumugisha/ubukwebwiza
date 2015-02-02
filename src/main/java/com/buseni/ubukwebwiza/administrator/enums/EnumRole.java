package com.buseni.ubukwebwiza.administrator.enums;

public enum EnumRole {
		
	ADMIN( 1 ) {
		@Override
		public String toString() {
			return "ADMIN";
		}
	},
	SUPER_ADMIN( 2 ) {
		@Override
		public String toString() {
			return "SUPER_ADMIN";
		}
	};

	private Integer id;

	private EnumRole( Integer id ) {
		this.id = id;
	}

	public String getName() {
		return toString();
	}

	public Integer getId() {
		return id;
	}
}
