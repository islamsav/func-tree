public enum ArrowFormat {
    DEFAULT("-->"), // Короткие тонкие
    DEFAULT_LONG("--->"), // Тонкие чуть длиннее
    BOLD("==>"), // Которкие жирные
    BOLD_LONG("===>"); // Жирные длиннее жирные

    ArrowFormat(String format) {
        this.format = format;
    }

    private String format;

    public String getFormat() {
        return this.format;
    }
}
