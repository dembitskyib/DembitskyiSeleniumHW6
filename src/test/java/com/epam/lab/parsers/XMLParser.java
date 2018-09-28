package com.epam.lab.parsers;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.epam.lab.model.User;
import com.epam.lab.model.Users;

public class XMLParser {
	private DocumentBuilder documentBuilder;
	private Document document;

	public XMLParser(String XMLPath) {
		try {
			documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			document = documentBuilder.parse(XMLPath);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}

	public void getUsers() {
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(Users.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Users users = (Users) jaxbUnmarshaller.unmarshal(new File("src/test/resources/users.xml"));
			for (User user : users.getUsers()) {
				System.out.println(user.getEmail());
				System.out.println(user.getPassword());
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

	public String getProperty(String tagName) {
		return document.getElementsByTagName(tagName).item(0).getTextContent();
	}
}
