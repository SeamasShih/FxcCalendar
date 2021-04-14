package com.honhai.foxconn.fxccalendar.data.holiday;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * This class is used to encapsulate the query parameters for the API call.
 * The methods can be chained together for easier and readable coding
 *
 * This class also provides the following enums which is associated with the parameter values.
 * <ul>
 *     <li>{@link APIParameter} - enumeration of keys for allowed parameters</li>
 *     <li>{@link Country} - enumeration of allowed country codes for the parameter <code>{@link APIParameter#COUNTRY}</code></li>
 *     <li>{@link Format} - enumeration of available response formats</li>
 * </ul>
 */
public class QueryParams {

    private Map<String, Object> params;

    public QueryParams() {
        params = new HashMap<>();
    }

    public QueryParams key(String key) {
        params.put(APIParameter.API_KEY.toString(), key);
        return this;
    }

    public QueryParams country(Country country) {
        params.put(APIParameter.COUNTRY.toString(), country.code());
        return this;
    }

    public QueryParams year(int year) {
        params.put(APIParameter.YEAR.toString(), year);
        return this;
    }

    public QueryParams month(int month) {
        params.put(APIParameter.MONTH.toString(), month);
        return this;
    }

    public QueryParams day(int day) {
        params.put(APIParameter.DAY.toString(), day);
        return this;
    }

    public QueryParams previous(boolean previous) {
        params.put(APIParameter.PREVIOUS.toString(), previous);
        return this;
    }

    public QueryParams upcoming(boolean upcoming) {
        params.put(APIParameter.UPCOMING.toString(), upcoming);
        return this;
    }

    public QueryParams isPublic(boolean _public) {
        params.put(APIParameter.PUBLIC.toString(), _public);
        return this;
    }

    public QueryParams format(Format format) {
        params.put(APIParameter.FORMAT.toString(), format.value);
        return this;
    }

    public QueryParams pretty(boolean pretty) {
        params.put(APIParameter.PRETTY.toString(), pretty);
        return this;
    }

    /**
     * Return the query string
     * @return
     */
    public String queryString() {

        if (params.isEmpty()) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        Iterator<Map.Entry<String, Object>> it = params.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry e = it.next();
            builder.append(e.getKey()).append("=").append(e.getValue());
            if (it.hasNext()) {
                builder.append("&");
            }
        }

        return builder.toString();
    }

    @Override
    public String toString() {
        return queryString();
    }

    /**
     * Enumeration of allowed query parameters
     */
    public enum APIParameter {
        API_KEY("key"),
        COUNTRY("country"),
        YEAR("year"),
        MONTH("month"),
        DAY("day"),
        PREVIOUS("previous"),
        UPCOMING("upcoming"),
        PUBLIC("public"),
        FORMAT("format"),
        PRETTY("pretty");

        private String param;

        APIParameter(String param) {
            this.param = param;
        }

        @Override
        public String toString() {
            return param;
        }
    }

    /**
     * Enumeration of allowed country codes for the parameter {@link APIParameter#COUNTRY}
     */
    public enum Country {
        ARGENTINA("AR"),
        ANGOLA("AO"),
        AUSTRIA("AT"),
        AUSTRALIA("AU"),
        ARUBA("AW"),
        ÅLAND_ISLANDS("AX"),
        BOSNIA_AND_HERZEGOVINA("BA"),
        BELGIUM("BE"),
        BULGARIA("BG"),
        BOLIVIA("BO"),
        BRAZIL("BR"),
        THE_BHAMAS("BS"),
        CANADA("CA"),
        SWITZERLAND("CH"),
        CHINA("CN"),
        COLOMBIA("CO"),
        Costa_Rica("CR"),
        CUBA("CU"),
        CZECH_REPUBLIC("CZ"),
        GERMANY("DE"),
        DENMARK("DK"),
        DOMINICAN_REPUBLIC("DO"),
        ECUADOR("EC"),
        SPAIN("ES"),
        FINLAND("FI"),
        FRANCE("FR"),
        UNITED_KINGDOM("GB"),
        ENGLAND("GB-ENG"),
        NORTHERN_IRELAND("GB-NIR"),
        SCOTLAND("GB-SCT"),
        WALES("GB-WLS"),
        GREECE("GR"),
        GUATEMALA("GT"),
        HONG_KONG("HK"),
        HONDURAS("HN"),
        CROATIA("HR"),
        HUNGARY("HU"),
        INDONESIA("ID"),
        IRELAND("IE"),
        INDIA("IN"),
        ISRAEL("IL"),
        ICELAND("IS"),
        ITALY("IT"),
        JAPAN("JP"),
        KAZAKHSTAN("KZ"),
        LESOTHO("LS"),
        LUXEMBOURG("LU"),
        MADAGASCAR("MG"),
        MARTINIQUE("MQ"),
        MALTA("MT"),
        MAURITIUS("MU"),
        MEXICO("MX"),
        MOZAMBIQUE("MZ"),
        NIGERIA("NG"),
        NETHERLANDS("NL"),
        NORWAY("NO"),
        PERU("PE"),
        PAKISTAN("PK"),
        PHILIPPINES("PH"),
        POLAND("PL"),
        PUERTO_RICO("PR"),
        PORTUGAL("PT"),
        PARAGUAY("PY"),
        RéUNION("RE"),
        ROMANIA("RO"),
        RUSSIA("RU"),
        SEYCHELLES("SC"),
        SWEDEN("SE"),
        SINGAPORE("SG"),
        SLOVENIA("SI"),
        SAO_TOME_AND_PRINCIPE("ST"),
        SLOVAKIA("SK"),
        TUNISIA("TN"),
        TURKEY("TR"),
        UKRAINE("UA"),
        UNITED_STATES("US"),
        URUGUAY("UY"),
        VENEZUELA("VE"),
        SOUTH_AFRICA("ZA"),
        ZIMBABWE("ZW");

        private String country;

        Country(String country) {
            this.country = country;
        }

        public String code() {
            return this.country;
        }
    }

    /**
     * Enumeration for allowed values for the query parameter {@link APIParameter#FORMAT}
     */
    public enum Format {
        STRING("string"),
        CSV("csv"),
        JSON("json"),
        PHP("php"),
        TSV("tsv"),
        YAML("yaml"),
        XML("xml");

        private String value;

        Format(String value) {
            this.value = value;
        }

        public String format() {
            return this.value;
        }

    }
}
