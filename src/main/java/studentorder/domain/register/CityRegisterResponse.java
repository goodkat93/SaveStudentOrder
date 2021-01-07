package studentorder.domain.register;

public class CityRegisterResponse {
   private boolean existing;
   private boolean temporal; // класс-обертка, может быть равна null

    public boolean isExisting() {
        return existing;
    }

    public void setExisting(boolean existing) {
        this.existing = existing;
    }

    public Boolean getTemporal() {
        return temporal;
    }

    public void setTemporal(boolean temporal) {
        this.temporal = temporal;
    }

    @Override
    public String toString() {
        return "CityRegisterResponse{" +
                "existing=" + existing +
                ", temporal=" + temporal +
                '}';
    }
}
