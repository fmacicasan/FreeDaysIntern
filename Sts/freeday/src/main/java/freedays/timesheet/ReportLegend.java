package freedays.timesheet;

public enum ReportLegend {
    
    LEGAL("Co","concediu odihna"),
    SPECIAL ("Cs", "concediu special"),
    NO_PAY ("Cfs", "concediu fara salar"),
    ILL("Bo","concediu de boala"),
    MEDICAL("M", "concediu medical");
    
    private String term;
    private String detail;
    
    private ReportLegend(String term, String detail){
        this.term = term;
        this.detail = detail;
    }

    public String getTerm() {
        return term;
    }

    public String getDetail() {
        return detail;
    }
    
    public String getReportRepresentaiton(){
        return term+" - "+detail;
    }
    
    
    

}
