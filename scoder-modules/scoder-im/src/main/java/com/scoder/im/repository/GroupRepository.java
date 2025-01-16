package com.scoder.im.repository;


import com.scoder.im.api.domain.Group;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GroupRepository extends MongoRepository<Group, String>, ChatCustomRepository {

    Group findByTeamId(Long teamId);
}