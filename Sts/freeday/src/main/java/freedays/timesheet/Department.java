package freedays.timesheet;

public enum Department {

    SOFTWARE("Software Development"), 
    ADMIN("Administration"), 
    DATA("Data Acquisition and Management"),
    DIRECTORS("Directors"),
    PRE_SALES("Enterprise Pre-Sales"),
    FINANCE("Finance"),
    IT("IT"),
    MARKETING("Marketing"),
    PROFESSIONAL_SERVICES("Professional Services"),
    QA("QA (Development)"),
    SUPPORT("Support and Maintenance");
    
    private String description;
    
    
    private Department(String description){
        this.description = description;
    }
    
    public String getDescription(){
        return this.description;
    }
}
