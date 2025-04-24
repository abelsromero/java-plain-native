package org.abelsromero.demo.sb.args;

public enum OutputFormat {

    TEXT, JSON;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
