/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.dto;

public class ResponseDTO {

    private String status;
    private String mes;
    private Object data;
    private Object list_data;
    private String trans_page;
    
    
    // Constructor
    public ResponseDTO(String status, String mes, Object data, Object list_data, String trans_page) {
        this.status = status;
        this.mes = mes;
        this.data = data;
        this.list_data = list_data;
        this.trans_page = trans_page;
    }

    // Getters and Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getList_data() {
        return list_data;
    }

    public void setList_data(Object list_data) {
        this.list_data = list_data;
    }

    public String getTrans_page() {
        return trans_page;
    }

    public void setTrans_page(String trans_page) {
        this.trans_page = trans_page;
    }
}
