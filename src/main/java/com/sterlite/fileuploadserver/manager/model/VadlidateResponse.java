package com.sterlite.fileuploadserver.manager.model;

public class VadlidateResponse {
    private Integer validateCode;

    public Integer getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(Integer validateCode) {
        this.validateCode = validateCode;
    }

    public String getValidateMassage() {
        return validateMassage;
    }

    public void setValidateMassage(String validateMassage) {
        this.validateMassage = validateMassage;
    }

    public VadlidateResponse() {
    }

    public VadlidateResponse(Integer validateCode, String validateMassage) {
        this.validateCode = validateCode;
        this.validateMassage = validateMassage;
    }

    private String validateMassage;
}
