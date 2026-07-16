package com.example.ojt_ui.model;

import java.util.List;
import java.util.stream.Collectors;

public class CodeGroup {

    private final String key;
    private final String label;
    private final boolean editable;
    private final List<String> columns;
    private final List<Row> rows;
    private final Integer fullCount;
    private final String readonlyNote;

    public CodeGroup(String key, String label, boolean editable, List<String> columns,
                      List<List<String>> rows, Integer fullCount, String readonlyNote) {
        this.key = key;
        this.label = label;
        this.editable = editable;
        this.columns = columns;
        this.rows = rows.stream().map(Row::new).collect(Collectors.toList());
        this.fullCount = fullCount;
        this.readonlyNote = readonlyNote;
    }

    public String getKey() {
        return key;
    }

    public String getLabel() {
        return label;
    }

    public boolean isEditable() {
        return editable;
    }

    public List<String> getColumns() {
        return columns;
    }

    public List<Row> getRows() {
        return rows;
    }

    public Integer getFullCount() {
        return fullCount;
    }

    public String getReadonlyNote() {
        return readonlyNote;
    }

    public int getCount() {
        return fullCount != null ? fullCount : rows.size();
    }

    public String getShortLabel() {
        return label.replaceAll("\\s*\\(.*\\)$", "");
    }

    public String getBadge() {
        if (editable) {
            return "Enum";
        }
        return label.contains("UN/LOCODE") ? "UN/LOCODE" : "ISO 3166-1";
    }

    /** 테이블 한 행. searchKey는 목업의 data-search 속성(클라이언트 필터용)에 대응한다. */
    public static class Row {
        private final List<String> cells;
        private final String searchKey;

        public Row(List<String> cells) {
            this.cells = cells;
            this.searchKey = cells.stream().collect(Collectors.joining(" ")).toLowerCase();
        }

        public List<String> getCells() {
            return cells;
        }

        public String getSearchKey() {
            return searchKey;
        }
    }
}
