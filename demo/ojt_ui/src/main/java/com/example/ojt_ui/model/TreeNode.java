package com.example.ojt_ui.model;

import java.util.List;

/**
 * S02 분류 트리의 노드. kind에 따라 사용되는 필드가 다르다(mockup_ui/s02-data.js의 S02_TREE 객체 형태를 그대로 반영):
 * - "group": icon, children 사용
 * - "type": parent, instanceCount, fields, submodels 사용
 */
public class TreeNode {

    private final String key;
    private final String label;
    private final String kind;
    private final String icon;
    private final List<String> children;
    private final String parent;
    private final Integer instanceCount;
    private final List<FieldGroup> fields;
    private final List<String> submodels;

    public TreeNode(String key, String label, String kind, String icon, List<String> children,
                     String parent, Integer instanceCount, List<FieldGroup> fields, List<String> submodels) {
        this.key = key;
        this.label = label;
        this.kind = kind;
        this.icon = icon;
        this.children = children;
        this.parent = parent;
        this.instanceCount = instanceCount;
        this.fields = fields;
        this.submodels = submodels;
    }

    public static TreeNode group(String key, String label, String icon, List<String> children) {
        return new TreeNode(key, label, "group", icon, children, null, null, null, null);
    }

    public static TreeNode type(String key, String label, String parent, int instanceCount,
                                 List<FieldGroup> fields, List<String> submodels) {
        return new TreeNode(key, label, "type", null, null, parent, instanceCount, fields, submodels);
    }

    public String getKey() {
        return key;
    }

    public String getLabel() {
        return label;
    }

    public String getKind() {
        return kind;
    }

    public String getIcon() {
        return icon;
    }

    public List<String> getChildren() {
        return children;
    }

    public String getParent() {
        return parent;
    }

    public Integer getInstanceCount() {
        return instanceCount;
    }

    public List<FieldGroup> getFields() {
        return fields;
    }

    public List<String> getSubmodels() {
        return submodels;
    }

    public boolean isGroup() {
        return "group".equals(kind);
    }

    public boolean isType() {
        return "type".equals(kind);
    }

    public String getShortLabel() {
        return label.replaceAll("\\s*\\(.*\\)$", "");
    }
}
