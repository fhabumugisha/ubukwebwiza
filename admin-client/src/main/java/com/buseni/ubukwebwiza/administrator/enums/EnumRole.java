package com.buseni.ubukwebwiza.administrator.enums;

public enum EnumRole {
		
	ROLE_ADMIN( 1 ) {
		@Override
		public String toString() {
			return "ROLE_ADMIN";
		}
	},
	ROLE_SUPER_ADMIN( 2 ) {
		@Override
		public String toString() {
			return "ROLE_SUPER_ADMIN";
		}
	},
	ROLE_USER( 3 ) {
		@Override
		public String toString() {
			return "ROLE_USER";
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
	
	public static String getNameFromId(Integer id){
		for(EnumRole role : EnumRole.values()){
			if(role.getId().equals(id)){
				return role.getName();
			}
		}
		return null;
	}
	
	
}
