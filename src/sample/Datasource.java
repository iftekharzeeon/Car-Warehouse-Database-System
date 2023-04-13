package sample;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Datasource {
    public static final String DB_NAME = "carShowroom.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:F:\\Varsity\\Courses\\CSE_Programming Language\\1-2_107\\Project\\Car Showroom\\" + DB_NAME;

    private static final String USER_QUERY_VIEWER = "SELECT * FROM user WHERE user_name = ?;";
    private static final String CAR_QUERY = "SELECT * FROM cars;";
    private static final String QUANTITY_QUERY = "SELECT quantity FROM cars WHERE reg_num = ?;";
    private static final String DELETE_QUERY = "DELETE FROM cars WHERE reg_num = ?;";
    private static final String UPDATE_QUANTITY = "UPDATE cars SET quantity = ? WHERE reg_num = ?;";
    private static final String REG_QUERY = "SELECT * FROM cars WHERE reg_num LIKE ?;";
    private static final String MAKER_QUERY = "SELECT * FROM cars WHERE car_maker LIKE ?";
    private static final String MODEL_QUERY = " AND car_model LIKE ?";

    private static final String USER_QUERY_ADMIN = "SELECT * FROM admins WHERE admin_name = ? AND password = ?;";
    private static final String INSERT_QUERY = "INSERT INTO cars (reg_num, year_made, color1, color2, color3, car_maker, car_model, price, quantity) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String UPDATE_REG_QUERY = "UPDATE cars SET reg_num = ? WHERE id = ?;";
    private static final String UPDATE_MAKER_QUERY = "UPDATE cars SET car_maker = ? WHERE id = ?;";
    private static final String UPDATE_MODEL_QUERY = "UPDATE cars SET car_model = ? WHERE id = ?;";
    private static final String UPDATE_PRICE_QUERY = "UPDATE cars SET price = ? WHERE id = ?;";
    private static final String UPDATE_QUANTITY_QUERY = "UPDATE cars SET quantity = ? WHERE id = ?;";
    private static final String UPDATE_YEAR_QUERY = "UPDATE cars SET year_made = ? WHERE id = ?;";
    private static final String UPDATE_COLOR1_QUERY = "UPDATE cars SET color1 = ? WHERE id = ?;";
    private static final String UPDATE_COLOR2_QUERY = "UPDATE cars SET color2 = ? WHERE id = ?;";
    private static final String UPDATE_COLOR3_QUERY = "UPDATE cars SET color3 = ? WHERE id = ?;";

    private static Connection connection;
    private static PreparedStatement userFindQuery;
    private static PreparedStatement showCarQuery;
    private static PreparedStatement getQuantityQuery;
    private static PreparedStatement deleteQuery;
    private static PreparedStatement updateQuantityQueryWithReg;
    private static PreparedStatement searchRegQuery;
    private static PreparedStatement searchMakerQuery;

    private static PreparedStatement adminFindQuery;
    private static PreparedStatement insertCarQuery;
    private static PreparedStatement updateRegQuery;
    private static PreparedStatement updateMakerQuery;
    private static PreparedStatement updateModelQuery;
    private static PreparedStatement updatePriceQuery;
    private static PreparedStatement updateQuantityQuery;
    private static PreparedStatement updateYearQuery;
    private static PreparedStatement updateColor1Query;
    private static PreparedStatement updateColor2Query;
    private static PreparedStatement updateColor3Query;

    private static final Datasource instance = new Datasource();

    private Datasource(){
    }

    public static Datasource getInstance() {
        return instance;
    }

    public boolean open() throws SQLException {

        connection = DriverManager.getConnection(CONNECTION_STRING);
        userFindQuery = connection.prepareStatement(USER_QUERY_VIEWER);
        showCarQuery = connection.prepareStatement(CAR_QUERY);
        getQuantityQuery = connection.prepareStatement(QUANTITY_QUERY);
        deleteQuery = connection.prepareStatement(DELETE_QUERY);
        updateQuantityQueryWithReg = connection.prepareStatement(UPDATE_QUANTITY);
        searchRegQuery = connection.prepareStatement(REG_QUERY);

        adminFindQuery = connection.prepareStatement(USER_QUERY_ADMIN);
        insertCarQuery = connection.prepareStatement(INSERT_QUERY);
        updateRegQuery = connection.prepareStatement(UPDATE_REG_QUERY);
        updateMakerQuery = connection.prepareStatement(UPDATE_MAKER_QUERY);
        updateModelQuery = connection.prepareStatement(UPDATE_MODEL_QUERY);
        updatePriceQuery = connection.prepareStatement(UPDATE_PRICE_QUERY);
        updateQuantityQuery = connection.prepareStatement(UPDATE_QUANTITY_QUERY);
        updateYearQuery = connection.prepareStatement(UPDATE_YEAR_QUERY);
        updateColor1Query = connection.prepareStatement(UPDATE_COLOR1_QUERY);
        updateColor2Query = connection.prepareStatement(UPDATE_COLOR2_QUERY);
        updateColor3Query = connection.prepareStatement(UPDATE_COLOR3_QUERY);

        return true;
    }
    public void close() throws SQLException {
        connection.close();
    }
    public boolean userSearch(String name) throws SQLException {
        userFindQuery.setString(1, name);
        ResultSet resultSet = userFindQuery.executeQuery();
        return resultSet.next();
    }

    public boolean adminSearch(String user, String password) throws SQLException {
        adminFindQuery.setString(1, user);
        adminFindQuery.setString(2, password);
        ResultSet resultSet = adminFindQuery.executeQuery();
        return resultSet.next();
    }

    public List<Cars> showCars() throws SQLException {
        ResultSet resultSet = showCarQuery.executeQuery();

        List<Cars> carsList = new ArrayList<>();
        while (resultSet.next()) {
            System.out.println("what");
            Cars car = new Cars();
            car.setId(resultSet.getInt(1));
            car.setReg(resultSet.getString(2));
            car.setYear(resultSet.getInt(3));
            if (resultSet.getString(4) == null) {
                car.setColor1("---");
            } else {
                car.setColor1(resultSet.getString(4));
            }if (resultSet.getString(5) == null) {
                car.setColor2("---");
            } else {
                car.setColor2(resultSet.getString(5));
            }if (resultSet.getString(6) == null) {
                car.setColor3("---");
            } else {
                car.setColor3(resultSet.getString(6));
            }
            car.setMaker(resultSet.getString(7));
            car.setModel(resultSet.getString(8));
            car.setPrice(resultSet.getInt(9));
            car.setQuantity(resultSet.getInt(10));

            carsList.add(car);

        }
        return carsList;

    }
    public boolean buyCar(String reg) {
        try {
            String temp = "SELECT count(*) FROM cars WHERE reg_num = ?;";
            PreparedStatement tempSQL = connection.prepareStatement(temp);
            tempSQL.setString(1, reg);
            if (!tempSQL.execute()) {
                System.out.println("WTH");
                //System.out.println(tempSet.getString(1));
                return false;
            }
            getQuantityQuery.setString(1, reg);
            ResultSet resultSet = getQuantityQuery.executeQuery();
            int quantity = resultSet.getInt(1);
            quantity = quantity - 1;
            if (quantity <= 0) {
                deleteQuery.setString(1, reg);
                if (deleteQuery.executeUpdate() == 1) {
                    System.out.println("Car deleted successfully");
                    return true;
                }
            }
            updateQuantityQueryWithReg.setInt(1, quantity);
            updateQuantityQueryWithReg.setString(2, reg);

            return updateQuantityQueryWithReg.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Cars> regSearch(String reg){
        try {
            searchRegQuery.setString(1, reg);
            ResultSet resultSet = searchRegQuery.executeQuery();

            List<Cars> carsList = new ArrayList<>();
            if (resultSet == null) {
                return null;
            }
            while (resultSet.next()) {
                Cars car = new Cars();
                car.setId(resultSet.getInt(1));
                car.setReg(resultSet.getString(2));
                car.setYear(resultSet.getInt(3));
                if (resultSet.getString(4) == null) {
                    car.setColor1("---");
                } else {
                    car.setColor1(resultSet.getString(4));
                }if (resultSet.getString(5) == null) {
                    car.setColor2("---");
                } else {
                    car.setColor2(resultSet.getString(5));
                }if (resultSet.getString(6) == null) {
                    car.setColor3("---");
                } else {
                    car.setColor3(resultSet.getString(6));
                }
                car.setMaker(resultSet.getString(7));
                car.setModel(resultSet.getString(8));
                car.setPrice(resultSet.getInt(9));
                car.setQuantity(resultSet.getInt(10));

                carsList.add(car);
            }
            return carsList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Cars> makerSearch(String maker, String model) throws SQLException {
        try {
            StringBuilder sb = new StringBuilder(MAKER_QUERY);
            if (model.equalsIgnoreCase("any")) {
                searchMakerQuery = connection.prepareStatement(MAKER_QUERY);
                searchMakerQuery.setString(1, maker);
            } else {
                sb.append(MODEL_QUERY);
                searchMakerQuery = connection.prepareStatement(sb.toString());
                searchMakerQuery.setString(1, maker);
                searchMakerQuery.setString(2, model);
            }
            ResultSet resultSet = searchMakerQuery.executeQuery();
            if (resultSet == null) {
                return null;
            }
            List<Cars> carsList = new ArrayList<>();
            while (resultSet.next()) {
                Cars car = new Cars();
                car.setId(resultSet.getInt(1));
                car.setReg(resultSet.getString(2));
                car.setYear(resultSet.getInt(3));
                if (resultSet.getString(4) == null) {
                    car.setColor1("---");
                } else {
                    car.setColor1(resultSet.getString(4));
                }if (resultSet.getString(5) == null) {
                    car.setColor2("---");
                } else {
                    car.setColor2(resultSet.getString(5));
                }if (resultSet.getString(6) == null) {
                    car.setColor3("---");
                } else {
                    car.setColor3(resultSet.getString(6));
                }
                car.setMaker(resultSet.getString(7));
                car.setModel(resultSet.getString(8));
                car.setPrice(resultSet.getInt(9));
                car.setQuantity(resultSet.getInt(10));

                carsList.add(car);
            }
            return carsList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public boolean addCar(String reg_num, int year_made, String color1, String color2, String color3, String car_maker, String car_model, int price, int quantity) throws SQLException {
        try {
            insertCarQuery.setString(1, reg_num);
            insertCarQuery.setInt(2, year_made);
            if (color1.equals("")) {
                insertCarQuery.setString(3, null);
            } else {
                insertCarQuery.setString(3, color1);
            }if (color2.equals("")) {
                insertCarQuery.setString(4, null);
            } else {
                insertCarQuery.setString(4, color2);
            }if (color3.equals("")) {
                insertCarQuery.setString(5, null);
            } else {
                insertCarQuery.setString(5, color3);
            }
            insertCarQuery.setString(6, car_maker);
            insertCarQuery.setString(7, car_model);
            insertCarQuery.setInt(8, price);
            insertCarQuery.setInt(9, quantity);

            return insertCarQuery.executeUpdate() == 1;
        } catch (Exception e) {
            return false;
        }
    }
    public boolean updateReg(int id, String reg) throws SQLException {
        try {
            updateRegQuery.setString(1, reg);
            updateRegQuery.setInt(2, id);

            return updateRegQuery.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateMaker(int id, String maker) throws SQLException {
        try {
            updateMakerQuery.setString(1, maker);
            updateMakerQuery.setInt(2, id);

            return updateMakerQuery.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateModel(int id, String model) throws SQLException {
        updateModelQuery.setString(1, model);
        updateModelQuery.setInt(2, id);

        return updateModelQuery.executeUpdate() == 1;
    }
    public boolean updatePrice(int id, int price) throws SQLException {
        updatePriceQuery.setInt(1, price);
        updatePriceQuery.setInt(2, id);

        return updatePriceQuery.executeUpdate() == 1;
    }

    public boolean updateQuantity(int id, int quantty) throws SQLException {
        updateQuantityQuery.setInt(1, quantty);
        updateQuantityQuery.setInt(2, id);

        return updateQuantityQuery.executeUpdate() == 1;
    }

    public boolean updateYear(int id, int year) throws SQLException {
        updateYearQuery.setInt(1, year);
        updateYearQuery.setInt(2, id);

        return updateYearQuery.executeUpdate() == 1;
    }

    public boolean updateColor1(int id, String color1) throws SQLException {
        updateColor1Query.setString(1, color1);
        updateColor1Query.setInt(2, id);

        return updateColor1Query.executeUpdate() == 1;
    }

    public boolean updateColor2(int id, String color2) throws SQLException {
        updateColor2Query.setString(1, color2);
        updateColor2Query.setInt(2, id);

        return updateColor2Query.executeUpdate() == 1;
    }

    public boolean updateColor3(int id, String color3) throws SQLException {
        updateColor3Query.setString(1, color3);
        updateColor3Query.setInt(2, id);

        return updateColor3Query.executeUpdate() == 1;
    }

    public boolean deleteCar(String reg) throws SQLException {
        deleteQuery.setString(1, reg);
        return deleteQuery.executeUpdate() == 1;
    }
}