package dto.request;

public class CreateCourier extends CourierBase{

    private String firstName;

    public CreateCourier(String firstName) {
    }

    public CreateCourier(String login, String password, String firstName) {
        super(login, password);
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
