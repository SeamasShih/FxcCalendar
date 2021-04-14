package com.honhai.foxconn.fxccalendar.data.holiday;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * This class implements the methods defines in the interface {@link APIConsumer}.
 *
 * @author rakesh
 */
public class HolidayAPIConsumer implements APIConsumer {

    //store the base url in a variable for later use
    private String baseURl;
    //to make a https connection
    private URLConnection connection;

    /**
     * Parameterized constructor
     *
     * @param baseURl - the base url of the API
     */
    public HolidayAPIConsumer(String baseURl) {
        this.baseURl = baseURl;
    }

    /**
     * Call the holidays api and send back the response encapsulatd in a {@link HolidayAPIResponse} object.
     * This is a wrapper method that depends on {@link HolidayAPIConsumer#getHolidaysAsString(QueryParams)}
     * to get the json string and then map it to a java object
     *
     * @param queryParams - the params encapsulated in an object
     * @return an object representation of the response
     * @throws IOException - when there are connection issues or the URL is malformed
     */
    @Override
    public HolidayAPIResponse getHolidays(QueryParams queryParams) throws IOException, ParserConfigurationException, SAXException {
        //get the response string
        String json = getHolidaysAsString(queryParams);
        Log.d("Seamas","j = " + json);

        HolidayAPIResponse response = new HolidayAPIResponse();

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(json));
        Document doc = dBuilder.parse(is);
        doc.getDocumentElement().normalize();

        response = createHolidayAPIResponse(response,doc);

        //map and return the response encapsulated in HolidayAPIResponse object
        return response;
    }

    private HolidayAPIResponse createHolidayAPIResponse(HolidayAPIResponse response ,Document doc){
        Element status = (Element) doc.getElementsByTagName(response.LABEL_STATUS).item(0);
        response.setStatus(Integer.valueOf(status.getTextContent()));

        List<Holiday> holidayList = new ArrayList<>();
        NodeList holidays = doc.getElementsByTagName(response.LABEL_HOLIDAYS);
        for (int i = 0; i < holidays.getLength(); i++) {
            Element holiday = (Element) holidays.item(i);
            Element name = (Element) holiday.getElementsByTagName(response.LABEL_HOLIDAYS_HOLIDAY_NAME).item(0);
            Element date = (Element) holiday.getElementsByTagName(response.LABEL_HOLIDAYS_HOLIDAY_DATE).item(0);
            Element observed = (Element) holiday.getElementsByTagName(response.LABEL_HOLIDAYS_HOLIDAY_OBSERVED).item(0);
            Element _public = (Element) holiday.getElementsByTagName(response.LABEL_HOLIDAYS_HOLIDAY_PUBLIC).item(0);

            if (name == null)
                return response;

            Holiday h = new Holiday();
            h.setName(name.getTextContent());
            h.setDate(date.getTextContent());
            h.setObserved(observed.getTextContent());
            h.set_public(Boolean.valueOf(_public.getTextContent()));
            holidayList.add(h);
        }
        response.setHolidays(holidayList);
        return response;
    }

    /**
     * Call the holidays api and send back the response as a json string
     *
     * @param queryParams - the params encapsulated in an object
     * @return an object representation of the response
     * @throws IOException - when there are connection issues or the URL is malformed
     */
    @Override
    public String getHolidaysAsString(QueryParams queryParams) throws IOException {

        //construct the complete url
        URL url = new URL(this.baseURl + "?" + queryParams.queryString());

        //attempt the https connection
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.connect();

        //uninitialized objects for later use
        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader;
        String line;

        int responseCode = connection.getResponseCode();
        //check for the response code
        if (responseCode == 200) {
            //read from inputstream if response code is 200
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } else {
            //read from errorstream when the response code is not 200
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        }

        //read the contents of the response
        while ((line = bufferedReader.readLine()) != null) {
            builder.append(line);
        }
        bufferedReader.close();

        //return the response
        return builder.toString();
    }
}