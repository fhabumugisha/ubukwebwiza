package com.buseni.ubukwebwiza.provider.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.buseni.ubukwebwiza.provider.beans.MessageDto;
import com.buseni.ubukwebwiza.provider.domain.Message;
import com.buseni.ubukwebwiza.provider.domain.MessageAnswer;

public interface MessageService {
	
	/**
	 * List messages of a provider
	 * @param idProvider : id of the provider
	 * @param pageable : paging info
	 * @return paged list of messages
	 */
	Page<Message> listProviderMessages(Integer idProvider, Pageable pageable);
	/**
	 * Find a message by id
	 * @param idMessage : id of the message
	 * @return
	 */
	Message findById(Integer idMessage);
	
	
	/**
	 * Send a mail to the provider and saves and message in the table "message"
	 * @param message : Object containing mail to be send
	 * @return 
	 */
	MessageDto contactProvider(MessageDto message);
	/**
	 * Answer a message
	 * @param messageAnswer
	 */
	void answerMessage(MessageAnswer messageAnswer);

}
