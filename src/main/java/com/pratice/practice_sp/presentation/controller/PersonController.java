package com.pratice.practice_sp.presentation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pratice.practice_sp.presentation.dto.PersonDto;
import com.pratice.practice_sp.presentation.dto.PersonInsertDto;
import com.pratice.practice_sp.presentation.dto.PersonUpdateDto;
import com.pratice.practice_sp.service.interfaces.ICommonService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    @Autowired
    private ICommonService<PersonDto, PersonInsertDto, PersonUpdateDto> personService;

    @GetMapping
    public ResponseEntity<List<PersonDto>> findAll() {
        return ResponseEntity.ok(this.personService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> findById(@PathVariable Long id) {
        var personDto = this.personService.findById(id);

        return personDto == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(personDto);
    }

    @PostMapping
    public ResponseEntity<PersonDto> add(@Valid @RequestBody PersonInsertDto personInsertDto) {
        var personDto = this.personService.add(personInsertDto);

        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(personDto.getId())
                        .toUri())
                .body(personDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDto> update(@Valid @RequestBody PersonUpdateDto personUpdateDto,
            @PathVariable Long id) {
        if (id == null || id <= 0) { // Maybe if (id <= 0) {
            return ResponseEntity.badRequest().body(null);
        }

        var personDto = this.personService.update(personUpdateDto, id);

        return personDto == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(personDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PersonDto> deleteById(@PathVariable Long id) {
        if (id == null || id <= 0) {
            // return ResponseEntity.badRequest().build();
            return ResponseEntity.badRequest().body(null);
        }

        var personDto = this.personService.deleteById(id);

        return personDto == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(personDto);
    }

}
