package com.zhaowenjie.mygp.exception;

public class WordException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private Integer code;

    public WordException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
