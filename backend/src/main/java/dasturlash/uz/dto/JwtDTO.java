package dasturlash.uz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public record JwtDTO(
        String login,
        String role
) {

}


