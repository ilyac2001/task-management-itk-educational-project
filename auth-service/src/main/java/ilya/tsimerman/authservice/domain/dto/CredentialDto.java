package ilya.tsimerman.authservice.domain.dto;

public record CredentialDto(
        String type,
        String value,
        boolean temporary
) {}
