package com.example.ojt_ui.model;

import java.util.List;

public class MatrixView {

    public static class Header {
        private final String line1;
        private final String line2;
        private final boolean estimated;

        public Header(String line1, String line2, boolean estimated) {
            this.line1 = line1;
            this.line2 = line2;
            this.estimated = estimated;
        }

        public String getLine1() {
            return line1;
        }

        public String getLine2() {
            return line2;
        }

        public boolean isEstimated() {
            return estimated;
        }
    }

    public static class Cell {
        private final String text;
        private final String cssClass;

        public Cell(String text, String cssClass) {
            this.text = text;
            this.cssClass = cssClass;
        }

        public String getText() {
            return text;
        }

        public String getCssClass() {
            return cssClass;
        }
    }

    public static class Row {
        private final String docName;
        private final List<Cell> cells;

        public Row(String docName, List<Cell> cells) {
            this.docName = docName;
            this.cells = cells;
        }

        public String getDocName() {
            return docName;
        }

        public List<Cell> getCells() {
            return cells;
        }
    }

    private final List<Header> headers;
    private final List<Row> rows;

    public MatrixView(List<Header> headers, List<Row> rows) {
        this.headers = headers;
        this.rows = rows;
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public List<Row> getRows() {
        return rows;
    }
}
