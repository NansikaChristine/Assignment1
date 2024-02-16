package com.book;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		try {
			//Books.TruncateTableBooks();
			//ObjectMapper mapper = new XmlMapper();
			FileInputStream inputStream = new FileInputStream("D:\\GJANAM\\Assignment1\\Project\\src\\main\\java\\com\\book\\Book.xml");
			ObjectMapper mapper = new XmlMapper();
			TypeReference<List<Books>> typeReference = new TypeReference<>() {
			};

			List<Books> booksList = mapper.readValue(inputStream, typeReference);
			inputStream.close();


			for (Books book : booksList) {
//				int ini = book.getId();
//				JacksonInject.Value getId = null;
//				if (!getId.equals(ini)) {
				book.Database();
				}
		} catch (Exception e) {
//			e.printStackTrace();
		}
//		throw new RuntimeException();
		}
}
