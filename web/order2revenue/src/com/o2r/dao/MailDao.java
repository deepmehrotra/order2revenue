package com.o2r.dao;

import java.util.Properties;

public interface MailDao {
	public void sendMail(Properties props,String email);
}
