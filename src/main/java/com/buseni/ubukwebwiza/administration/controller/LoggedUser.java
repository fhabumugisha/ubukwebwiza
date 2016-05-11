package com.buseni.ubukwebwiza.administration.controller;

import java.io.Serializable;
import java.util.Date;

public class LoggedUser implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String		authorities ;
	private boolean accountNonExpired ;
	private boolean credentialsNonExpired ;
	private boolean 		accountNonLocked	;			
	private Date 		lastRequest;
		private String sessionId;
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getAuthorities() {
			return authorities;
		}
		public void setAuthorities(String authorities) {
			this.authorities = authorities;
		}
		public boolean isAccountNonExpired() {
			return accountNonExpired;
		}
		public void setAccountNonExpired(boolean accountNonExpired) {
			this.accountNonExpired = accountNonExpired;
		}
		public boolean isCredentialsNonExpired() {
			return credentialsNonExpired;
		}
		public void setCredentialsNonExpired(boolean credentialsNonExpired) {
			this.credentialsNonExpired = credentialsNonExpired;
		}
		public boolean isAccountNonLocked() {
			return accountNonLocked;
		}
		public void setAccountNonLocked(boolean accountNonLocked) {
			this.accountNonLocked = accountNonLocked;
		}
		public Date getLastRequest() {
			return lastRequest;
		}
		public void setLastRequest(Date lastRequest) {
			this.lastRequest = lastRequest;
		}
		public String getSessionId() {
			return sessionId;
		}
		public void setSessionId(String sessionId) {
			this.sessionId = sessionId;
		}
		@Override
		public String toString() {
			return "LoggedUser [username=" + username + ", authorities=" + authorities + ", accountNonExpired="
					+ accountNonExpired + ", credentialsNonExpired=" + credentialsNonExpired + ", accountNonLocked="
					+ accountNonLocked + ", lastRequest=" + lastRequest + ", sessionId=" + sessionId + "]";
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (accountNonExpired ? 1231 : 1237);
			result = prime * result + (accountNonLocked ? 1231 : 1237);
			result = prime * result + ((authorities == null) ? 0 : authorities.hashCode());
			result = prime * result + (credentialsNonExpired ? 1231 : 1237);
			result = prime * result + ((lastRequest == null) ? 0 : lastRequest.hashCode());
			result = prime * result + ((sessionId == null) ? 0 : sessionId.hashCode());
			result = prime * result + ((username == null) ? 0 : username.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			LoggedUser other = (LoggedUser) obj;
			if (accountNonExpired != other.accountNonExpired)
				return false;
			if (accountNonLocked != other.accountNonLocked)
				return false;
			if (authorities == null) {
				if (other.authorities != null)
					return false;
			} else if (!authorities.equals(other.authorities))
				return false;
			if (credentialsNonExpired != other.credentialsNonExpired)
				return false;
			if (lastRequest == null) {
				if (other.lastRequest != null)
					return false;
			} else if (!lastRequest.equals(other.lastRequest))
				return false;
			if (sessionId == null) {
				if (other.sessionId != null)
					return false;
			} else if (!sessionId.equals(other.sessionId))
				return false;
			if (username == null) {
				if (other.username != null)
					return false;
			} else if (!username.equals(other.username))
				return false;
			return true;
		}
		
		

}
