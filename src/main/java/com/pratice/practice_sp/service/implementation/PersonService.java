package com.pratice.practice_sp.service.implementation;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pratice.practice_sp.persistence.entity.PersonEntity;
import com.pratice.practice_sp.persistence.repository.IPersonRepository;
import com.pratice.practice_sp.presentation.dto.PersonDto;
import com.pratice.practice_sp.presentation.dto.PersonInsertDto;
import com.pratice.practice_sp.presentation.dto.PersonUpdateDto;
import com.pratice.practice_sp.service.interfaces.ICommonService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PersonService implements ICommonService<PersonDto, PersonInsertDto, PersonUpdateDto> {

    @Autowired
    private IPersonRepository personRepository;

    private static final ModelMapper MAPPER = new ModelMapper();

    @Override
    @Transactional
    public List<PersonDto> findAll() {
        // var persons = this.personRepository.findAll();
        var persons = this.personRepository.getAllPersons();

        return StreamSupport
                .stream(persons.spliterator(), false)
                .map(person -> MAPPER.map(person, PersonDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PersonDto findById(Long id) {
        // var personEntity = this.personRepository.findById(id)
        var personEntity = this.personRepository.getPersonById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("No se encontró la persona con id: " + id));

        return MAPPER.map(personEntity, PersonDto.class);
    }

    @Override
    @Transactional
    public PersonDto add(PersonInsertDto insertDto) {
        try {
            var personEntity = MAPPER.map(insertDto, PersonEntity.class);
            // personEntity = this.personRepository.save(personEntity);
            personEntity = this.personRepository
                    .insertPerson(personEntity.getName(), personEntity.getLastName())
                    .orElseThrow(() -> new UnsupportedOperationException(
                            "Error al guardas a la Entidad Persona: " + insertDto));

            return MAPPER.map(personEntity, PersonDto.class);

        } catch (Exception ex) {
            throw new UnsupportedOperationException(
                    "Error al guardar a la Persona: " + insertDto + " -> ex: " + ex.toString());
        }
    }

    @Override
    @Transactional
    public PersonDto update(PersonUpdateDto updateDto, Long id) {
        PersonEntity personEntity = id != null
                ? this.personRepository.getPersonById(id)
                        .orElseThrow(() -> new EntityNotFoundException("No se encontró a la persona con ID: " + id))
                : this.personRepository.getPersonById(updateDto.getId())
                        .orElseThrow(() -> new EntityNotFoundException(
                                "No se encontró a la persona con ID: " + updateDto.getId()));

        personEntity.setName(updateDto.getName());
        personEntity.setLastName(updateDto.getLastName());

        // this.personRepository.save(personEntity);
        this.personRepository.updatePerson(
                personEntity.getId(),
                personEntity.getName(),
                personEntity.getLastName());

        return MAPPER.map(personEntity, PersonDto.class);
    }

    @Override
    @Transactional
    public PersonDto deleteById(Long id) {
        var personEntity = this.personRepository.getPersonById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró a la persona con ID: " + id));

        var personDto = MAPPER.map(personEntity, PersonDto.class);
        this.personRepository.deletePersonById(id);

        return personDto;
    }

}
