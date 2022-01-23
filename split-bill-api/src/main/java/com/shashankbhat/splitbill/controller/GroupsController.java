package com.shashankbhat.splitbill.controller;

import com.shashankbhat.splitbill.dto.groups.GroupsAllDataDto;
import com.shashankbhat.splitbill.dto.groups.GroupsEntityDto;
import com.shashankbhat.splitbill.dto.groups.GroupsSaveDto;
import com.shashankbhat.splitbill.entity.GroupsEntity;

import com.shashankbhat.splitbill.entity.UsersEntity;
import com.shashankbhat.splitbill.repository.GroupsRepository;
import com.shashankbhat.splitbill.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/group")
class GroupsController {
    @Autowired
    private GroupsRepository groupsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @PostMapping(value = "/saveGroup")
    public ResponseEntity<Integer> saveGroup(@RequestBody @Valid GroupsSaveDto group) {
        GroupsEntity result = groupsRepository.save(new GroupsEntity(null, group.name, System.currentTimeMillis()));
        return new ResponseEntity<Integer>(result.getId(), HttpStatus.OK);
    }

    @GetMapping(value = "/allGroups")
    public ResponseEntity<?> getAllGroups() {
        List<GroupsEntity> result = groupsRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        List<GroupsEntityDto> response = new ArrayList<>();
        result.forEach(groupsEntity -> {
            List<UsersEntity> users = usersRepository.findByGroupId(groupsEntity.getId());
            response.add(new GroupsEntityDto(groupsEntity, users));
        });

        return new ResponseEntity<>(new GroupsAllDataDto(response), HttpStatus.OK);
    }
}


