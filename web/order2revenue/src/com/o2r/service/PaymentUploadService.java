package com.o2r.service;

import java.util.List;

import com.o2r.model.PaymentUpload;

/**
 * @author Deep Mehrotra
 *
 */
public interface PaymentUploadService {
 
 public void addPaymentUpload(PaymentUpload expense , int sellerId);

 public List<PaymentUpload> listPaymentUploads(int sellerId);
 
 public PaymentUpload getPaymentUpload(int paymentUploadId);
 
 public void deletePaymentUpload(PaymentUpload payupload,int sellerId);
 
 public PaymentUpload getManualPayment(int sellerId);
}