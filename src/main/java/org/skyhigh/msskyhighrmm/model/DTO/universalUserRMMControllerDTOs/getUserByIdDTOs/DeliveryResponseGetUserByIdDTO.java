package org.skyhigh.msskyhighrmm.model.DTO.universalUserRMMControllerDTOs.getUserByIdDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.skyhigh.msskyhighrmm.model.BusinessObjects.Users.UniversalUser;

@Setter
@Getter
@AllArgsConstructor
public class DeliveryResponseGetUserByIdDTO {
    private String message;
    private UniversalUser foundUniversalUser;
}
