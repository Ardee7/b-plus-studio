package com.api.b_plus_studio.controllers;

import com.api.b_plus_studio.Exceptions.UserException;
import com.api.b_plus_studio.dtos.CreateUserRequest;
import com.api.b_plus_studio.dtos.GenericResponse;
import com.api.b_plus_studio.dtos.UserRequest;
import com.api.b_plus_studio.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @Operation(summary = "Create a new user", description = "Creates a new user with the provided details.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping
    public ResponseEntity createUser(@Validated @RequestBody CreateUserRequest request) {
        return ResponseEntity.ok(new ResponseEntity<>(GenericResponse.build(userService.createUser(request)), HttpStatus.OK));
    }

    @Operation(summary = "Get all users", description = "Retrieves a list of all users.")
    @ApiResponse(responseCode = "200", description = "List of users retrieved successfully")
    @GetMapping
    public ResponseEntity getAllUsers() {
        return ResponseEntity.ok(new ResponseEntity<>(GenericResponse.build(userService.getAllUsers()), HttpStatus.OK));
    }

    @Operation(summary = "Get user by ID", description = "Retrieves a user by their unique ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable Long id) throws UserException {
        return ResponseEntity.ok(new ResponseEntity<>(GenericResponse.build(userService.getUserById(id)), HttpStatus.OK));
    }

    @Operation(summary = "Update a user", description = "Updates the details of an existing user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity updateUser(@PathVariable Long id, @Validated @RequestBody UserRequest request) {
        return ResponseEntity.ok(new ResponseEntity<>(GenericResponse.build(userService.updateUser(id, request)), HttpStatus.OK));
    }

    @Operation(summary = "Delete a user", description = "Deletes a user by their ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(new ResponseEntity<>(GenericResponse.build("Deletion Completed"), HttpStatus.OK));
    }

}
