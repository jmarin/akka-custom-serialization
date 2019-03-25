package property.based.testing;

public class Phone {
    private final String countryCode;

    private final int countryPhoneCode;

    private final int number;

    public Phone(String countryCode, int countryPhoneCode, int number) {
        this.countryCode = countryCode;
        this.countryPhoneCode = countryPhoneCode;
        this.number = number;
    }


    public int getNumber() {
        return countryPhoneCode + number;
    }

}
