package com.automarks.storage.storageService;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface StorageRepository extends MongoRepository<Assignment,String>{

    @Override
    List<Assignment> findAll();

    @Override
    List<Assignment> findAll(Sort sort);

    List<Assignment> findById(long id);
}
