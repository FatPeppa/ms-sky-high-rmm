package org.skyhigh.msskyhighrmm.service;

import org.skyhigh.msskyhighrmm.model.DTO.DeliveryRequestRegisterUserDTO;
import org.skyhigh.msskyhighrmm.model.UniversalUser;

import java.util.List;
import java.util.UUID;

public interface UniversalUserService {

    /**
     * Создает нового клиента
     * @param registeringUniversalUser - клиент для создания
     */
    UUID registerUser(DeliveryRequestRegisterUserDTO registeringUniversalUser);

    /**
     * Создает нового клиента
     * @param universal_user - клиент для создания
     */
    void create(UniversalUser universal_user);

    /**
     * Возвращает список всех имеющихся юзеров
     * @return список юзеров
     */
    List<UniversalUser> readAll();

    /**
     * Возвращает .юзера по его ID
     * @param id - ID юзера
     * @return - объект юзера с заданным ID
     */
    UniversalUser read(UUID id);

    /**
     * Обновляет юзера с заданным ID,
     * в соответствии с переданным юзером
     * @param universal_user - юзер в соответсвии с которым нужно обновить данные
     * @param id - id юзера которого нужно обновить
     * @return - true если данные были обновлены, иначе false
     */
    boolean update(UniversalUser universal_user, UUID id);

    /**
     * Удаляет юзера с заданным ID
     * @param id - id юзера, которого нужно удалить
     * @return - true если юзер был удален, иначе false
     */
    boolean delete(UUID id);
}