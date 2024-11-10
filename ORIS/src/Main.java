import java.sql.*;
import java.util.Scanner;

public class Main {

    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "rootwert";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/ORIS";

    public static void main(String[] args) throws Exception {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("select * from driver");

        while (result.next()) {
            System.out.println(result.getInt("id") + " " + result.getString("first_name"));
        }
        String sqlInsertUser = "insert into driver(first_name, last_name, age)" +
                "values (?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertUser);

        int totalInserted = 0;
        for (int i = 1; i<=6; i++) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите имя");
            String firstName = scanner.nextLine();
            System.out.println("Введите фамилию");
            String secondName = scanner.nextLine();
            System.out.println("Введите возраст");
            Integer age = scanner.nextInt();

            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, secondName);
            preparedStatement.setInt(3, age);

            int affectedRows = preparedStatement.executeUpdate();
            totalInserted += affectedRows;
        }
        System.out.println("Было добавлено " + totalInserted + " строк");

        System.out.println("Водители старше 18 лет и с фамилией, начинающейся на согласную:");
        String sqlSelectDrivers = "SELECT * FROM driver WHERE age >= 18 AND (last_name ILIKE 'b%' OR last_name ILIKE 'c%' OR last_name ILIKE 'd%' OR last_name ILIKE 'f%' OR last_name ILIKE 'g%' OR last_name ILIKE 'h%' OR last_name ILIKE 'j%' " +
                "OR last_name ILIKE 'k%' OR last_name ILIKE 'l%' OR last_name ILIKE 'm%' OR last_name ILIKE 'n%' OR last_name ILIKE 'p%' OR last_name ILIKE 'q%' OR last_name ILIKE 'r%' OR last_name ILIKE 's%' " +
                "OR last_name ILIKE 't%' OR last_name ILIKE 'v%' OR last_name ILIKE 'w%' OR last_name ILIKE 'x%' OR last_name ILIKE 'y%' OR last_name ILIKE 'z%')";

        ResultSet filteredResult = statement.executeQuery(sqlSelectDrivers);
        while (filteredResult.next()) {
            System.out.println(filteredResult.getInt("id") + " " + filteredResult.getString("first_name") + " " + filteredResult.getString("last_name") + " " + filteredResult.getInt("age"));
        }
    }
}