package utils;

public class PaymentDetails {
    private String CARD_NUMBER;
    private String MONTH;
    private String YEAR;
    private String SECURITY_CODE;
    private String EMAIL_ID;
    private String PASSWORD;
    private String PIN;
    private String VALUE;

    // Getters and setters
    public String getCARD_NUMBER() { return CARD_NUMBER; }
    public void setCARD_NUMBER(String CARD_NUMBER) { this.CARD_NUMBER = CARD_NUMBER; }

    public String getMONTH() { return MONTH; }
    public void setMONTH(String MONTH) { this.MONTH = MONTH; }

    public String getYEAR() { return YEAR; }
    public void setYEAR(String YEAR) { this.YEAR = YEAR; }

    public String getSECURITY_CODE() { return SECURITY_CODE; }
    public void setSECURITY_CODE(String SECURITY_CODE) { this.SECURITY_CODE = SECURITY_CODE; }

    public String getEMAIL_ID() { return EMAIL_ID; }
    public void setEMAIL_ID(String EMAIL_ID) { this.EMAIL_ID = EMAIL_ID; }

    public String getPASSWORD() { return PASSWORD; }
    public void setPASSWORD(String PASSWORD) { this.PASSWORD = PASSWORD; }

    public String getPIN() { return PIN; }
    public void setPIN(String PIN) { this.PIN = PIN; }

    public String getVALUE() { return VALUE; }
    public void setVALUE(String VALUE) { this.VALUE = VALUE; }

    // ✅ Validation helpers
    public boolean isValidCard() {
        return CARD_NUMBER != null && MONTH != null && YEAR != null && SECURITY_CODE != null;
    }

    public boolean isValidPaypal() {
        return EMAIL_ID != null && PASSWORD != null;
    }

    public boolean isValidGiftCard() {
        return PIN != null && VALUE != null;
    }

    // ✅ Utility methods
    public String getMaskedCardNumber() {
        if (CARD_NUMBER == null || CARD_NUMBER.length() < 8) {
            return "****";
        }
        return CARD_NUMBER.substring(0, 4) + "****" + CARD_NUMBER.substring(CARD_NUMBER.length() - 4);
    }

    public String getFormattedExpiry() {
        return (MONTH != null ? MONTH : "") + (YEAR != null ? YEAR : "");
    }

    public String getFormattedExpiryWithSlash() {
        return (MONTH != null ? MONTH : "") + "/" + (YEAR != null ? YEAR : "");
    }
}