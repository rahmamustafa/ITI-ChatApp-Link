package gov.iti.link.business.mappers;

import gov.iti.link.business.DTOs.GroupDto;
import gov.iti.link.persistence.entities.GroupEntity;

public class GroupMapper {
    public static GroupEntity dtoToEntity(GroupDto groupDto) {
        GroupEntity groupEntity = new GroupEntity(groupDto.getGroupId(), groupDto.getGroupName(),groupDto.getAdminPhone(),groupDto.getPicture());

        return groupEntity;
    }

    public static GroupDto entityToDTO(GroupEntity groupEntity) {
        GroupDto groupDto = new GroupDto(groupEntity.getGroupId(), groupEntity.getGroupName(),groupEntity.getAdminPhone(),groupEntity.getPicture());
        return groupDto;

    }
}
