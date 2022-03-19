package com.devsupprojects.cap1.services;


import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsupprojects.cap1.dtos.ClientDTO;
import com.devsupprojects.cap1.entitities.Client;
import com.devsupprojects.cap1.repositories.ClientRepository;
import com.devsupprojects.cap1.services.exceptions.DatabaseException;
import com.devsupprojects.cap1.services.exceptions.NotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repository;
	
	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
		Page<Client> list = repository.findAll(pageRequest);
		return list.map(client -> new ClientDTO(client));
	}
	
	@Transactional(readOnly = true)
	public ClientDTO getOne(Long id) {
		Optional<Client> result = repository.findById(id);
		Client entity = result.orElseThrow(() -> new NotFoundException("Entity not found"));
		return new ClientDTO(entity);
	}
	
	@Transactional
	public ClientDTO createOne(ClientDTO dto) {
		Client entity = new Client();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		
		return new ClientDTO(entity);
	}
	
	@Transactional
	public ClientDTO updateOne(ClientDTO dto, Long id) {
		try {
			Client entity = repository.getOne(id);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new ClientDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new DatabaseException("Entity not found");
		}
	}
	
	public void deleteOne(Long id) {
		try {
			repository.deleteById(id);			
		}
		catch(EmptyResultDataAccessException e) {
			throw new NotFoundException("Id " + id + " não encontrado");
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Violação de integridade");
		}
	}

	private void copyDtoToEntity(ClientDTO dto, Client entity) {
		entity.setName(dto.getName());
		entity.setCpf(dto.getCpf());
		entity.setBirthDate(dto.getBirthDate());
		entity.setIncome(dto.getIncome());
		entity.setChildren(dto.getChildren());
	}
}
