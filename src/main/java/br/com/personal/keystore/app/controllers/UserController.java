package br.com.personal.keystore.app.controllers;

import br.com.personal.keystore.app.models.request.FolderSaveRequest;
import br.com.personal.keystore.app.models.request.FolderUpdateRequest;
import br.com.personal.keystore.app.models.request.PasswordSaveRequest;
import br.com.personal.keystore.app.models.request.PasswordUpdateRequest;
import br.com.personal.keystore.app.models.request.UserSaveRequest;
import br.com.personal.keystore.app.models.request.UserUpdateRequest;
import br.com.personal.keystore.app.models.response.FolderResponse;
import br.com.personal.keystore.app.models.response.PasswordResponse;
import br.com.personal.keystore.app.models.response.UserResponse;
import br.com.personal.keystore.domain.usecases.FolderMaintenanceUseCase;
import br.com.personal.keystore.domain.usecases.FolderQueryUseCase;
import br.com.personal.keystore.domain.usecases.PasswordMaintenanceUseCase;
import br.com.personal.keystore.domain.usecases.PasswordQueryUseCase;
import br.com.personal.keystore.domain.usecases.UserMaintenanceUseCase;
import br.com.personal.keystore.domain.usecases.UserQueryUseCase;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    UserQueryUseCase userQueryUseCase;

    UserMaintenanceUseCase userMaintenanceUseCase;

    PasswordQueryUseCase passwordQueryUseCase;

    PasswordMaintenanceUseCase passwordMaintenanceUseCase;

    FolderQueryUseCase folderQueryUseCase;

    FolderMaintenanceUseCase folderMaintenanceUseCase;

    public UserController(UserQueryUseCase userQueryUseCase, UserMaintenanceUseCase userMaintenanceUseCase, PasswordQueryUseCase passwordQueryUseCase, PasswordMaintenanceUseCase passwordMaintenanceUseCase, FolderQueryUseCase folderQueryUseCase, FolderMaintenanceUseCase folderMaintenanceUseCase) {
        this.userQueryUseCase = userQueryUseCase;
        this.userMaintenanceUseCase = userMaintenanceUseCase;
        this.passwordQueryUseCase = passwordQueryUseCase;
        this.passwordMaintenanceUseCase = passwordMaintenanceUseCase;
        this.folderQueryUseCase = folderQueryUseCase;
        this.folderMaintenanceUseCase = folderMaintenanceUseCase;
    }

    @Operation(summary = "Get all users", description = "Get list of users", tags = {"Users"})
    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userQueryUseCase.getAll();
    }

    @Operation(summary = "Get user by id", description = "Get unique user", tags = {"Users"})
    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable("id") Long id) {
        return userQueryUseCase.getById(id);
    }

    @Operation(summary = "Save user", description = "Save unique user", tags = {"Users"})
    @PostMapping
    public void saveUser(@RequestBody @Valid UserSaveRequest userSaveRequest) {
        this.userMaintenanceUseCase.save(userSaveRequest);
    }

    @Operation(summary = "Update user by id", description = "Update existent user", tags = {"Users"})
    @PutMapping("/{id}")
    public void updateUser(@PathVariable("id") Long id, @RequestBody @Valid UserUpdateRequest userUpdateRequest) {
        this.userMaintenanceUseCase.update(id, userUpdateRequest);
    }

    @Operation(summary = "Delete user by id", description = "Delete existent user", tags = {"Users"})
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        this.userMaintenanceUseCase.delete(id);
    }


    // Password
    @Operation(summary = "Get all passwords from user", description = "Get all passwords from user", tags = {"Passwords"})
    @GetMapping("/{userId}/passwords")
    public List<PasswordResponse> getAllPasswords(@PathVariable("userId") Long userId) {
        return passwordQueryUseCase.getAll(userId);
    }

    @Operation(summary = "Get all passwords from user and folder", description = "Get all passwords from existent folder", tags = {"Passwords"})
    @GetMapping("/{userId}/passwords/folders/{folderId}")
    public List<PasswordResponse> getAllPasswordsFromFolder(@PathVariable("userId") Long userId, @PathVariable("folderId") Long folderId) {
        return passwordQueryUseCase.getByFolder(userId, folderId);
    }

    @Operation(summary = "Get password from user by id", description = "Get password from user by id", tags = {"Passwords"})
    @GetMapping("/{userId}/passwords/{passwordId}")
    public PasswordResponse getPasswordById(@PathVariable("userId") Long userId, @PathVariable("passwordId") Long passwordId) {
        return passwordQueryUseCase.getById(userId, passwordId);
    }

    @Operation(summary = "Add password to user", description = "Add password to user", tags = {"Passwords"})
    @PostMapping("/{userId}/passwords")
    public void savePassword(@PathVariable("userId") Long userId, @RequestBody @Valid PasswordSaveRequest passwordSaveRequest) throws Exception {
        this.passwordMaintenanceUseCase.save(userId, passwordSaveRequest);
    }

    @Operation(summary = "Update password from user by id", description = "Update password from user by id", tags = {"Passwords"})
    @PutMapping("/{userId}/passwords/{passwordId}")
    public void updatePassword(@PathVariable("userId") Long userId, @PathVariable("passwordId") Long passwordId, @RequestBody @Valid PasswordUpdateRequest passwordUpdateRequest) {
        this.passwordMaintenanceUseCase.update(userId, passwordId, passwordUpdateRequest);
    }

    @Operation(summary = "Delete a password from user by id", description = "Delete a password from user by id", tags = {"Passwords"})
    @DeleteMapping("/{userId}/passwords/{passwordId}")
    public void deletePassword(@PathVariable("userId") Long userId, @PathVariable("passwordId") Long passwordId) {
        this.passwordMaintenanceUseCase.delete(userId, passwordId);
    }


    // Folders
    @Operation(summary = "Get all folders from user", description = "Get all folders from user", tags = {"Folders"})
    @GetMapping("/{userId}/folders")
    public List<FolderResponse> getAllFolders(@PathVariable("userId") Long userId) {
        return folderQueryUseCase.getAll(userId);
    }

    @Operation(summary = "Get folder from user by id", description = "Get folder from user by id", tags = {"Folders"})
    @GetMapping("/{userId}/folders/{folderId}")
    public FolderResponse getFolderById(@PathVariable("userId") Long userId, @PathVariable("folderId") Long folderId) {
        return folderQueryUseCase.getById(userId, folderId);
    }

    @Operation(summary = "Add folder to user", description = "Add folder to user", tags = {"Folders"})
    @PostMapping("/{userId}/folders")
    public void saveFolder(@PathVariable("userId") Long userId, @RequestBody @Valid FolderSaveRequest folderSaveRequest) {
        this.folderMaintenanceUseCase.save(userId, folderSaveRequest);
    }

    @Operation(summary = "Update folder from user by id", description = "Update folder from user by id", tags = {"Folders"})
    @PutMapping("/{userId}/folders/{folderId}")
    public void updateFolder(@PathVariable("userId") Long userId, @PathVariable("folderId") Long folderId, @RequestBody @Valid FolderUpdateRequest folderUpdateRequest) {
        this.folderMaintenanceUseCase.update(userId, folderId, folderUpdateRequest);
    }

    @Operation(summary = "Delete a folder from user by id", description = "Delete a folder from user by id", tags = {"Folders"})
    @DeleteMapping("/{userId}/folders/{folderId}")
    public void deleteFolder(@PathVariable("userId") Long userId, @PathVariable("folderId") Long folderId) {
        this.folderMaintenanceUseCase.delete(userId, folderId);
    }


}
