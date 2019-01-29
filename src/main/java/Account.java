import org.apache.commons.lang3.RandomStringUtils;

public class Account {
    int id;
    String username;
    String firstName;
    String lastName;
    String email;
    String password;
    String PhoneNO;
    int userStatus;
    int sessionID;

    public Account(String Username, String Password){
        int id = Integer.parseInt(RandomStringUtils.randomAlphanumeric(10)); //assume i checked that it didnt exist
        String username = Username;
        String password = Password;
        String firstName = "";
        String lastName = "";
        String email = "";
        String PhoneNO = "";
        int userStatus = 0;
        int sessionID = 0;
    }
    public Account(int id, String Username, String firstname, String lastname, String email, String Password, String phone, int userStatus){
        this.id = id;
        this.username = Username;
        this.firstName = firstname;
        this.lastName = lastname;
        this.email = email;
        this.password = Password;
        this.PhoneNO = phone;
        this.userStatus = userStatus;
    }
    public String toString(){

        return String.format("{\"id\":%i," +
                "\"username\":\"%s\"," +
                "\"firstName\":\"%s\"," +
                "\"lastName\":\"%s\"," +
                "\"email\":\"%s\"," +
                "\"password\":\"%s\"," +
                "\"phone\":\"%s\"," +
                "\"userStatus\":%i}",id,firstName,lastName,email,password,PhoneNO, userStatus);
    }
    //{
    //  "id": 0,
    //  "username": "string",
    //  "firstName": "string",
    //  "lastName": "string",
    //  "email": "string",
    //  "password": "string",
    //  "phone": "string",
    //  "userStatus": 0
    //}
}
