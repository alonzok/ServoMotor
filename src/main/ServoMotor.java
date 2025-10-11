// Clase principal del programa.
package main;

// Se importan las librerías para la conexión de la base de datos SQL.
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// Se importan las librerías para el manejo del servo motor de marca Phidget.
import com.phidget22.PhidgetException;
import com.phidget22.RCServo;

/**
 * Clase principal del programa para controlar el servo motor. 
 */
public class ServoMotor implements Runnable {

	// Se crea la variable para la conexión de la base de datos.
	private Connection conn;
	
	public static void main(String args[]) {
		// Se inicializa la variable de sensar el servo motor.
		ServoMotor sensor = new ServoMotor();
		
		// Se crea un hilo para mantener la lectura del servo motor mientras configura el servo motor.
		Thread sensorThread = new Thread(sensor);
		sensorThread.start();
	}

	@Override
	public void run() {

		// Se crea la conexión de la base de datos utilizando la liga de la base de datos, usuario y contraseña.
		try {
			String url = "jdbc:mysql://localhost:3306/sensor";
			conn = DriverManager.getConnection(url, "root", "secret");
			
			// En caso de tener una excepción, imprime en consola el error.
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		// Mantiene en un ciclo para controlar el estado del servo motor.
		while (true) {
			try {

				// Se incializa el servo motor en el puerto 5000.
				RCServo rcServo0 = new RCServo();
				rcServo0.open(5000);
				// Se visualiza si el servo se debe de mover, en caso de si, mover.
				try {
					// Se crea el objeto statement para realizar la petición de SQL.
					Statement stmt = conn.createStatement();
					ResultSet rs;
					String item = "1";

					// Se agrega la sentencia a ejecutar la petición.
					rs = stmt.executeQuery("SELECT active FROM ServoMotor;");
					while (rs.next()) {

						// Se ejecuta y se obtiene el estado del servo motor
						// de forma de string.
						item = rs.getString("active");
					}

					// Compara el estado del servo motor, en caso de ser activo, se le agrega
					// 3 puntos al posición del servo motor.
					if (item.compareTo("1") == 0) {
						rcServo0.setTargetPosition(rcServo0.getPosition() + 3);
						rcServo0.setEngaged(true);
						
					// En caso de ser inactivo, se apaga el servo motor
					} else {
						rcServo0.setEngaged(false);
					}

				// En caso de tener una excepción, imprime en consola el error.
				} catch (Exception ex) {
					System.err.println(ex);
				}

				// Se cierra la comunicación con el servo motor.
				rcServo0.close();

			// En caso de tener una excepción, imprime en consola el error.
			} catch (PhidgetException e) {
				e.printStackTrace();
			}
		}
	}

}
