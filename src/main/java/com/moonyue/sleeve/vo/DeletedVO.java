package com.moonyue.sleeve.vo;

import com.moonyue.sleeve.common.util.ResponseUtil;
import com.moonyue.sleeve.core.bean.Code;
import org.springframework.http.HttpStatus;

public class DeletedVO extends UnifyResponseVO<String> {
    public DeletedVO() {
        super(Code.DELETED.getCode());
        ResponseUtil.setCurrentResponseHttpStatus(HttpStatus.CREATED.value());
    }

    public DeletedVO(int code) {
        super(code);
        ResponseUtil.setCurrentResponseHttpStatus(HttpStatus.CREATED.value());
    }

    public DeletedVO(String message) {
        super(Code.DELETED.getCode(), message);
        ResponseUtil.setCurrentResponseHttpStatus(HttpStatus.CREATED.value());
    }

    public DeletedVO(int code, String message) {
        super(code, message);
        ResponseUtil.setCurrentResponseHttpStatus(HttpStatus.CREATED.value());
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
