package com.pratice.practice_sp.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class StoredProcedureInitializer {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        // procedureName, parameters, procedureCode
        // Get all
        createStoredProcedureIfNotExists(
                "getAllPersons",
                "",
                "SELECT * FROM persons;");

        // Get By Id
        createStoredProcedureIfNotExists(
                "getPersonById",
                "IN p_id BIGINT",
                "SELECT * FROM persons WHERE id = p_id;");

        // Insert
        // createStoredProcedureIfNotExists(
        // "insertPerson",
        // "IN p_name VARCHAR(255), IN p_last_name VARCHAR(255), OUT p_id BIGINT",
        // "INSERT INTO persons (name, last_name) VALUES (p_name, p_last_name); SET p_id
        // = LAST_INSERT_ID();");
        createStoredProcedureIfNotExists(
                "insertPerson",
                "IN p_name VARCHAR(255), IN p_last_name VARCHAR(255)",
                "INSERT INTO persons (name, last_name) VALUES (p_name, p_last_name); CALL getPersonById(LAST_INSERT_ID());");

        // Update
        createStoredProcedureIfNotExists(
                "updatePerson",
                "IN p_id BIGINT, IN p_name VARCHAR(255), IN p_last_name VARCHAR(255)",
                "UPDATE persons SET name = p_name, last_name = p_last_name WHERE id = p_id; CALL getPersonById(p_id);");

        // Delete
        createStoredProcedureIfNotExists(
                "deletePersonById",
                "IN p_id BIGINT",
                "DELETE FROM persons WHERE id = p_id;");
    }

    private void createStoredProcedureIfNotExists(String procedureName, String params, String procedureCode) {
        String checkProcedure = "SELECT COUNT(*) FROM information_schema.ROUTINES " +
                "WHERE ROUTINE_SCHEMA = 'practice_sp_db' " +
                "AND ROUTINE_NAME = '" + procedureName + "'";

        Integer count = this.jdbcTemplate.queryForObject(checkProcedure, Integer.class);

        if (count == null || count == 0) {
            String createProcedure = "CREATE PROCEDURE " + procedureName + "(" + params + ") " +
                    "BEGIN " +
                    procedureCode + " " +
                    "END;";

            this.jdbcTemplate.execute(createProcedure);
        }
    }
}
