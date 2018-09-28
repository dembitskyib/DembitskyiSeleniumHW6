package com.epam.lab.parsers;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.epam.lab.model.Users;

public class JAXBParser {
	public static Users getUsers(String usersDataPath) {
		JAXBContext jaxbContext;
		Users users = null;
		try {
			jaxbContext = JAXBContext.newInstance(Users.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			users = (Users) jaxbUnmarshaller.unmarshal(new File(usersDataPath));
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return users;
	}
}
