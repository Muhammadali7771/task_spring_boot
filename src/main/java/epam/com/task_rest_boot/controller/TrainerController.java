package epam.com.task_rest_boot.controller;

import epam.com.task_rest_boot.dto.ChangeLoginDto;
import epam.com.task_rest_boot.dto.LoginRequestDto;
import epam.com.task_rest_boot.dto.RegistrationResponseDto;
import epam.com.task_rest_boot.dto.trainer.*;
import epam.com.task_rest_boot.dto.training.TrainingDto;
import epam.com.task_rest_boot.service.impl.TrainerServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("gym.com/api/trainer")
public class TrainerController {
    private final TrainerServiceImpl trainerService;

    public TrainerController(TrainerServiceImpl trainerService) {
        this.trainerService = trainerService;
    }

    @PostMapping
    @Operation(summary = "Register a new trainer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "New Trainer successfully has been registered"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided"),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request")
    })
    public ResponseEntity<RegistrationResponseDto> register(@RequestBody @Valid TrainerCreateDto dto) {
        RegistrationResponseDto responseDto = trainerService.create(dto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Retrieve specific trainer with the supplied username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieves the trainer with the supplied username"),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found"),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request")
    })
    public ResponseEntity<TrainerDto> get(@RequestParam("username") String username) {
        TrainerDto trainerDto = trainerService.getTrainerByUsername(username);
        return new ResponseEntity<>(trainerDto, HttpStatus.OK);
    }


    @PostMapping("/login")
    @Operation(summary = "Authenticate a trainer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - authentication failed"),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request")
    })
    public ResponseEntity<Void> login(@RequestBody @Valid LoginRequestDto dto) {
        trainerService.login(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/change-login")
    @Operation(summary = "Change the password of the trainer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password has been changed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided"),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request")
    })
    public ResponseEntity<Void> changeLogin(@RequestBody @Valid ChangeLoginDto dto) {
        trainerService.changePassword(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Operation(summary = "Update trainer information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the trainee information"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided"),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found"),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request")
    })
    public ResponseEntity<TrainerDto> update(@RequestParam("username") String username, @RequestBody TrainerUpdateDto dto) {
        TrainerDto updatedTrainer = trainerService.update(dto, username);
        return new ResponseEntity<>(updatedTrainer, HttpStatus.OK);
    }


    @PatchMapping("/change-status/{username}")
    @Operation(summary = "Activate/De-Activate trainer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully activates or deactivates trainer"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided"),
            @ApiResponse(responseCode = "404", description = "Resource you were trying to reach is not found"),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request")
    })
    public ResponseEntity<Void> activateOrDeactivateTrainer(@PathVariable String username) {
        trainerService.activateOrDeactivateTrainer(username);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/trainings-list")
    @Operation(summary = "Get trainer trainings list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieves the trainings of the trainer supplied with the username by criteria"),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found"),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request")
    })
    public ResponseEntity<List<TrainingDto>> getTrainingsList(@RequestParam("username") String trainerUsername,
                                                              @RequestParam(name = "period-from", required = false) Date periodFrom,
                                                              @RequestParam(name = "period-to", required = false) Date periodTo,
                                                              @RequestParam(name = "trainee-name", required = false) String traineeName) {
        List<TrainingDto> trainings = trainerService.getTrainerTrainingsList(trainerUsername, periodFrom, periodTo, traineeName);
        return ResponseEntity.ok(trainings);
    }

}
