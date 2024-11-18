

public class WDaysSkiPassA implements SkiPass  {
    String id;
    String type;
    String subType = "a";
    Boolean status;


    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public boolean getStatus() {
        return false;
    }

    @Override
    public void setStatus(boolean status) {

    }
}
