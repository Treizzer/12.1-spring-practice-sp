package com.pratice.practice_sp.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonUpdateDto {

    Long id;

    @NotBlank(message = "El nombre NO puede ser nulo o vacío")
    @Size(min = 2, message = "El nombre debe tener al menos dos caracteres")
    String name;

    @NotBlank(message = "El apellido NO puede ser nulo o vacío")
    @Size(min = 2, message = "El apellido debe tener al menos dos caracteres")
    String lastName;

}

/*
 * @NotNull: El campo no debe ser null.
 * 
 * @NotEmpty: El campo no debe ser null y su tamaño debe ser mayor que cero.
 * 
 * @NotBlank: El campo no debe ser null y debe contener al menos un carácter que
 * no sea un espacio en blanco.
 * 
 * @Size: Controla el tamaño de cadenas, listas, arreglos, etc.
 * 
 * @Min y @Max: Define los valores mínimo y máximo para números.
 * 
 * @Email: Verifica si el campo tiene un formato de correo electrónico válido.
 * 
 * @Pattern: Define una expresión regular que el campo debe cumplir.
 */
