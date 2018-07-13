package com.catsbank.db.repository;

import com.catsbank.db.entities.Cat;
import org.springframework.data.repository.CrudRepository;

public interface CatsRepository extends CrudRepository<Cat,Integer> {}
