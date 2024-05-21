package org.skyhigh.msskyhighrmm.service.UniversalUserService;

import org.skyhigh.msskyhighrmm.model.BusinessObjects.Users.ListOfUniversalUser;
import org.skyhigh.msskyhighrmm.model.BusinessObjects.Users.UniversalUser;
import org.skyhigh.msskyhighrmm.model.BusinessObjects.Users.UserInfo.UserInfo;
import org.skyhigh.msskyhighrmm.model.BusinessObjects.Users.UsersToBlockInfoListElement;
import org.skyhigh.msskyhighrmm.model.DBEntities.AdministratorKeyCodeEntity;
import org.skyhigh.msskyhighrmm.model.DBEntities.UniversalUserEntity;
import org.skyhigh.msskyhighrmm.model.ServiceMethodsResultMessages.UniversalUserServiceMessages.AddAdminKey.AddAdminKeyResultMessage;
import org.skyhigh.msskyhighrmm.model.ServiceMethodsResultMessages.UniversalUserServiceMessages.BlockUsers.BlockUsersResultMessage;
import org.skyhigh.msskyhighrmm.model.ServiceMethodsResultMessages.UniversalUserServiceMessages.BlockUsers.BlockUsersResultMessageListElement;
import org.skyhigh.msskyhighrmm.model.ServiceMethodsResultMessages.UniversalUserServiceMessages.LoginUser.LoginUserResultMessage;
import org.skyhigh.msskyhighrmm.model.ServiceMethodsResultMessages.UniversalUserServiceMessages.RegisterUser.RegisterUserResultMessage;
import org.skyhigh.msskyhighrmm.model.SystemObjects.UniversalPagination.PaginatedObject;
import org.skyhigh.msskyhighrmm.model.SystemObjects.UniversalPagination.PaginationInfo;
import org.skyhigh.msskyhighrmm.model.SystemObjects.UniversalUser.Converters.UserEntityToUserBOConverter;
import org.skyhigh.msskyhighrmm.model.SystemObjects.UniversalUser.Filters.UniversalUserFilters;
import org.skyhigh.msskyhighrmm.model.SystemObjects.UniversalUser.Sort.UniversalUserSort;
import org.skyhigh.msskyhighrmm.repository.AdministratorKeyCodeRepository;
import org.skyhigh.msskyhighrmm.repository.BlockReasonsRepository;
import org.skyhigh.msskyhighrmm.repository.UniversalUserRepository;
import org.skyhigh.msskyhighrmm.repository.UsersRolesRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UniversalUserServiceImpl implements UniversalUserService {
    private final UniversalUserRepository universalUserRepository;
    private final BlockReasonsRepository blockReasonsRepository;
    private final AdministratorKeyCodeRepository administratorKeyCodeRepository;
    private final UsersRolesRepository usersRolesRepository;

    private final PasswordEncoder passwordEncoder;

    public UniversalUserServiceImpl(UniversalUserRepository universalUserRepository, BlockReasonsRepository blockReasonsRepository, AdministratorKeyCodeRepository administratorKeyCodeRepository, UsersRolesRepository usersRolesRepository, PasswordEncoder passwordEncoder) {
        this.universalUserRepository = universalUserRepository;
        this.blockReasonsRepository = blockReasonsRepository;
        this.administratorKeyCodeRepository = administratorKeyCodeRepository;
        this.usersRolesRepository = usersRolesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public RegisterUserResultMessage registerUser(String login, String password, boolean isAdmin, String adminKey) {
        List<AdministratorKeyCodeEntity> adminKeyCodeEntities = new ArrayList<>();

        if (!(6 <= login.length() && login.length() <= 20)) {
            return new RegisterUserResultMessage(
                    "Login length must be in the range from 6 to 20 characters.",
                    1,
                    null
            );
        }

        if (!(8 <= password.length() && password.length() <= 20)) {
            return new RegisterUserResultMessage(
                    "Password length must be in the range from 8 to 20 characters.",
                    2,
                    null
            );
        }

        if (isAdmin) {
            if (adminKey == null)
                return new RegisterUserResultMessage(
                        "AdminKey cannot be null for admins.",
                        4,
                        null
                );

            if (adminKey.length() != 32)
                return new RegisterUserResultMessage(
                        "AdminKey must be size of 32.",
                        5,
                        null
                );

            adminKeyCodeEntities =
                    administratorKeyCodeRepository.findByCode(adminKey);

            if (adminKeyCodeEntities == null || adminKeyCodeEntities.size() != 1)
                return new RegisterUserResultMessage(
                        "There is no such admin key code for admins.",
                        6,
                        null
                );

            if (adminKeyCodeEntities.get(0).getUser_id() != null)
                return new RegisterUserResultMessage(
                        "Admin with certain admin key code already exists.",
                        7,
                        null
                );
        }

        if (!universalUserRepository.findByLogin(login).isEmpty())
            return new RegisterUserResultMessage(
                    "User with this id already exists.",
                    3,
                    null
            );

        String encodedPass = passwordEncoder.encode(password);

        UniversalUserEntity user = new UniversalUserEntity(
                null,
                login,
                encodedPass,
                null,
                null
        );

        UUID universal_user_id = (universalUserRepository.save(user)).getId();

        if (isAdmin) {
            administratorKeyCodeRepository.setRegisteredUserId(
                    adminKeyCodeEntities.get(0).getId(),
                    universal_user_id
            );
        }

        return new RegisterUserResultMessage(
                "User created successfully.",
                0,
                universal_user_id
        );
    }

    @Override
    public LoginUserResultMessage loginUser(String login, String password) {
        LoginUserResultMessage result = new LoginUserResultMessage();

        ArrayList<UniversalUserEntity> users = (ArrayList<UniversalUserEntity>) universalUserRepository
                .findByLogin(login);

        if (users.size() != 1) {
            result.setGlobalOperationCode(1);
            result.setGlobalMessage("Пользователя не существует.");
            return result;
        }

        if (!passwordEncoder.matches(password, users.get(0).getPassword())) {
            result.setGlobalOperationCode(2);
            result.setGlobalMessage("Неправильный пароль.");
            return result;
        }

        result.setGlobalOperationCode(0);
        result.setLogonUserId(users.get(0).getId());
        return result;
    }

    //Проверка существования пользователя с указанным логином
    @Override
    public UUID checkUser(String login) {
        ArrayList<UniversalUserEntity> users = (ArrayList<UniversalUserEntity>) universalUserRepository
                .findByLogin(login);

        if (users.size() != 1)
            return null;

        return users.get(0).getId();
    }

    @Override
    public UniversalUser getUserById(UUID id) {
        return UserEntityToUserBOConverter.convert(universalUserRepository.getReferenceById(id));
    }

    @Override
    public ListOfUniversalUser searchUsers(PaginationInfo paginationInfo, UniversalUserFilters universalUserFilters, UniversalUserSort universalUserSort) {
        ArrayList<UniversalUser> resultUniversalUsersList = (ArrayList<UniversalUser>) UserEntityToUserBOConverter
                .convertList(
                    universalUserRepository.findAll()
                );

        if (universalUserFilters != null) {
            resultUniversalUsersList = UniversalUserFilters.filter(universalUserFilters.getBlock_reason_id(),
                    universalUserFilters.getUser_info(),
                    universalUserRepository);
        }

        if (resultUniversalUsersList == null) return null;

        if (universalUserSort != null) {
            UniversalUserSort.sort(resultUniversalUsersList, universalUserSort);
        }

        int paginationItemCount = resultUniversalUsersList.size();
        int paginationPageNumber = 1;
        int itemCount = resultUniversalUsersList.size();

        if (paginationInfo != null) {
            paginationItemCount = paginationInfo.getRequestedItemCount();
            paginationPageNumber = paginationInfo.getPage();

            PaginatedObject<UniversalUser> paginated = new PaginatedObject<>(paginationItemCount,
                    paginationPageNumber, resultUniversalUsersList);
            resultUniversalUsersList = paginated.getResultList();
        }

        return resultUniversalUsersList != null
            ? new ListOfUniversalUser(itemCount, paginationItemCount, paginationPageNumber, resultUniversalUsersList)
            : null;
    }

    @Override
    public UniversalUser updateUserById(UUID userId, UserInfo newUserInfoAttributes) {
        if (!universalUserRepository.existsById(userId)) return null;

        universalUserRepository.updateUserInfoForUserWithId(userId, newUserInfoAttributes);

        return UserEntityToUserBOConverter.convert(universalUserRepository.getReferenceById(userId));
    }

    @Override
    public BlockUsersResultMessage blockUsers(ArrayList<UsersToBlockInfoListElement> usersInfoToBlock,
                                              UUID userToBlockId, String blockReasonId) {
        BlockUsersResultMessage resultMessage = new BlockUsersResultMessage(
                null,
                0,
                null);

        //если указаны данные конкретного пользователя и список пользователей одновременно
        if ((userToBlockId != null || blockReasonId != null) && usersInfoToBlock != null) {
            resultMessage.setGlobalOperationCode(2);
            resultMessage.setGlobalMessage("Указание списка пользователей и" +
                    " конкретного пользователя в разных параметрах одновременно запрещено");
            return resultMessage;
        }

        //если не указан идентификатор блокируемого пользователя
        if (userToBlockId == null && blockReasonId != null) {
            resultMessage.setGlobalOperationCode(2);
            resultMessage.setGlobalMessage("Не указан идентификатор блокируемого пользователя");
            return resultMessage;
        }

        //если не указан код причины блокировки
        if (userToBlockId != null && blockReasonId == null) {
            resultMessage.setGlobalOperationCode(2);
            resultMessage.setGlobalMessage("Не указан код причины блокировки");
            return resultMessage;
        }

        //если не указан ни конкретный пользователь, ни список пользователей
        if (userToBlockId == null && usersInfoToBlock == null) {
            resultMessage.setGlobalOperationCode(2);
            resultMessage.setGlobalMessage("Недостаточно информации для блокировки пользователей");
            return resultMessage;
        }

        ArrayList<BlockUsersResultMessageListElement> certainUserBlockResultList = new ArrayList<>();

        //Если указан конкретный пользователь
        if (userToBlockId != null) {
            if (!universalUserRepository.existsById(userToBlockId)) {
                if (resultMessage.getGlobalOperationCode() == 0)
                    resultMessage.setGlobalOperationCode(2);

                certainUserBlockResultList.add(new BlockUsersResultMessageListElement(
                        "Пользователь с идентификатором '" +
                                userToBlockId + "' не найден",
                        1
                ));
            }
            else if (!blockReasonsRepository.existsById(blockReasonId)) {
                if (resultMessage.getGlobalOperationCode() == 0)
                    resultMessage.setGlobalOperationCode(2);

                certainUserBlockResultList.add(new BlockUsersResultMessageListElement(
                        "Причина блокировки с идентификатором '" +
                                blockReasonId + "' не найдена",
                        2
                ));
            } else {
                universalUserRepository.setBlockReasonIdForUserWithId(userToBlockId, blockReasonId);

                certainUserBlockResultList.add(new BlockUsersResultMessageListElement(
                        "Пользователь с идентификатором '" +
                                userToBlockId + "' успешно заблокирован",
                        0
                ));
            }

            switch (resultMessage.getGlobalOperationCode()) {
                case 0 -> resultMessage.setGlobalMessage("Указанный пользователь успешно заблокированы");
                case 2 -> resultMessage.setGlobalMessage("Указанный пользователь не заблокирован из-за ошибки");
            }

            resultMessage.setCertainBlockUsersResults(certainUserBlockResultList);

            return resultMessage;
        }

        //если указан список пользователей (и не указан конкретный пользователь)
        for (UsersToBlockInfoListElement element : usersInfoToBlock) {
            if (!universalUserRepository.existsById(element.getUserId())) {
                if (resultMessage.getGlobalOperationCode() == 0)
                    resultMessage.setGlobalOperationCode(2);

                certainUserBlockResultList.add(new BlockUsersResultMessageListElement(
                        "Пользователь с идентификатором '" +
                                element.getUserId() + "' не найден",
                        1
                ));
            }
            else if (!blockReasonsRepository.existsById(element.getBlockReasonId())) {
                if (resultMessage.getGlobalOperationCode() == 0)
                    resultMessage.setGlobalOperationCode(2);

                certainUserBlockResultList.add(new BlockUsersResultMessageListElement(
                        "Причина блокировки с идентификатором '" +
                                element.getBlockReasonId() + "' не найдена",
                        2
                ));
            }
            else {
                if (resultMessage.getGlobalOperationCode() == 2)
                    resultMessage.setGlobalOperationCode(1);

                universalUserRepository.setBlockReasonIdForUserWithId(
                        element.getUserId(),
                        element.getBlockReasonId()
                );

                certainUserBlockResultList.add(new BlockUsersResultMessageListElement(
                        "Пользователь с идентификатором '" +
                                element.getUserId() + "' успешно заблокирован",
                        0
                ));
            }
        }

        switch (resultMessage.getGlobalOperationCode()) {
            case 0 -> resultMessage.setGlobalMessage("Все пользователи из списка заблокированы");
            case 1 -> resultMessage.setGlobalMessage("Заблокирована лишь часть пользователей из списка");
            case 2 -> resultMessage.setGlobalMessage("Ни один пользователь из списка не был заблокирован");
        }

        resultMessage.setCertainBlockUsersResults(certainUserBlockResultList);

        return resultMessage;
    }

    @Override
    public AddAdminKeyResultMessage addAdminKey(UUID userMadeRequest, String adminKey) {
        List<AdministratorKeyCodeEntity> checkUserMadeRequestList =
                administratorKeyCodeRepository.findByUserId(userMadeRequest);

        if (checkUserMadeRequestList == null || checkUserMadeRequestList.isEmpty())
            return new AddAdminKeyResultMessage(
                    "User made request is not an administrator.",
                    2,
                    null
            );

        if (adminKey.length() != 32)
            return new AddAdminKeyResultMessage(
                    "Admin key length must be 32 characters.",
                    1,
                    null
            );

        AdministratorKeyCodeEntity administratorKeyCodeReferenceToCreate = new AdministratorKeyCodeEntity(
                null,
                null,
                adminKey
        );

        UUID createdReferenceId = (administratorKeyCodeRepository.save(administratorKeyCodeReferenceToCreate))
                .getId();

        return new AddAdminKeyResultMessage(
                "Admin key created.",
                0,
                createdReferenceId
        );
    }

    @Override
    public List<UniversalUser> readAll() {
        return UserEntityToUserBOConverter.convertList(universalUserRepository.findAll());
    }
}