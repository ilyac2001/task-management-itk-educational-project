package ilya.tsimerman.authservice.domain.dto;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record AuthRequest(
        @Email
        @NotBlank
        String email,

        @NotBlank
        String password
) {}
