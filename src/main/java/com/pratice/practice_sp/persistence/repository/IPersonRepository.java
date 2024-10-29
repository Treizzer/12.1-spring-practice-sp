package com.pratice.practice_sp.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratice.practice_sp.persistence.entity.PersonEntity;

@Repository
public interface IPersonRepository extends CrudRepository<PersonEntity, Long> {

    @Procedure
    Iterable<PersonEntity> getAllPersons();

    @Procedure
    Optional<PersonEntity> getPersonById(@Param("p_id") Long id);

    @Procedure
    Optional<PersonEntity> insertPerson(
            @Param("p_name") String name,
            @Param("p_last_name") String lastName);

    @Procedure
    Optional<PersonEntity> updatePerson(
            @Param("p_id") Long id,
            @Param("p_name") String name,
            @Param("p_last_name") String lastName);

    @Procedure
    void deletePersonById(@Param("p_id") Long id);

}
