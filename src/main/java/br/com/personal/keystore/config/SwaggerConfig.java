package br.com.personal.keystore.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;


@OpenAPIDefinition(
    info = @Info(
        title = "Petralhas Authenticator",
        version = "0.1.0",
        description = "Petralhas Authenticator API",
        contact = @Contact(url = "https://github.com/brunoslvb", name = "Bruno da Silva Barros", email = "brunosilva2365@gmail.com")
    ),
    tags = {
        @Tag(name = "Users", description = "User resources"),
        @Tag(name = "Passwords", description = "Password resources"),
        @Tag(name = "Folders", description = "Folder resources")
    },
    servers = {
        @Server(
            description = "Server 1",
            url = "http://localhost:8081"
        )
    }
)
public class SwaggerConfig { }
