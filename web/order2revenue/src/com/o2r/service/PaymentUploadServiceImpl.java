package com.o2r.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.o2r.dao.PaymentUploadDao;
import com.o2r.helper.CustomException;
import com.o2r.model.PaymentUpload;

/**
 * @author Deep Mehrotra
 *
 */
@Service("paymentUploadService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PaymentUploadServiceImpl implements PaymentUploadService {

	@Autowired
	private PaymentUploadDao paymentUploadDao;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addPaymentUpload(PaymentUpload upload, int sellerId)
			throws CustomException {
		paymentUploadDao.addPaymentUpload(upload, sellerId);
	}

	@Override
	public List<PaymentUpload> listPaymentUploads(int sellerId)
			throws CustomException {

		return paymentUploadDao.listPaymentUploads(sellerId);
	}

	@Override
	public PaymentUpload getPaymentUpload(int paymentUploadId)
			throws CustomException {
		return paymentUploadDao.getPaymentUpload(paymentUploadId);
	}

	@Override
	public void deletePaymentUpload(PaymentUpload payupload, int sellerId)
			throws CustomException {
		paymentUploadDao.deletePaymentUpload(payupload, sellerId);
	}

	@Override
	public PaymentUpload getManualPayment(int sellerId) throws CustomException {
		return paymentUploadDao.getManualPayment(sellerId);
	}

}