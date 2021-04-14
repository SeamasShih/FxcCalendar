package com.honhai.foxconn.fxccalendar.data.holiday;

import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

/**
 * This interface defines methods to consume the Holidays API
 *
 * @author rakesh
 */
public interface APIConsumer {

    /**
     * Call the holidays api and send back the response encapsulatd in a {@link HolidayAPIResponse} object
     *
     * @param queryParams - the params encapsulated in an object
     * @return an object representation of the response
     * @throws IOException - when there are connection issues or the URL is malformed
     */
    HolidayAPIResponse getHolidays(QueryParams queryParams) throws IOException, SAXException, ParserConfigurationException;

    /**
     * Call the holidays api and send back the response as a json string
     *
     * @param queryParams - the params encapsulated in an object
     * @return an object representation of the response
     * @throws IOException - when there are connection issues or the URL is malformed
     */
    String getHolidaysAsString(QueryParams queryParams) throws IOException;
}
