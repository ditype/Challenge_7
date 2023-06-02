package com.challenge.mycinema.controller;

import com.challenge.mycinema.model.Booking;
import com.challenge.mycinema.model.Payment;
import com.challenge.mycinema.repository.BookingRepository;
import com.challenge.mycinema.repository.PaymentRepository;
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
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private SortAscDesc sortAscDesc;

    // Insert Data payment
    @PostMapping("/create")
    public ResponseEntity<MessageModel> insertData(@RequestBody List<Payment> param) {
        MessageModel msg = new MessageModel();
        try {
            List<Payment> paymentList = new ArrayList<>();
            List validasi = new ArrayList();
            for (Payment data : param) {
                Payment payment = new Payment();
                Booking booking = bookingRepository.getById(data.getBookingId());
                String uuid = UUID.randomUUID().toString();

                // Validasi 1 : pembayaran < total tagihan harga
                if (data.getTotalPayment().compareTo(booking.getTotalHrg()) < 0) {
                    validasi.add("Total pembayaran kurang!");
                }
                // Validasi 2 : double payment
                if (booking != null) {
                    validasi.add("Pembayaran telah dilakukan!");
                }

                payment.setPaymentId(uuid);
                payment.setBookingId(data.getBookingId());
                payment.setPaymentDate(new Date());
                payment.setTotalPayment(data.getTotalPayment());
                payment.setPaymentMethod(data.getPaymentMethod());
                payment.setStatus("On Process");
                payment.setCreated(new Date());
                payment.setCreatedBy(data.getCreatedBy());
                payment.setUpdated(new Date());
                payment.setUpdatedBy("SYSTEM");
                payment.setIsactive("Y");

                paymentList.add(payment);
            }
            if (validasi.size() > 0) {
                msg.setStatus(false);
                msg.setMessage(validasi.toString());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
            } else {
                paymentRepository.saveAll(paymentList);

                msg.setStatus(true);
                msg.setMessage("Success to inserted data..");
                msg.setData(paymentList);

                return ResponseEntity.status(HttpStatus.OK).body(msg);
            }
        } catch (Exception e) {
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }

    // Update Data payment
    @PutMapping("/update")
    public ResponseEntity<MessageModel> updateData(@RequestBody List<Payment> param) {
        MessageModel msg = new MessageModel();
        try {
            List<Payment> paymentList = new ArrayList<>();
            List validasi = new ArrayList();
            for (Payment data : param) {
                Payment payment = paymentRepository.getById(data.getPaymentId());
                Booking booking = bookingRepository.getById(data.getBookingId());

                // Validasi 1 : pembayaran < total tagihan harga
                if (data.getTotalPayment().compareTo(booking.getTotalHrg()) < 0) {
                    validasi.add("Total pembayaran kurang!");
                }

                payment.setBookingId(data.getBookingId());
                payment.setPaymentDate(data.getPaymentDate());
                payment.setTotalPayment(data.getTotalPayment());
                payment.setPaymentMethod(data.getPaymentMethod());
                payment.setStatus(data.getStatus());
                payment.setUpdated(new Date());
                payment.setUpdatedBy(data.getUpdatedBy());
                payment.setIsactive(data.getIsactive());

                paymentList.add(payment);
            }
            if (validasi.size() > 0) {
                msg.setStatus(false);
                msg.setMessage(validasi.toString());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
            } else {
                paymentRepository.saveAll(paymentList);

                msg.setStatus(true);
                msg.setMessage("Success to updated data..");
                msg.setData(paymentList);

                return ResponseEntity.status(HttpStatus.OK).body(msg);
            }
        } catch (Exception e) {
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }

    // Delete data payment By payment_id
    @DeleteMapping("/delete/{paymentId}")
    public ResponseEntity<MessageModel> deleteById(@PathVariable("paymentId") String paymentId) {
        MessageModel msg = new MessageModel();
        try {
            paymentRepository.deleteById(paymentId);

            msg.setStatus(true);
            msg.setMessage("Success to deleted data..");
            return ResponseEntity.status(HttpStatus.OK).body(msg);
        } catch (Exception e) {
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }

    // Get Data payment By payment_id
    @GetMapping("/getData/{paymentId}")
    public ResponseEntity<MessageModel> detById(@PathVariable("paymentId") String paymentId) {
        MessageModel msg = new MessageModel();
        try {
            Payment data = paymentRepository.getById(paymentId);

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

    // Get List All Data payment
    @GetMapping("/getList")
    public ResponseEntity<MessageModel> getListData() {
        MessageModel msg = new MessageModel();
        try {
            List<Payment> data = (List<Payment>) paymentRepository.findAll();

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

    // Get Data Pagination From payment
    @GetMapping("/getPagination")
    public ResponseEntity<MessageModelPagination> getDataPagination(@RequestParam(value = "page",defaultValue = "0") Integer page,
                                                                    @RequestParam(value = "size",defaultValue = "10") Integer size,
                                                                    @RequestParam(value = "sort", required=false) String sort,
                                                                    @RequestParam(value = "urutan", required=false) String urutan) {
        MessageModelPagination msg = new MessageModelPagination();
        try {
            Sort objSort = sortAscDesc.getSortingData(sort,urutan);
            Pageable pageRequest = objSort == null ? PageRequest.of(page, size) : PageRequest.of(page, size,objSort);

            Page<Payment> data = paymentRepository.findAll(pageRequest);

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

    // Get Data payment By booking_id
    @GetMapping("/getData/booking/{bookingId}")
    public ResponseEntity<MessageModel> getByBooking(@PathVariable("bookingId") String bookingId) {
        MessageModel msg = new MessageModel();
        try {
            List<Payment> data = paymentRepository.getByBooking(bookingId);

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