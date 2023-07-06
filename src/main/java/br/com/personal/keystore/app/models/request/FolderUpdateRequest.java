package br.com.personal.keystore.app.models.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class FolderUpdateRequest {

    @NotBlank
    @Size(max = 30)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
