/**
 * 
 */
package com.buseni.ubukwebwiza.provider.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buseni.ubukwebwiza.exceptions.ResourceNotFoundException;
import com.buseni.ubukwebwiza.provider.beans.MessageDto;
import com.buseni.ubukwebwiza.provider.domain.Message;
import com.buseni.ubukwebwiza.provider.domain.MessageAnswer;
import com.buseni.ubukwebwiza.provider.domain.Provider;
import com.buseni.ubukwebwiza.provider.repository.MessageAnswerRepo;
import com.buseni.ubukwebwiza.provider.repository.MessageRepo;
import com.buseni.ubukwebwiza.provider.repository.ProviderRepo;
import com.buseni.ubukwebwiza.provider.service.MessageService;

/**
 * @author habumugisha
 *
 */
@Service
@Transactional(readOnly=true)
public class MessageServiceImpl implements MessageService {
	
	private MessageRepo messageRepo;
	private MessageAnswerRepo messageAnswerRepo;
	private	ProviderRepo providerRepo;
	
	@Autowired
	public MessageServiceImpl(MessageRepo messageRepo,ProviderRepo providerRepo, MessageAnswerRepo messageAnswerRepo) {
		super();
		this.messageRepo = messageRepo;
		this.providerRepo = providerRepo;
		this.messageAnswerRepo = messageAnswerRepo;
	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.provider.service.MessageService#listProviderMessages(java.lang.Integer, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Message> listProviderMessages(Integer idProvider, Pageable pageable) {
		if(null == idProvider){
			throw new NullPointerException("idProvider should be null");
		}	
		PageRequest pr = new PageRequest(pageable.getPageNumber()-1, pageable.getPageSize());
		return messageRepo.findByProvider_id(idProvider, pr);
	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.provider.service.MessageService#findById(java.lang.Integer)
	 */
	@Override
	public Message findById(Integer idMessage) {
		if(null == idMessage){
			throw new NullPointerException("idMessage should be null");
		}		
		Message message  = messageRepo.findOne(idMessage);
		if(message ==  null){
			throw new ResourceNotFoundException();
		}	
		return message;
	}
	
	@Override
	@Transactional
	public MessageDto contactProvider(MessageDto messageDto) {
		if(messageDto == null){
			throw new NullPointerException();
		}
		Message message = new Message();
		message.setComment(messageDto.getComment());
		message.setSubject(messageDto.getSubject());
		message.setSenderEmail(messageDto.getSenderEmail());
		message.setSenderName(messageDto.getSenderName());
		message.setSenderPhonenumber(messageDto.getSenderPhonenumber());
		Provider provider =  providerRepo.findOne(messageDto.getIdProvider());
		message.setProvider(provider);	
		message.setCreatedAt(new Date());
		messageRepo.save(message);
		
		messageDto.setProviderEmail(provider.getAccount().getEmail());
		messageDto.setProviderName(provider.getBusinessName());
		messageDto.setProviderUrlName(provider.getUrlName());
		return messageDto;
		
	}
	@Transactional
	@Override
	public void answerMessage(MessageAnswer messageAnswer) {
		if(messageAnswer == null){
			throw new NullPointerException();
		}
		messageAnswer.setCreatedAt(new Date());
		messageAnswerRepo.save(messageAnswer);
	}

}
