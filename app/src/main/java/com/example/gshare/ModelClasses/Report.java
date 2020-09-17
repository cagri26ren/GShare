package com.example.gshare.ModelClasses;

public class Report {

    private String reported;
    private String report;
    private String chatNumber;
    private String reporter;
    private String reportId;

    public Report( String report , String chatNumber , String reported , String reporter , String reportId ){
        this.report = report;
        this.reported = reported;
        this.reporter = reporter;
        this.chatNumber = chatNumber;
        this.reportId = reportId;
    }
    public Report(){}

    public String getReported() {
        return reported;
    }

    public void setReported(String reported) {
        this.reported = reported;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getChatNumber() {
        return chatNumber;
    }

    public void setChatNumber(String chatNumber) {
        this.chatNumber = chatNumber;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }
}
