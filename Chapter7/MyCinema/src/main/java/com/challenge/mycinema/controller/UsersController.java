package com.challenge.mycinema.controller;

import com.challenge.mycinema.model.Users;
import com.challenge.mycinema.repository.UsersRepository;
import com.challenge.mycinema.service.SortAscDesc;
import com.challenge.mycinema.utils.MessageModel;
import com.challenge.mycinema.utils.MessageModelPagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private SortAscDesc sortAscDesc;

    // Insert Data users
    @PostMapping("/create")
    public ResponseEntity<MessageModel> insertData(@RequestBody List<Users> param) {
        MessageModel msg = new MessageModel();
        try {
            List<Users> usersList = new ArrayList<>();
            for (Users data : param) {
                Users users = new Users();
                String uuid = UUID.randomUUID().toString();

                users.setUserId(uuid);
                users.setEmail(data.getEmail());
                users.setNoTelp(data.getNoTelp());
                users.setUsername(data.getUsername());
                users.setPassword(data.getPassword());
                users.setLevelUser(data.getLevelUser());
                users.setCreated(new Date());
                users.setCreatedBy(data.getCreatedBy());
                users.setUpdated(new Date());
                users.setUpdatedBy("SYSTEM");
                users.setIsactive("Y");

                usersList.add(users);
            }
            usersRepository.saveAll(usersList);

            msg.setStatus(true);
            msg.setMessage("Success to inserted data..");
            msg.setData(usersList);

            return ResponseEntity.status(HttpStatus.OK).body(msg);
        } catch (Exception e) {
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }

    // Update Data users
    @PutMapping("/update")
    public ResponseEntity<MessageModel> updateData(@RequestBody List<Users> param) {
        MessageModel msg = new MessageModel();
        try {
            List<Users> usersList = new ArrayList<>();
            for (Users data : param) {
                Users users = usersRepository.getById(data.getUserId());

                users.setEmail(data.getEmail());
                users.setNoTelp(data.getNoTelp());
                users.setUsername(data.getUsername());
                users.setPassword(data.getPassword());
                users.setLevelUser(data.getLevelUser());
                users.setUpdated(new Date());
                users.setUpdatedBy(data.getUpdatedBy());
                users.setIsactive(data.getIsactive());

                usersList.add(users);
            }
            usersRepository.saveAll(usersList);

            msg.setStatus(true);
            msg.setMessage("Success to updated data..");
            msg.setData(usersList);

            return ResponseEntity.status(HttpStatus.OK).body(msg);
        } catch (Exception e) {
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }

    // Delete data users By user_id
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<MessageModel> deleteById(@PathVariable("userId") String userId) {
        MessageModel msg = new MessageModel();
        try {
            usersRepository.deleteById(userId);

            msg.setStatus(true);
            msg.setMessage("Success to deleted data..");
            return ResponseEntity.status(HttpStatus.OK).body(msg);
        } catch (Exception e) {
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }

    // Get Data users By user_id
    @GetMapping("/getData/{userId}")
    public ResponseEntity<MessageModel> getById(@PathVariable("userId") String userId) {
        MessageModel msg = new MessageModel();
        try {
            Users data = usersRepository.getById(userId);

            msg.setStatus(true);
            msg.setMessage("Success to get data..");
            msg.setData(data);

            return ResponseEntity.status(HttpStatus.OK).body(msg);
        } catch (Exception e) {
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }

    // Get List All Data users
    @GetMapping("/getList")
    public ResponseEntity<MessageModel> getListData() {
        MessageModel msg = new MessageModel();
        try {
            List<Users> data = (List<Users>) usersRepository.findAll();

            msg.setStatus(true);
            msg.setMessage("Success to get all data..");
            msg.setData(data);

            return ResponseEntity.status(HttpStatus.OK).body(msg);
        } catch (Exception e) {
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }

    // Get Data Pagination From users
    @GetMapping("/getPagination")
    public ResponseEntity<MessageModelPagination> getDataPagination(@RequestParam(value = "page",defaultValue = "0") Integer page,
                                                                    @RequestParam(value = "size",defaultValue = "10") Integer size,
                                                                    @RequestParam(value = "sort", required=false) String sort,
                                                                    @RequestParam(value = "urutan", required=false) String urutan) {
        MessageModelPagination msg = new MessageModelPagination();
        try {
            Sort objSort = sortAscDesc.getSortingData(sort,urutan);
            Pageable pageRequest = objSort == null ? PageRequest.of(page, size) : PageRequest.of(page, size,objSort);

            Page<Users> data = usersRepository.findAll(pageRequest);

            msg.setStatus(true);
            msg.setMessage("Success to get all data..");
            msg.setData(data.getContent());
            msg.setCurrentPage(data.getNumber());
            msg.setTotalPages(data.getTotalPages());
            msg.setTotalItems((int) data.getTotalElements());
            msg.setNumberOfElement(data.getNumberOfElements());

            return ResponseEntity.status(HttpStatus.OK).body(msg);
        } catch (Exception e) {
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }

}