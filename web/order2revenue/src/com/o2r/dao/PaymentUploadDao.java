package com.o2r.dao;

import java.util.List;

import com.o2r.helper.CustomException;
import com.o2r.model.PaymentUpload;

/**
 * @author Deep Mehrotra
 *
 */
public interface PaymentUploadDao {
 
 public String addPaymentUpload(PaymentUpload upload , int sellerId) throws CustomException;

 public List<PaymentUpload> listPaymentUploads(int sellerId)throws CustomException;
 
 public PaymentUpload getPaymentUpload(int paymentUploadId)throws CustomException;
 
 public void deletePaymentUpload(PaymentUpload payupload,int sellerId)throws CustomException;
 
 public PaymentUpload getManualPayment(int sellerId)throws CustomException;
}