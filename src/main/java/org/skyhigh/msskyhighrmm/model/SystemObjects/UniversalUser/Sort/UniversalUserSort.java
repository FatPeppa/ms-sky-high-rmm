package org.skyhigh.msskyhighrmm.model.SystemObjects.UniversalUser.Sort;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.skyhigh.msskyhighrmm.model.BusinessObjects.Users.UniversalUser;
import org.skyhigh.msskyhighrmm.model.SystemObjects.SortDirection;
import org.skyhigh.msskyhighrmm.model.SystemObjects.UniversalUser.Comparators.LoginUniversalUserComparator;
import org.skyhigh.msskyhighrmm.model.SystemObjects.UniversalUser.Comparators.UserIdUniversalUserComparator;
import org.skyhigh.msskyhighrmm.model.SystemObjects.UniversalUser.Filters.UniversalUserFilters;
import org.skyhigh.msskyhighrmm.validation.annotations.NotEmpty;

import java.util.ArrayList;

@Setter
@Getter
@AllArgsConstructor
public class UniversalUserSort {

    @NotEmpty
    private SortDirection direction;

    @NotEmpty
    private UniversalUserSortParameter sortBy;

    public static void sort(ArrayList<UniversalUser> usersListToSort, UniversalUserSort universalUserSort)
    {
        switch (universalUserSort.getDirection()) {
            case ASC -> {
                if (universalUserSort.getSortBy() == UniversalUserSortParameter.LOGIN) {
                    usersListToSort.sort(new LoginUniversalUserComparator());
                } else if (universalUserSort.getSortBy() == UniversalUserSortParameter.USER_ID) {
                    usersListToSort.sort(new UserIdUniversalUserComparator());
                }
            }
            case DESC -> {
                if (universalUserSort.getSortBy() == UniversalUserSortParameter.LOGIN) {
                    usersListToSort.sort(new LoginUniversalUserComparator().reversed());
                } else if (universalUserSort.getSortBy() == UniversalUserSortParameter.USER_ID) {
                    usersListToSort.sort(new UserIdUniversalUserComparator().reversed());
                }
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) || (
                (direction == null ? ((UniversalUserSort) obj).getDirection() == null : this.direction.equals(((UniversalUserSort) obj).getDirection()))
                && (sortBy == null ? ((UniversalUserSort) obj).getSortBy() == null : this.sortBy.equals(((UniversalUserSort) obj).getSortBy())));
    }
}
