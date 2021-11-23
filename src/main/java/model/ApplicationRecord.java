package model;


public class ApplicationRecord {

    private final String officialName;
    private String information;

    public ApplicationRecord(String officialName) {
        this.officialName = officialName;
    }

    public ApplicationRecord(String officialName,String information) {
       this(officialName);
       setInformation(information);
    }

    public String getOfficialName() {
        return officialName;
    }

    public String getInformation() {
        return information;
    }
    /**
     * Set the information of the specific application.
     *
     * @param information is information about the application that will be set.
     */
    public void setInformation(String information) {
        this.information = information;
    }
}
