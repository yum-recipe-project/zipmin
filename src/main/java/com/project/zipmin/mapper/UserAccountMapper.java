package com.project.zipmin.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.project.zipmin.dto.UserAccountDTO;
import com.project.zipmin.entity.UserAccount;

@Mapper(componentModel = "spring")
public interface UserAccountMapper {
	UserAccountDTO userAccountToUserAccountDTO(UserAccount userAccount);
	UserAccount userAccountDTOToUserAccount(UserAccountDTO userAccountDTO);
	List<UserAccountDTO> userAccountListToUserAccountDTOList(List<UserAccount> userAccountList);
}
