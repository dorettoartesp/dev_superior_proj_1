package com.devsupprojects.cap1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsupprojects.cap1.entitities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}
