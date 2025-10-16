package data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ResourceData {
    private Long id;
    private String name;
    private Integer year;
    private String color;
    @JsonProperty("pantone_value")
    private String pantoneValue;

}
