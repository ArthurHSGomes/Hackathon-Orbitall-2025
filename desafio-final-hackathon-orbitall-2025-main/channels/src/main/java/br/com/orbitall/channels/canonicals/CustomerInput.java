package br.com.orbitall.channels.canonicals;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CustomerInput(
        @NotBlank(message = "Full name cannot be null or empty")
        @Size(max = 255, message = "Full name cannot exceed 255 characters")
        String fullName,

        @NotBlank(message = "Email cannot be null or empty")
        @Size(max = 100, message = "Email cannot exceed 100 characters")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "The phone cannot be null or empty")
        String phone
) {
}

