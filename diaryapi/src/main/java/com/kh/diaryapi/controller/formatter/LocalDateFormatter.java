package com.kh.diaryapi.controller.formatter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.expression.ParseException;
import org.springframework.format.Formatter;

public class LocalDateFormatter implements Formatter<LocalDate> {
	@Override
	public String print(LocalDate object, Locale locale) {
		return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(object);
	}

	@Override
	public LocalDate parse(String text, Locale locale) throws ParseException {
		return LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}
}