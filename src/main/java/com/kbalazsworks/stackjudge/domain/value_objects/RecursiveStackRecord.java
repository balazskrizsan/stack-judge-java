package com.kbalazsworks.stackjudge.domain.value_objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RecursiveStackRecord(
    @JsonProperty Long id,
    @JsonProperty String name,
    @JsonProperty Long parentId,
    @JsonProperty int depth,
    @JsonProperty String path)
{
}
