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
	 * Counts the unread messages
	 * @param idProvider
	 * @return
	 */
	int findUnreadProviderMessages(Integer idProvider);
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
	MessageAnswer answerMessage(MessageAnswer messageAnswer);
	/**
	 * Finds a message answer by id
	 * @param idMessageAnswer : id if the message answer
	 * @return
	 */
	MessageAnswer findMessageAnswerById(Integer idMessageAnswer);
	/**
	 * Readsa a message ( set readed to true and lastupdate to new date())
	 * @param idMessage id of the message
	 * @return
	 */
	Message read(Integer idMessage);
	/**
	 * list all messages
	 */
	Page<Message> findAll(Pageable page);

}
