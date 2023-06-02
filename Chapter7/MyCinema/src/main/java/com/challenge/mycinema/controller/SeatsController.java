package com.challenge.mycinema.controller;

import com.challenge.mycinema.model.Seats;
import com.challenge.mycinema.repository.SeatsRepository;
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
@RequestMapping("/seats")
public class SeatsController {
    @Autowired
    private SeatsRepository seatsRepository;

    @Autowired
    private SortAscDesc sortAscDesc;

    // Insert Data seats
    @PostMapping("/create")
    public ResponseEntity<MessageModel> insertData(@RequestBody List<Seats> param) {
        MessageModel msg = new MessageModel();
        try {
            List<Seats> seatsList = new ArrayList<>();
            for (Seats data : param) {
                Seats seats = new Seats();
                String uuid = UUID.randomUUID().toString();

                seats.setSeatId(uuid);
                seats.setNoKursi(data.getNoKursi());
                seats.setStudioId(data.getStudioId());
                seats.setCreated(new Date());
                seats.setCreatedBy(data.getCreatedBy());
                seats.setUpdated(new Date());
                seats.setUpdatedBy("SYSTEM");
                seats.setIsactive("Y");

                seatsList.add(seats);
            }
            seatsRepository.saveAll(seatsList);

            msg.setStatus(true);
            msg.setMessage("Success to inserted data..");
            msg.setData(seatsList);

            return ResponseEntity.status(HttpStatus.OK).body(msg);
        } catch (Exception e) {
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }

    // Update Data seats
    @PutMapping("/update")
    public ResponseEntity<MessageModel> updateData(@RequestBody List<Seats> param) {
        MessageModel msg = new MessageModel();
        try {
            List<Seats> seatsList = new ArrayList<>();
            for (Seats data : param) {
                Seats seats = seatsRepository.getById(data.getSeatId());

                seats.setNoKursi(data.getNoKursi());
                seats.setStudioId(data.getStudioId());
                seats.setUpdated(new Date());
                seats.setUpdatedBy(data.getUpdatedBy());
                seats.setIsactive(data.getIsactive());

                seatsList.add(seats);
            }
            seatsRepository.saveAll(seatsList);

            msg.setStatus(true);
            msg.setMessage("Success to updated data..");
            msg.setData(seatsList);

            return ResponseEntity.status(HttpStatus.OK).body(msg);
        } catch (Exception e) {
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }

    // Delete Data seats By seat_id
    @DeleteMapping("/delete/{seatId}")
    public ResponseEntity<MessageModel> deleteById(@PathVariable("seatId") String seatId) {
        MessageModel msg = new MessageModel();
        try {
            seatsRepository.deleteById(seatId);

            msg.setStatus(true);
            msg.setMessage("Success to deleted data..");
            return ResponseEntity.status(HttpStatus.OK).body(msg);
        } catch (Exception e) {
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }

    // Get Data seats By seat_id
    @GetMapping("/getData/{seatId}")
    public ResponseEntity<MessageModel> getById(@PathVariable("seatId") String seatId) {
        MessageModel msg = new MessageModel();
        try {
            Seats data = seatsRepository.getById(seatId);

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

    // Get List Data seats
    @GetMapping("/getList")
    public ResponseEntity<MessageModel> getListData() {
        MessageModel msg = new MessageModel();
        try {
            List<Seats> data = (List<Seats>) seatsRepository.findAll();

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

    // Get Data Pagination From seats
    @GetMapping("/getPagination")
    public ResponseEntity<MessageModelPagination> getDataPagination(@RequestParam(value = "page",defaultValue = "0") Integer page,
                                                                    @RequestParam(value = "size",defaultValue = "10") Integer size,
                                                                    @RequestParam(value = "sort", required=false) String sort,
                                                                    @RequestParam(value = "urutan", required=false) String urutan) {
        MessageModelPagination msg = new MessageModelPagination();
        try {
            Sort objSort = sortAscDesc.getSortingData(sort,urutan);
            Pageable pageRequest = objSort == null ? PageRequest.of(page, size) : PageRequest.of(page, size,objSort);

            Page<Seats> data = seatsRepository.findAll(pageRequest);

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