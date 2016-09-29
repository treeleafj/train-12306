package org.leaf.train.repository.model;

import java.util.Map;

/**
 * @author leaf
 * @date 2016-09-27 15:16
 */
public class TrainResponse<T> {

    private String validateMessagesShowId;

    private Boolean status;

    private Integer httpstatus;

    private Map<String, Object> data;

    public String getValidateMessagesShowId() {
        return validateMessagesShowId;
    }

    public void setValidateMessagesShowId(String validateMessagesShowId) {
        this.validateMessagesShowId = validateMessagesShowId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getHttpstatus() {
        return httpstatus;
    }

    public void setHttpstatus(Integer httpstatus) {
        this.httpstatus = httpstatus;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
