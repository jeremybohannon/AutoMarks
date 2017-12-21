package com.automarks.storage.storageService;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface UserRepository extends MongoRepository<User, String>{

    @Override
    List<User> findAll();

    @Override
    List<User> findAll(Sort sort);

    List<User> findBySchoolID(String schoolID);
}
