package com.ArsenyVekshin.table;

import java.io.Serializable;
import java.util.ArrayList;

public class Table implements Serializable {
    private ArrayList<TableRow> tableRows;

    public Table() {
        tableRows = new ArrayList<>();
    }

    public ArrayList<TableRow> getTableRows() {
        return tableRows;
    }
}
