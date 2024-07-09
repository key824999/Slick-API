package toy.slick.common;

import lombok.Getter;

import java.time.format.DateTimeFormatter;

public interface Const {

    @Getter
    enum DateTimeFormat {
        yyyyMMdd    ("yyyyMMdd",    DateTimeFormatter.ofPattern("yyyyMMdd")),
        yyyyMMddHH  ("yyyyMMddHH",  DateTimeFormatter.ofPattern("yyyyMMddHH"));

        private final String format;
        private final DateTimeFormatter dateTimeFormatter;

        DateTimeFormat(String format, DateTimeFormatter dateTimeFormatter) {
            this.format = format;
            this.dateTimeFormatter = dateTimeFormatter;
        }
    }

    interface ZoneId {
        String NEW_YORK = "America/New_York";
        String SEOUL    = "Asia/Seoul";
        String UTC      = "UTC";
    }
}
