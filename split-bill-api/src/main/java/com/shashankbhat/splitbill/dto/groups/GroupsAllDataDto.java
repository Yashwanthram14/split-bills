package com.shashankbhat.splitbill.database.local.dto.groups;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupsAllDataDto {
    List<GroupsEntityDto> data;
}
