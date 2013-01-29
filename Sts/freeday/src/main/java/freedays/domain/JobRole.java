package freedays.domain;

public enum JobRole {
        DEV("Developer"), 
        QA("Tester"), 
        SDET("Software Development Engineer in Test"), 
        PM("Product Manager"), 
        IT("IT Engineer"), 
        EM("Engineering Manager"), 
        PO("Product Owner"), 
        SE("Support Engineer"), 
        PS("Professional Services"), 
        OBS("Observer"), 
        DEV_JR("Junior Developer"), 
        DEV_SR("Senior Developer"), 
        QA_JR("Junior Tester"), 
        QA_SR("Senior Tester"), 
        UXD("UX Designer"), 
        TJT("Temporary Job Title"),
        DEV_T("Trainee Developer"),
        QA_T("Trainee Tester"),
        TW("Technical Writer"), 
        SC("Site Controller"), 
        RM("Research and Marketing Associate"), 
        QCL("QC Linguist"), 
        PC("Project Co-ordinator"),
        PSE("Pre-Sales Engineer"),
        MISR("Marketing Inside Sales Representative"), 
        HRM("HR Manager"),
        HRBP("HR Business Partner"),
        GD("Graphic Designer"),
        GM("General Manager"),
        FAC("Facilities and ADmin Co-ordinator"),
        ELM("Engineer Linguist Manager"),
        EL("Engineer Linguist"),
        DCC("Data Collection Co-ordinator"),
        DA("Data Analyst"),
        C("Collection"),
        CSM("Client Service Manager"),
        ACC_T("Assistant Accountant"),
        ADM_T("Admin Assistant");
        
        private String description;
        
        
        private JobRole(String description){
            this.description = description;
        }
        
        public String getDescription(){
            return this.description;
        }
}
