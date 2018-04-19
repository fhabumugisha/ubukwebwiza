/**
 * 
 */
package com.buseni.ubukwebwiza.provider.service.impl;

import static java.lang.Math.toIntExact;

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
		PageRequest pr = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
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
		Message message  = messageRepo.findById(idMessage).orElse(null);
		if(message ==  null){
			throw new ResourceNotFoundException();
		}	
		return message;
	}
	
	@Override
	@Transactional
	public Message read(Integer idMessage) {
		if(null == idMessage){
			throw new NullPointerException("idMessage should be null");
		}		
		Message message  = messageRepo.findById(idMessage).orElse(null);
		if(message ==  null){
			throw new ResourceNotFoundException();
		}	
		message.setReaded(true);
		message.setLastUpdate(new Date());
		return messageRepo.save(message);
		
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
		Provider provider =  providerRepo.findById(messageDto.getIdProvider()).orElseThrow(()-> new NullPointerException(" shouldn't be null"));
		message.setProvider(provider);	
		message.setCreatedAt(new Date());
		messageRepo.save(message);
		
		messageDto.setProviderEmail(provider.getAccount().getEmail());
		messageDto.setProviderName(provider.getBusinessName());
		messageDto.setProviderUrlName(provider.getUrlName());
		messageDto.setId(message.getId());
		return messageDto;
		
	}
	@Transactional
	@Override
	public MessageAnswer answerMessage(MessageAnswer messageAnswer) {
		if(messageAnswer == null){
			throw new NullPointerException();
		}
		messageAnswer.setCreatedAt(new Date());
		Message message = messageRepo.findById(messageAnswer.getMessage().getId()).orElseThrow(()-> new NullPointerException(" shouldn't be null"));
		messageAnswer.setMessage(message);
		
		MessageAnswer updated = messageAnswerRepo.save(messageAnswer);
		if(updated.isFromUser()){
			updated.getMessage().setReaded(false);
			messageRepo.save(updated.getMessage());
		}
		return updated;
	}

	@Override
	public MessageAnswer findMessageAnswerById(Integer idMessageAnswer) {
		if(idMessageAnswer == null){
			throw new NullPointerException();
		}
		MessageAnswer messageAnswer = messageAnswerRepo.findById(idMessageAnswer).orElse(null);
		if(messageAnswer == null){
			throw new NullPointerException();
		}
		return messageAnswer;
	}

	@Override
	public int findUnreadProviderMessages(Integer idProvider) {
		if(idProvider == null){
			throw new NullPointerException();
		}
		long unreadMsg = messageRepo.countByProvider_idAndReaded(idProvider, false);
		return toIntExact(unreadMsg);
	}

	@Override
	public Page<Message> findAll(Pageable page) {
		PageRequest pr = PageRequest.of(page.getPageNumber(), page.getPageSize());
		return messageRepo.findAll(pr);
	}

}
