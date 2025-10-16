package data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResourceResponse {
        private Integer page;
        private Integer per_page;
        private Integer total;
        private Integer total_pages;
        private List<ResourceData> data;
        private Support support;
}