package br.com.personal.keystore.app.models.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserUpdateRequest {

    @NotBlank
    @Size(min = 2, max = 50)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
