package ejercicio;

import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Menu {

	private static final String URL = "jdbc:mysql://localhost:3306/Alumnos16";
	private static final String USER = "root";
	private static final String PASSWORD = "manager";

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int opcion;

		while (true) {

			System.out.println("Menú de operaciones:");
			System.out.println("1. Insertar un nuevo alumno");
			System.out.println("2. Mostrar todos los alumnos");
			System.out.println("3. Poner alumos en fichero de texto");
			System.out.println("4. Poner alumos en fichero de texto");
			System.out.println("5. Modificar el nombre de un alumno (por NIA)");
			System.out.println("6. Eliminar un alumno (por NIA)");
			System.out.println("7. Eliminar alumnos cuyo apellido contiene una palabra dada");
			System.out.println("8. Guardar alumnos en un fichero JSON");
			System.out.println("9. Leer un fichero JSON de alumnos y guardarlos en la BD");
			System.out.println("10. Salir");
			System.out.print("Seleccione una opción: ");
			opcion = scanner.nextInt();
			scanner.nextLine();

			switch (opcion) {
			case 1:
				insertarAlumnoconScanner(scanner);
				break;
			case 2:
				mostrarAlumnos();
				break;
			case 3:
				guardarAlumnosFichero();
				break;
			case 4:
				leerYGuardarAlumnos("fichero.txt");
				break;

			case 5:
				modificarNombreAlumno(scanner);
				break;
			case 6:
				eliminarAlumnoPorNIA(scanner);
				break;
			case 7:
				eliminarAlumnosPorApellido(scanner);
				break;
			case 8:
				guardarAlumnosEnXML();
				break;
			case 9:
				leerAlumnosDeXML();
				break;

			case 10:
				System.out.println("Saliendo...");
				return;
			default:
				System.out.println("Opción no válida.");
			}
		}
	}

	private static void insertarAlumnoconScanner(Scanner scanner) {
		System.out.println("Introduce los datos del alumno:");

		System.out.print("NIA (número de identificación del alumno): ");
		int nia = scanner.nextInt();
		scanner.nextLine();

		System.out.print("Nombre: ");
		String nombre = scanner.nextLine();

		System.out.print("Apellidos: ");
		String apellidos = scanner.nextLine();

		System.out.print("Género: ");
		String genero = scanner.nextLine();

		System.out.print("Fecha de nacimiento (YYYY-MM-DD): ");
		String fechaNacimiento = scanner.nextLine();

		System.out.print("Ciclo: ");
		String ciclo = scanner.nextLine();

		System.out.print("Curso: ");
		String curso = scanner.nextLine();

		System.out.print("Grupo: ");
		String grupo = scanner.nextLine();

		String sql = "INSERT INTO alumno (NIA, Nombre, Apellidos, Genero, Fecha_De_Nacimiento, Ciclo, Curso, Grupo) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setInt(1, nia);
			stmt.setString(2, nombre);
			stmt.setString(3, apellidos);
			stmt.setString(4, genero);
			stmt.setDate(5, Date.valueOf(fechaNacimiento));
			stmt.setString(6, ciclo);
			stmt.setString(7, curso);
			stmt.setString(8, grupo);

			int filasAfectadas = stmt.executeUpdate();
			if (filasAfectadas > 0) {
				System.out.println("¡Alumno insertado con éxito!");
			} else {
				System.out.println("No se pudo insertar el alumno.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void insertarAlumno(int nia, String nombre, String apellidos, String genero, String fechaNacimiento,
			String ciclo, String curso, String grupo) {
		String sql = "INSERT INTO alumno (NIA, Nombre, Apellidos, Genero, Fecha_De_Nacimiento, Ciclo, Curso, Grupo) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setInt(1, nia);
			stmt.setString(2, nombre);
			stmt.setString(3, apellidos);
			stmt.setString(4, genero);
			stmt.setDate(5, Date.valueOf(fechaNacimiento));
			stmt.setString(6, ciclo);
			stmt.setString(7, curso);
			stmt.setString(8, grupo);

			int filasAfectadas = stmt.executeUpdate();
			if (filasAfectadas > 0) {
				System.out.println("¡Alumno insertado con éxito!");
			} else {
				System.out.println("No se pudo insertar el alumno.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void mostrarAlumnos() {
		String sql = "SELECT * FROM alumno";

		try {
			Connection conection = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement stmt = conection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			System.out.println("Lista de alumnos:");
			while (rs.next()) {
				int nia = rs.getInt("NIA");
				String nombre = rs.getString("Nombre");
				String apellidos = rs.getString("Apellidos");
				String genero = rs.getString("Genero");
				Date fechaNacimiento = rs.getDate("Fecha_De_Nacimiento");
				String ciclo = rs.getString("Ciclo");
				String curso = rs.getString("Curso");
				String grupo = rs.getString("Grupo");

				System.out.printf("NIA: " + nia + " | Nombre: " + nombre + " | Apellidos: " + apellidos + " | Género: "
						+ genero + " | Fecha Nacimiento: " + fechaNacimiento + " | Ciclo: " + ciclo + " | Curso: "
						+ curso + " | Grupo: " + grupo + "%n");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static void guardarAlumnosFichero() {
		String sql = "SELECT * FROM alumno";
		File fichero = new File("alumnos.txt");
		FileWriter fw = null;
		BufferedWriter bw = null;

		try {
			fw = new FileWriter(fichero, true);
			bw = new BufferedWriter(fw);
			Connection conection = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement stmt = conection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int nia = rs.getInt("NIA");
				String nombre = rs.getString("Nombre");
				String apellidos = rs.getString("Apellidos");
				String genero = rs.getString("Genero");
				Date fechaNacimiento = rs.getDate("Fecha_De_Nacimiento");
				String ciclo = rs.getString("Ciclo");
				String curso = rs.getString("Curso");
				String grupo = rs.getString("Grupo");

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				String fechaComoCadena = sdf.format(fechaNacimiento);

				bw.write("Nia: " + nia);
				bw.newLine();
				bw.write("Nombre: " + nombre);
				bw.newLine();
				bw.write("Apellidos: " + apellidos);
				bw.newLine();
				bw.write("Genero: " + genero);
				bw.newLine();
				bw.write("Fecha de Nacimiento: " + fechaComoCadena);
				bw.newLine();
				bw.write("Ciclo: " + ciclo);
				bw.newLine();
				bw.write("Curso: " + curso);
				bw.newLine();
				bw.write("Grupo: " + grupo);
				bw.newLine();
				bw.write("------------");
				bw.newLine();
			}
			System.out.println("Alumnos guardados en el fichero correctamente");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private static void leerYGuardarAlumnos(String archivo) {
		BufferedReader br = null;
		FileReader fr = null;
		try {
			String linea;
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);

			while ((linea = br.readLine()) != null) {

				String[] datos = linea.split(",");

				if (datos.length == 8) {

					int nia = Integer.parseInt(datos[0].trim());
					String nombre = datos[1].trim();
					String apellidos = datos[2].trim();
					String genero = datos[3].trim();
					String fechaNacimiento = datos[4].trim();
					String ciclo = datos[5].trim();
					String curso = datos[6].trim();
					String grupo = datos[7].trim();

					insertarAlumno(nia, nombre, apellidos, genero, fechaNacimiento, ciclo, curso, grupo);
				} else {
					System.out.println("Formato incorrecto en la línea: " + linea);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void modificarNombreAlumno(Scanner scanner) {
		System.out.print("Introduce el NIA del alumno a modificar: ");
		int nia = scanner.nextInt();
		scanner.nextLine();

		System.out.print("Introduce el nuevo nombre: ");
		String nuevoNombre = scanner.nextLine();

		String sql = "UPDATE alumno SET Nombre = ? WHERE NIA = ?";

		try {
			Connection conexion = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement consulta = conexion.prepareStatement(sql);

			consulta.setString(1, nuevoNombre);
			consulta.setInt(2, nia);

			int filasAfectadas = consulta.executeUpdate();
			if (filasAfectadas > 0) {
				System.out.println("¡Nombre modificado con éxito!");
			} else {
				System.out.println("No se encontró un alumno con ese NIA.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void eliminarAlumnoPorNIA(Scanner scanner) {
		System.out.print("Introduce el NIA del alumno a eliminar: ");
		int nia = scanner.nextInt();

		String sql = "DELETE FROM alumno WHERE NIA = ?";

		try {
			Connection conexion = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement consulta = conexion.prepareStatement(sql);

			consulta.setInt(1, nia);

			int filasAfectadas = consulta.executeUpdate();
			if (filasAfectadas > 0) {
				System.out.println("¡Alumno eliminado con éxito!");
			} else {
				System.out.println("No se encontró un alumno con ese NIA.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void eliminarAlumnosPorApellido(Scanner scanner) {
		System.out.print("Introduce la palabra a buscar en los apellidos: ");
		String palabra = scanner.nextLine();

		String sql = "DELETE FROM alumno WHERE Apellidos LIKE ?";

		try {
			Connection conexion = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement consulta = conexion.prepareStatement(sql);

			consulta.setString(1, "%" + palabra + "%");

			int filasAfectadas = consulta.executeUpdate();
			System.out.println(filasAfectadas + " alumnos eliminados.");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void guardarAlumnosEnXML() {
		String sql = "SELECT * FROM alumno";
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

		try {
			Connection conexion = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement consulta = conexion.createStatement();
			ResultSet resutado = consulta.executeQuery(sql);

			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();

			Element rootElement = doc.createElement("Alumnos");
			doc.appendChild(rootElement);

			while (resutado.next()) {
				Element alumno = doc.createElement("Alumno");
				rootElement.appendChild(alumno);

				Element nia = doc.createElement("NIA");
				nia.appendChild(doc.createTextNode(String.valueOf(resutado.getInt("NIA"))));
				alumno.appendChild(nia);

				Element nombre = doc.createElement("Nombre");
				nombre.appendChild(doc.createTextNode(resutado.getString("Nombre")));
				alumno.appendChild(nombre);

				Element apellidos = doc.createElement("Apellidos");
				apellidos.appendChild(doc.createTextNode(resutado.getString("Apellidos")));
				alumno.appendChild(apellidos);

				Element genero = doc.createElement("Genero");
				genero.appendChild(doc.createTextNode(resutado.getString("Genero")));
				alumno.appendChild(genero);

				Element fechaNacimiento = doc.createElement("FechaDeNacimiento");
				fechaNacimiento.appendChild(doc.createTextNode(resutado.getDate("Fecha_De_Nacimiento").toString()));
				alumno.appendChild(fechaNacimiento);

				Element ciclo = doc.createElement("Ciclo");
				ciclo.appendChild(doc.createTextNode(resutado.getString("Ciclo")));
				alumno.appendChild(ciclo);

				Element curso = doc.createElement("Curso");
				curso.appendChild(doc.createTextNode(resutado.getString("Curso")));
				alumno.appendChild(curso);

				Element grupo = doc.createElement("Grupo");
				grupo.appendChild(doc.createTextNode(resutado.getString("Grupo")));
				alumno.appendChild(grupo);
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformador = transformerFactory.newTransformer();
			DOMSource busca = new DOMSource(doc);
			StreamResult resltXMl = new StreamResult(new File("alumnos.xml"));
			transformador.setOutputProperty(OutputKeys.INDENT, "yes");
			transformador.transform(busca, resltXMl);

			System.out.println("Alumnos guardados en 'alumnos.xml'.");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

	private static void leerAlumnosDeXML() {
		File XMLfichero = new File("alumnos.xml");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document documento = db.parse(XMLfichero);
			documento.getDocumentElement().normalize();

			NodeList alumnoList = documento.getElementsByTagName("Alumno");

			for (int i = 0; i < alumnoList.getLength(); i++) {
				Node node = alumnoList.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;

					int nia = Integer.parseInt(element.getElementsByTagName("NIA").item(0).getTextContent());
					String nombre = element.getElementsByTagName("Nombre").item(0).getTextContent();
					String apellidos = element.getElementsByTagName("Apellidos").item(0).getTextContent();
					String genero = element.getElementsByTagName("Genero").item(0).getTextContent();
					String fechaNacimiento = element.getElementsByTagName("FechaDeNacimiento").item(0).getTextContent();
					String ciclo = element.getElementsByTagName("Ciclo").item(0).getTextContent();
					String curso = element.getElementsByTagName("Curso").item(0).getTextContent();
					String grupo = element.getElementsByTagName("Grupo").item(0).getTextContent();

					insertarAlumno(nia, nombre, apellidos, genero, fechaNacimiento, ciclo, curso, grupo);
				}
			}
			System.out.println("Alumnos leídos de 'alumnos.xml' e insertados en la BD.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
