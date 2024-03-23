package org.skyhigh.msskyhighrmm.model.DTO.universalUserRMMControllerDTOs.searchUsersDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.skyhigh.msskyhighrmm.model.BusinessObjects.Users.UniversalUser;
import org.skyhigh.msskyhighrmm.model.SystemObjects.UniversalPagination.PageInfo;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class DeliveryResponseSearchUsersDTO {
    private int foundItemAmount;
    private PageInfo pageInfo;
    private List<UniversalUser> universalUsers;
}
