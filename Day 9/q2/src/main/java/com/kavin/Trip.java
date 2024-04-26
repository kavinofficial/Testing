package com.kavin;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.Row;

public class Trip {
    public String from, to, departDate, returnDate;
    SimpleDateFormat formatter = new SimpleDateFormat("d MMMM yyyy");

    public String formatDate(Date date) {
        return formatter.format(date);
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDepartDate() {
        return departDate;
    }

    public void setDepartDate(Date departDate) {
        this.departDate = formatDate(departDate);
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = formatDate(returnDate);
    }

    public void setValues(Row row) {
        this.from = row.getCell(0).getStringCellValue();
        this.to = row.getCell(1).getStringCellValue();
        System.out.println(from + " " + to);
        // this.departDate = formatDate(row.getCell(2).getDateCellValue());
        // this.returnDate = formatDate(row.getCell(3).getDateCellValue());
    }
}
