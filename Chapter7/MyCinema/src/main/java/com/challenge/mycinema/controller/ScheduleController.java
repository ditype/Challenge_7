package com.challenge.mycinema.controller;

import com.challenge.mycinema.model.Schedule;
import com.challenge.mycinema.repository.ScheduleRepository;
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
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private SortAscDesc sortAscDesc;

    // Insert Data schedule
    @PostMapping("/create")
    public ResponseEntity<MessageModel> insertData(@RequestBody List<Schedule> param) {
        MessageModel msg = new MessageModel();
        try {
            List<Schedule> scheduleList = new ArrayList<>();
            for (Schedule data : param) {
                Schedule schedule = new Schedule();
                String uuid = UUID.randomUUID().toString();

                schedule.setScheduleId(uuid);
                schedule.setFilmId(data.getFilmId());
                schedule.setStudioId(data.getStudioId());
                schedule.setHrgTiket(data.getHrgTiket());
                schedule.setTglTayang(data.getTglTayang());
                schedule.setJamMulai(data.getJamMulai());
                schedule.setJamSelesai(data.getJamSelesai());
                schedule.setDesc(data.getDesc());
                schedule.setCreated(new Date());
                schedule.setCreatedBy(data.getCreatedBy());
                schedule.setUpdated(new Date());
                schedule.setUpdatedBy("SYSTEM");
                schedule.setIsactive("Y");

                scheduleList.add(schedule);
            }
            scheduleRepository.saveAll(scheduleList);

            msg.setStatus(true);
            msg.setMessage("Success to inserted data..");
            msg.setData(scheduleList);

            return ResponseEntity.status(HttpStatus.OK).body(msg);
        } catch (Exception e) {
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }

    // Update Data schedule
    @PutMapping("/update")
    public ResponseEntity<MessageModel> updateData(@RequestBody List<Schedule> param) {
        MessageModel msg = new MessageModel();
        try {
            List<Schedule> scheduleList = new ArrayList<>();
            for (Schedule data : param) {
                Schedule schedule = scheduleRepository.getById(data.getScheduleId());

                schedule.setFilmId(data.getFilmId());
                schedule.setStudioId(data.getStudioId());
                schedule.setHrgTiket(data.getHrgTiket());
                schedule.setTglTayang(data.getTglTayang());
                schedule.setJamMulai(data.getJamMulai());
                schedule.setJamSelesai(data.getJamSelesai());
                schedule.setDesc(data.getDesc());
                schedule.setUpdated(new Date());
                schedule.setUpdatedBy(data.getUpdatedBy());
                schedule.setIsactive(data.getIsactive());

                scheduleList.add(schedule);
            }
            scheduleRepository.saveAll(scheduleList);

            msg.setStatus(true);
            msg.setMessage("Success to updated data..");
            msg.setData(scheduleList);

            return ResponseEntity.status(HttpStatus.OK).body(msg);
        } catch (Exception e) {
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }

    // Delete data schedule By schedule_id
    @DeleteMapping("/delete/{scheduleId}")
    public ResponseEntity<MessageModel> deleteById(@PathVariable("scheduleId") String scheduleId) {
        MessageModel msg = new MessageModel();
        try {
            scheduleRepository.deleteById(scheduleId);

            msg.setStatus(true);
            msg.setMessage("Success to deleted data..");
            return ResponseEntity.status(HttpStatus.OK).body(msg);
        } catch (Exception e) {
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }

    // Get Data schedule By schedule_id
    @GetMapping("/getData/{scheduleId}")
    public ResponseEntity<MessageModel> detById(@PathVariable("scheduleId") String scheduleId) {
        MessageModel msg = new MessageModel();
        try {
            Schedule data = scheduleRepository.getById(scheduleId);

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

    // Get List All Data schedule
    @GetMapping("/getList")
    public ResponseEntity<MessageModel> getListData() {
        MessageModel msg = new MessageModel();
        try {
            List<Schedule> data = (List<Schedule>) scheduleRepository.findAll();

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

    // Get Data Pagination From schedule
    @GetMapping("/getPagination")
    public ResponseEntity<MessageModelPagination> getDataPagination(@RequestParam(value = "page",defaultValue = "0") Integer page,
                                                                    @RequestParam(value = "size",defaultValue = "10") Integer size,
                                                                    @RequestParam(value = "sort", required=false) String sort,
                                                                    @RequestParam(value = "urutan", required=false) String urutan) {
        MessageModelPagination msg = new MessageModelPagination();
        try {
            Sort objSort = sortAscDesc.getSortingData(sort,urutan);
            Pageable pageRequest = objSort == null ? PageRequest.of(page, size) : PageRequest.of(page, size,objSort);

            Page<Schedule> data = scheduleRepository.findAll(pageRequest);

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

    // Get Data schedule By film_id
    @GetMapping("/getData/film/{filmId}")
    public ResponseEntity<MessageModel> getByFilm(@PathVariable("filmId") String filmId) {
        MessageModel msg = new MessageModel();
        try {
            List<Schedule> data = scheduleRepository.getByFilm(filmId);

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

}