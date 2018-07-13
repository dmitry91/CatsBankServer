package com.catsbank.db.dao;

import com.catsbank.db.entities.Cat;
import org.springframework.data.repository.CrudRepository;

public interface CatsRepository extends CrudRepository<Cat,Integer> {}
