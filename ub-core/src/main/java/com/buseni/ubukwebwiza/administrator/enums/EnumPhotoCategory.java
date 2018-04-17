package com.buseni.ubukwebwiza.administrator.enums;

public enum EnumPhotoCategory {
		
	PROVIDER( 1 ) {
		@Override
		public String toString() {
			return "Provider";
		}
	},
	PROFILE( 2 ) {
		@Override
		public String toString() {
			return "Profile";
		}
	},
	HOME_PAGE( 3 ) {
		@Override
		public String toString() {
			return "Home Page";
		}
	};

	private Integer id;

	private EnumPhotoCategory( Integer id ) {
		this.id = id;
	}

	public String getName() {
		return toString();
	}

	public Integer getId() {
		return id;
	}
	
	public static String getNameFromId(Integer id){
		for(EnumPhotoCategory role : EnumPhotoCategory.values()){
			if(role.getId().equals(id)){
				return role.getName();
			}
		}
		return null;
	}
	
	
}
