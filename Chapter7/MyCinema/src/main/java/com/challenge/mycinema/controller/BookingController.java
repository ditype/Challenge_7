package com.challenge.mycinema.controller;

import com.challenge.mycinema.model.Booking;
import com.challenge.mycinema.model.Schedule;
import com.challenge.mycinema.repository.BookingRepository;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private SortAscDesc sortAscDesc;

    // Insert Data booking
    @PostMapping("/create")
    public ResponseEntity<MessageModel> insertData(@RequestBody List<Booking> param) {
        MessageModel msg = new MessageModel();
        try {
            List<Booking> bookingList = new ArrayList<>();
            for (Booking data : param) {
                Booking booking = new Booking();
                Schedule schedule = scheduleRepository.getById(data.getScheduleId());
                String uuid = UUID.randomUUID().toString();

                booking.setBookingId(uuid);
                booking.setScheduleId(data.getScheduleId());
                booking.setUserId(data.getUserId());
                booking.setNoKursi(data.getNoKursi());
                booking.setOrderDate(new Date());
                booking.setJmlTiket(data.getJmlTiket());
                booking.setTotalHrg(schedule.getHrgTiket().multiply(BigDecimal.valueOf(data.getJmlTiket())));
                booking.setCreated(new Date());
                booking.setCreatedBy(data.getCreatedBy());
                booking.setUpdated(new Date());
                booking.setUpdatedBy("SYSTEM");
                booking.setIsactive("Y");

                bookingList.add(booking);
            }
            bookingRepository.saveAll(bookingList);

            msg.setStatus(true);
            msg.setMessage("Success to inserted data..");
            msg.setData(bookingList);

            return ResponseEntity.status(HttpStatus.OK).body(msg);
        } catch (Exception e) {
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }

    // Update Data booking
    @PutMapping("/update")
    public ResponseEntity<MessageModel> updateData(@RequestBody List<Booking> param) {
        MessageModel msg = new MessageModel();
        try {
            List<Booking> bookingList = new ArrayList<>();
            for (Booking data : param) {
                Booking booking = bookingRepository.getById(data.getBookingId());
                Schedule schedule = scheduleRepository.getById(data.getScheduleId());

                booking.setScheduleId(data.getScheduleId());
                booking.setUserId(data.getUserId());
                booking.setNoKursi(data.getNoKursi());
                booking.setOrderDate(new Date());
                booking.setJmlTiket(data.getJmlTiket());
                booking.setTotalHrg(schedule.getHrgTiket().multiply(BigDecimal.valueOf(data.getJmlTiket())));
                booking.setUpdated(new Date());
                booking.setUpdatedBy(data.getUpdatedBy());
                booking.setIsactive(data.getIsactive());

                bookingList.add(booking);
            }
            bookingRepository.saveAll(bookingList);

            msg.setStatus(true);
            msg.setMessage("Success to updated data..");
            msg.setData(bookingList);

            return ResponseEntity.status(HttpStatus.OK).body(msg);
        } catch (Exception e) {
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }

    // Delete data booking By booking_id
    @DeleteMapping("/delete/{bookingId}")
    public ResponseEntity<MessageModel> deleteById(@PathVariable("bookingId") String bookingId) {
        MessageModel msg = new MessageModel();
        try {
            bookingRepository.deleteById(bookingId);

            msg.setStatus(true);
            msg.setMessage("Success to deleted data..");
            return ResponseEntity.status(HttpStatus.OK).body(msg);
        } catch (Exception e) {
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }

    // Get Data booking By booking_id
    @GetMapping("/getData/{bookingId}")
    public ResponseEntity<MessageModel> detById(@PathVariable("bookingId") String bookingId) {
        MessageModel msg = new MessageModel();
        try {
            Booking data = bookingRepository.getById(bookingId);

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

    // Get List All Data booking
    @GetMapping("/getList")
    public ResponseEntity<MessageModel> getListData() {
        MessageModel msg = new MessageModel();
        try {
            List<Booking> data = (List<Booking>) bookingRepository.findAll();

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

    // Get Data Pagination From booking
    @GetMapping("/getPagination")
    public ResponseEntity<MessageModelPagination> getDataPagination(@RequestParam(value = "page",defaultValue = "0") Integer page,
                                                                    @RequestParam(value = "size",defaultValue = "10") Integer size,
                                                                    @RequestParam(value = "sort", required=false) String sort,
                                                                    @RequestParam(value = "urutan", required=false) String urutan) {
        MessageModelPagination msg = new MessageModelPagination();
        try {
            Sort objSort = sortAscDesc.getSortingData(sort,urutan);
            Pageable pageRequest = objSort == null ? PageRequest.of(page, size) : PageRequest.of(page, size,objSort);

            Page<Booking> data = bookingRepository.findAll(pageRequest);

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

    // Get Data booking By schedule_id
    @GetMapping("/getData/schedule/{scheduleId}")
    public ResponseEntity<MessageModel> getBySchedule(@PathVariable("scheduleId") String scheduleId) {
        MessageModel msg = new MessageModel();
        try {
            List<Booking> data = bookingRepository.getBySchedule(scheduleId);

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