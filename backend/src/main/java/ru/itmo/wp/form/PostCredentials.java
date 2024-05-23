package ru.itmo.wp.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PostCredentials {
    @NotEmpty
    @NotBlank
    @Size(min = 1, max = 20)
    private String title;

    @NotEmpty
    @NotBlank
    @Size(min = 1, max = 65000)
    private String text;

    @NotNull
    private String authorJwt;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthorJwt() {
        return authorJwt;
    }

    public void setAuthorJwt(String authorJwt) {
        this.authorJwt = authorJwt;
    }
}
