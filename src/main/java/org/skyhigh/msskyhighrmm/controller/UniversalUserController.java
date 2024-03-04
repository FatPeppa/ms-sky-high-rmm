package org.skyhigh.msskyhighrmm.controller;

import org.skyhigh.msskyhighrmm.model.DTO.DeliveryRequestRegisterUserDTO;
import org.skyhigh.msskyhighrmm.model.UniversalUser;
import org.skyhigh.msskyhighrmm.service.UniversalUserService;
import org.skyhigh.msskyhighrmm.validation.SpringAspect.annotationsApi.ValidParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@RestController
public class UniversalUserController {
    //private static Logger log = Logger.getLogger(UniversalUserController.class.getName());

    private final UniversalUserService universalUserService;

    @Autowired
    public UniversalUserController(UniversalUserService universalUserService) {
        this.universalUserService = universalUserService;

    }

    //project logic - comment it if you wanna just only test the project availability and try the part below
    @ValidParams
    @PostMapping(value = "/users")
    public ResponseEntity<UUID> registerUser(@RequestBody DeliveryRequestRegisterUserDTO registerUserDTO) {
        UUID registered_user_id = universalUserService.registerUser(registerUserDTO);
        return new ResponseEntity<>(registered_user_id, HttpStatus.OK);
    }

    //test controller methods - uncomment to test the project availability
    /*

    @ValidParams
    @GetMapping("/check")
    public void checkDeliveryAvailability(@RequestBody DeliveryRequestRegisterUserDTO requestDto) {
        System.out.println("Validation!");
    }

    @PostMapping(value = "/users")
    public ResponseEntity<?> create(@RequestBody UniversalUser universalUser) {
        universalUserService.create(universalUser);
        //log.info(universalUser.toString());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<UniversalUser>> read() {
        final List<UniversalUser> users = universalUserService.readAll();

        return users != null &&  !users.isEmpty()
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/users/{user_id}")
    public ResponseEntity<UniversalUser> read(@PathVariable(name = "user_id") UUID user_id) {
        final UniversalUser client = universalUserService.read(user_id);

        return client != null
                ? new ResponseEntity<>(client, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/users/{user_id}")
    public ResponseEntity<?> update(@PathVariable(name = "user_id") UUID user_id, @RequestBody UniversalUser universalUser) {
        final boolean updated = universalUserService.update(universalUser, user_id);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/users/{user_id}")
    public ResponseEntity<?> delete(@PathVariable(name = "user_id") UUID user_id) {
        final boolean deleted = universalUserService.delete(user_id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }*/
}