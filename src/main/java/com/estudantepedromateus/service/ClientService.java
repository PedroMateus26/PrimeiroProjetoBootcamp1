package com.estudantepedromateus.service;



import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.estudantepedromateus.dto.ClientDTO;
import com.estudantepedromateus.entity.Client;
import com.estudantepedromateus.exceptions.DataBaseException;
import com.estudantepedromateus.exceptions.ResourceNotFoundException;
import com.estudantepedromateus.repositories.ClientRepository;


@Service
public class ClientService implements UserDetailsService {

	@Autowired
	public ClientRepository repository;
	
	@Transactional(readOnly = true)
	public Page<ClientDTO> findAll(PageRequest pageRequest){
		Page<Client> list=repository.findAll(pageRequest);
		return list.map(x->new ClientDTO(x));
	}
	
	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Optional<Client> obj=repository.findById(id);
		Client entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entitiy not found"));
		return new ClientDTO(entity);
		
	}

	@Transactional
	public ClientDTO insert(ClientDTO dto) {
		Client entity= new Client();
		entity = makeEntity(dto,entity);
		entity=repository.save(entity);
		return new ClientDTO(entity);
	}

	@Transactional
	public ClientDTO update(Long id, ClientDTO dto) {
		try {
			Client entity=repository.getOne(id);
			entity = makeEntity(dto,entity);
			entity =repository.save(entity);
			return new ClientDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found "+id);
		}
		
	}
	
	@Transactional
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found "+id);
		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Integrity violation");
		}
		
	}

	
	private Client makeEntity(ClientDTO dto, Client entity) {
		BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
		entity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
		entity.setName(dto.getName());
		entity.setCpf(dto.getCpf());
		entity.setIncome(dto.getIncome());
		entity.setChildren(dto.getChildren());
		entity.setBirthDate(dto.getBirthDate());
		entity.setEmail(dto.getEmail());
		return entity;
	}

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		Client client = repository.findByEmail(s).orElseThrow(()->new UsernameNotFoundException("Client not found"));
		return client;
	}

}
