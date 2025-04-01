package com.api.b_plus_studio.Utilities;

import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Slf4j
public final class DateUtils {

    public static final String DATETIME_RESPONSE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSSZ";

    public DateUtils() {

    }

    public static LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    public static String getCurrentDateInResponseFormat() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_RESPONSE_FORMAT);
        return sdf.format(new Date());
    }

    public static String dateFormatConverter(final String currentFormat, final String actualFormat, final String dateToConvert) {
        try {
            DateFormat from = new SimpleDateFormat(currentFormat); // current format
            DateFormat to = new SimpleDateFormat(actualFormat); // wanted format
            return to.format(from.parse(dateToConvert));
        } catch (final Exception e) {
            log.error("Failed to convert string to date: {}", dateToConvert);
            return "";
        }
    }

}
