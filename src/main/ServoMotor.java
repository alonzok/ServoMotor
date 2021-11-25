package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.phidget22.PhidgetException;
import com.phidget22.RCServo;

public class ServoMotor implements Runnable {
	
	private Connection conn;
	
	public static void main(String args[]) {
		ServoMotor sensor = new ServoMotor();
		Thread sensorThread = new Thread(sensor);
		sensorThread.start();
	}

	@Override
	public void run() {

		try {
			String url = "jdbc:mysql://localhost:3306/sensor";
			conn = DriverManager.getConnection(url, "root", "secret");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		while (true) {
			try {
				RCServo rcServo0 = new RCServo();
				rcServo0.open(5000);
				// Se visualiza si el servo se debe de mover, en caso de si, mover.
				try {
					// Se conecta a la base de datos.

					// Se crea el objeto statement.
					Statement stmt = conn.createStatement();
					ResultSet rs;
					String item = "1";

					// Se agrega la sentencia a ejecutar al statement.
					rs = stmt.executeQuery("SELECT active FROM ServoMotor;");
					while (rs.next()) {

						// Se ejecuta y se obtiene el resultado de forma de string.
						item = rs.getString("active");
					}

					// En caso de que la bandera sea 1, se mueve el servo del valor mínimo al valor
					// máximo y del valor máximo al valor mínimo.
//					System.out.println(rcServo0.getPosition());
					if (item.compareTo("1") == 0) {
						rcServo0.setTargetPosition(rcServo0.getPosition() + 3);
						rcServo0.setEngaged(true);

					} else {
						rcServo0.setEngaged(false);
					}
				} catch (Exception ex) {
					System.err.println(ex);
				}

				rcServo0.close();
			} catch (PhidgetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
