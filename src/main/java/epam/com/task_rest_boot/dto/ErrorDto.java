package epam.com.task_rest_boot.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorDto(int status, String message, String path, Object params) {
    public ErrorDto(int status, String message, String path){
        this(status, message, path, null);
    }
}
