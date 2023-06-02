package com.challenge.mycinema.controller;

import com.challenge.mycinema.model.Studio;
import com.challenge.mycinema.repository.StudioRepository;
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
@RequestMapping("/studio")
public class StudioController {
    @Autowired
    private StudioRepository studioRepository;

    @Autowired
    private SortAscDesc sortAscDesc;

    // Insert Data studio
    @PostMapping("/create")
    public ResponseEntity<MessageModel> insertData(@RequestBody List<Studio> param) {
        MessageModel msg = new MessageModel();
        try {
            List<Studio> studioList = new ArrayList<>();
            for (Studio data : param) {
                Studio studio = new Studio();
                String uuid = UUID.randomUUID().toString();

                studio.setStudioId(uuid);
                studio.setKode(data.getKode());
                studio.setNama(data.getNama());
                studio.setQtySeat(data.getQtySeat());
                studio.setCreated(new Date());
                studio.setCreatedBy(data.getCreatedBy());
                studio.setUpdated(new Date());
                studio.setUpdatedBy("SYSTEM");
                studio.setIsactive("Y");

                studioList.add(studio);
            }
            studioRepository.saveAll(studioList);

            msg.setStatus(true);
            msg.setMessage("Success to inserted data..");
            msg.setData(studioList);

            return ResponseEntity.status(HttpStatus.OK).body(msg);
        } catch (Exception e) {
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }

    // Update Data studio
    @PutMapping("/update")
    public ResponseEntity<MessageModel> updateData(@RequestBody List<Studio> param) {
        MessageModel msg = new MessageModel();
        try {
            List<Studio> studioList = new ArrayList<>();
            for (Studio data : param) {
                Studio studio = studioRepository.getById(data.getStudioId());

                studio.setKode(data.getKode());
                studio.setNama(data.getNama());
                studio.setQtySeat(data.getQtySeat());
                studio.setUpdated(new Date());
                studio.setUpdatedBy(data.getUpdatedBy());
                studio.setIsactive(data.getIsactive());

                studioList.add(studio);
            }
            studioRepository.saveAll(studioList);

            msg.setStatus(true);
            msg.setMessage("Success to updated data..");
            msg.setData(studioList);

            return ResponseEntity.status(HttpStatus.OK).body(msg);
        } catch (Exception e) {
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }

    // Delete Data studio By studio_id
    @DeleteMapping("/delete/{studioId}")
    public ResponseEntity<MessageModel> deleteById(@PathVariable("studioId") String studioId) {
        MessageModel msg = new MessageModel();
        try {
            studioRepository.deleteById(studioId);

            msg.setStatus(true);
            msg.setMessage("Success to deleted data..");
            return ResponseEntity.status(HttpStatus.OK).body(msg);
        } catch (Exception e) {
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }

    // Get Data studio By studio_id
    @GetMapping("/getData/{studioId}")
    public ResponseEntity<MessageModel> getById(@PathVariable("studioId") String studioId) {
        MessageModel msg = new MessageModel();
        try {
            Studio data = studioRepository.getById(studioId);

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

    // Get List Data studio
    @GetMapping("/getList")
    public ResponseEntity<MessageModel> getListData() {
        MessageModel msg = new MessageModel();
        try {
            List<Studio> data = (List<Studio>) studioRepository.findAll();

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

    // Get Data Pagination From studio
    @GetMapping("/getPagination")
    public ResponseEntity<MessageModelPagination> getDataPagination(@RequestParam(value = "page",defaultValue = "0") Integer page,
                                                                    @RequestParam(value = "size",defaultValue = "10") Integer size,
                                                                    @RequestParam(value = "sort", required=false) String sort,
                                                                    @RequestParam(value = "urutan", required=false) String urutan) {
        MessageModelPagination msg = new MessageModelPagination();
        try {
            Sort objSort = sortAscDesc.getSortingData(sort,urutan);
            Pageable pageRequest = objSort == null ? PageRequest.of(page, size) : PageRequest.of(page, size,objSort);

            Page<Studio> data = studioRepository.findAll(pageRequest);

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