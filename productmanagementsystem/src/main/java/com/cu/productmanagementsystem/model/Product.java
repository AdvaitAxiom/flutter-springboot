package com.cu.productmanagementsystem.model;

public class Product {
    private String pid;
    private String pname;
    private String pcategory;

    public Product() {}

    public Product(String pid, String pname, String pcategory) {
        this.pid = pid;
        this.pname = pname;
        this.pcategory = pcategory;
    }

    public String getPid() { return pid; }
    public void setPid(String pid) { this.pid = pid; }

    public String getPname() { return pname; }
    public void setPname(String pname) { this.pname = pname; }

    public String getPcategory() { return pcategory; }
    public void setPcategory(String pcategory) { this.pcategory = pcategory; }
}
