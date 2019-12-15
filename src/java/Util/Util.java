package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

public class Util {
    
    private static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static String user = "sa";
    private static String password = "123";
    private static String host = "192.168.0.17";
    private static String database = "salud_mock";
    private static String uri = "none";
    private static Connection connectionUtil;
    private static Connection connectionUtilCubo;
    private static String PERSISTENCE_UNINAME = "Kansas2PU";
    private static String tipoClientesPresupuestoPublico = "1,4,6,9,10,11,12,13,14,15,16,17,18";
    private static DataSource source;
    
    public static EntityManager createEntityManager(EntityManager em){
        if (em == null) {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNINAME);
            em = factory.createEntityManager();
        }
        return em;
    }
    
    public static Connection openConnection(Connection connection) throws SQLException {

        if (connection != null) {
            if (connection.isClosed()) {
                connection = getConnection();
            }
        }else{
            connection = getConnection();
        }
        System.out.println("--------------CONNECTION SERVER-------------:" + connection);
        return connection;
    }
    
    public static void openConnection() throws SQLException {
        if (connectionUtil != null) {
            if (connectionUtil.isClosed()) {
                connectionUtil = getConnection();
            }
        } else {
            connectionUtil = getConnection();
        }
    }
    
    public static ResultSet executeQueryStatement(String sql) throws SQLException {
        Statement st = connectionUtil.createStatement();
        return st.executeQuery(sql);
    }
    
    public static int executeUpdateStatement(String sql) throws SQLException { 
        Statement st = connectionUtil.createStatement();
        return st.executeUpdate(sql);
    }
    
    private static Connection getConnection(){
        try{
            String url = "";
            if (uri.equals("none")){
                url = "jdbc:sqlserver://" + host + ";user=" + user + ";password=" + password + ";databaseName=" + database;
                Class.forName(driver);
                connectionUtil = DriverManager.getConnection(url, user, password);
            }else{
                if (source == null) {
                    Context initCtx = new InitialContext();
                    Context envCtx = (Context)initCtx.lookup("java:comp/env");
                    source = (DataSource)envCtx.lookup("jdbc/zeus");
                }
                connectionUtil = source.getConnection();
            }
            System.out.println("----------------CONNECTION SQL SERVER-------------:" + connectionUtil);
            Statement stCon = connectionUtil.createStatement(1004, 1007);
            stCon.executeUpdate("set DATEFORMAT ymd");
            stCon.close();
        }catch (NamingException e1){
            e1.printStackTrace();
        }catch (ClassNotFoundException e2) {
            e2.printStackTrace();
        }catch (SQLException e3) {
            e3.printStackTrace();
        }
        return connectionUtil;
    }
    
    /*public static Object getSessionBean(String namebean){
        Map map = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        return map.get(namebean);
    }
    
    public static Object getSession(String nameobjectsession){
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return request.getSession().getAttribute(nameobjectsession);
    }
    
    public static String getParameter(String name){
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return request.getParameter(name);
    }*/
    
    public static java.sql.Date converterStringDate(String fecha) { System.out.println("converterStringDate:" + fecha);
        String[] values = fecha.split("/");
        String format;
        if (values.length == 1){
            format = fecha;
        }else if (values[2] != null) {
            format = values[2] + "-" + values[1] + "-" + values[0];
        }else {
            format = fecha;
        }
        java.sql.Date date = java.sql.Date.valueOf(format);
        return date;
    }
    
    public static java.sql.Date converterStringDate2(String fecha) {
        java.sql.Date date = java.sql.Date.valueOf(fecha);
        return date;
    }
    
    public static long verificarNit(String nit){
        nit = nit == null ? "0" : nit;
        long nitCliente;
        try {
            nitCliente = Long.parseLong(nit);
        }catch (NumberFormatException e) {
            char[] data = nit.toCharArray();
            String nitR = "";
            for (char value : data) {
                try {
                  Integer.parseInt(String.valueOf(value));
                  nitR = nitR + value;
                }catch (NumberFormatException ex) {
                }
            }
            nitR = nitR.equals("") ? "0" : nitR;
            nitCliente = Long.parseLong(nitR);
        }
        return nitCliente;
    }
    
    /*public static List<MonthInterval> getMonths(String start, String end){
        List<MonthInterval> result = new ArrayList();
        String[] values = start.split("-");
        String[] values2 = end.split("-");
        int yearStart = Integer.parseInt(values[0]);
        int monthStart = Integer.parseInt(values[1]);
        int dayStart = 0;
        int yearEnd = Integer.parseInt(values2[0]);
        int monthEnd = Integer.parseInt(values2[1]);
        int dayEnd = 0;
        int yearGenerate = yearEnd - yearStart + 1;
        Calendar calendar = Calendar.getInstance();
        for (int i = 1; i <= yearGenerate; i++) {
            while (monthStart <= 12) {
                calendar.set(yearStart, monthStart, dayStart);
                int DAY_OF_MONTH = calendar.getActualMaximum(5);
                String fechaString1 = "";
                String fechaString2 = "";
                if (monthStart < 10) {
                    fechaString1 = yearStart + "-0" + monthStart + "-01";
                    fechaString2 = yearStart + "-0" + monthStart + "-" + DAY_OF_MONTH;
                } else {
                    fechaString1 = yearStart + "-" + monthStart + "-01";
                    fechaString2 = yearStart + "-" + monthStart + "-" + DAY_OF_MONTH;
                }
                MonthInterval interval = new MonthInterval();
                interval.setStartDate(fechaString1);
                interval.setEndDate(fechaString2);
                result.add(interval);
                monthStart++;
                if ((monthStart == monthEnd + 1) && (yearEnd == yearStart)) 
                    break;
            }
            monthStart = 1;
            yearStart++;
        }
        return result;
    }
    
    public static List<MonthInterval> getMonths(String start){
        List<MonthInterval> result = new ArrayList();
        String[] values = start.split("-");
        int year = Integer.parseInt(values[0]);
        int month = Integer.parseInt(values[1]);
        int date = Integer.parseInt(values[2]);
        int yearStart = year - 1;
        int monthStart = month;
        int dateStart = 1;
        Calendar calendar = Calendar.getInstance();
        int monthCount = 0;
        while (monthCount < 12) {
            monthCount++;
            monthStart++;
            calendar.set(yearStart, monthStart, 0, 0, 0, 0);
            int dayFinal = calendar.getActualMaximum(5);
            MonthInterval m = new MonthInterval();
            String dateBegin = String.valueOf(yearStart) + "-" + monthStart + "-1";
            String dateEnd = String.valueOf(yearStart) + "-" + monthStart + "-" + dayFinal;
            m.setStartDate(dateBegin);
            m.setEndDate(dateEnd);
            result.add(m);
            if (monthStart == 12) {
                yearStart++;
                monthStart = 0;
            }
        }
        return result;
    }*/
    
    public static java.util.Date converterStringDate(java.util.Date date) {
        return null;
    }
}    
