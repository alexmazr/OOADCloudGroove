package com.cloudgroove.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @JsonProperty("name")
    String name;
    @JsonProperty("userId")
    String userId;

}