package user;

public class UserGenerator {
    public static User generatorCoOne() {
        return new User("Lesha87654321@mail.ru", "1234", "Lesha");
    }

    public static User generatorCoTwo() {
        return new User("", "1234", "Lesha");
    }

    public static User generatorCoThree() {
        return new User("fjslsfls", "etwtopwtlw", "");
    }
}