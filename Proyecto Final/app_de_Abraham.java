import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class app_de_Abraham extends JFrame {
    // Atributos
    private JTextField textEmpleados;
    private JButton buscarButton;
    private JTextField textNombre;
    private JTextField textDomicilio;
    private JTextField textTelefono;
    private JTextField textEmail;
    private JTextField textFecha;
    private JTextField textGenero;
    private JButton btnGuardar;
    private JButton btnModificar;
    private JButton btneliminar;
    private JButton btnlimpiar;
    private JPanel Panel;
    private JLabel idEmpleados;
    private JLabel Nombre;
    private JLabel Domicilio;
    private JLabel Telefono;
    private JLabel Email;
    private JLabel Genero;
    private JLabel Fecha;
    private JLabel encabezado;
    Connection conexion;
    PreparedStatement ps;

    // Escucha de los botones
    public app_de_Abraham() {
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //conectar();
                try {
                    guardar();
                }catch (SQLException ex){
                    throw new RuntimeException(ex);
                }
            }
        });

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    buscar();
                }catch (SQLException ex){
                    throw new RuntimeException(ex);
                }
            }
        });

        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    modificar();
                }catch (SQLException ex){
                    throw new RuntimeException(ex);
                }
            }
        });

        btneliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    eliminar();
                }catch (SQLException ex){
                    throw new RuntimeException(ex);
                }
            }
        });

        btnlimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    limpiar();
                }catch (SQLException ex){
                    throw new RuntimeException(ex);
                }
            }
        });
    }


    // Metodo para guardar en la base de datos
    public void guardar() throws SQLException{
        conectar();
        ps = conexion.prepareStatement("INSERT INTO empleados (IdEmpleado,Nombre,Domicilio,Telefono,Email,fecha_nacimiento,Genero) VALUES (?,?,?,?,?,?,?)");
        ps.setInt(1, Integer.parseInt(textEmpleados.getText()));
        ps.setString(2, textNombre.getText());
        ps.setString(3, textDomicilio.getText());
        ps.setString(4, textTelefono.getText());
        ps.setString(5, textEmail.getText());
        ps.setString(6, textFecha.getText());
        ps.setString(7, textGenero.getText());
        ps.executeUpdate();
        JOptionPane.showMessageDialog(null, "Datos guardados correctamente.");
    }

    // Metodo para buscar en la base de datos
    public void buscar() throws SQLException{
        conectar();
        ps = conexion.prepareStatement("SELECT Nombre,Domicilio,Telefono,Email,fecha_nacimiento,Genero FROM empleados WHERE IdEmpleado = ?");
        ps.setInt(1, Integer.parseInt(textEmpleados.getText()));
        ResultSet r = ps.executeQuery();

        //next itera sobre cada uno de los datos de tu tabla
        if (r.next()){
            //obtener el valor del campo o input Nombre
            textNombre.setText(r.getString("Nombre"));
            textDomicilio.setText(r.getString("Domicilio"));
            textTelefono.setText(r.getString("Telefono"));
            textEmail.setText(r.getString("Email"));
            textFecha.setText(r.getString("fecha_nacimiento"));
            textGenero.setText(r.getString("Genero"));
            JOptionPane.showMessageDialog(null, "Datos encontrados");
        }else {
            JOptionPane.showMessageDialog(null, "No se encontro al Empleado");
        }
    }

    // Metodo para modificar un empledo en la base de datos
    public void modificar() throws SQLException{
        conectar();
        ps = conexion.prepareStatement("UPDATE empleados SET Nombre = ?, Domicilio = ?, Telefono = ?,Email = ?, fecha_nacimiento = ?, Genero = ? WHERE IdEmpleado = ?");
        ps.setString(1,textNombre.getText());
        ps.setString(2,textDomicilio.getText());
        ps.setString(3,textTelefono.getText());
        ps.setString(4,textEmail.getText());
        ps.setString(5,textFecha.getText());
        ps.setString(6,textGenero.getText());
        ps.setInt(7,Integer.parseInt(textEmpleados.getText()));


        int filasAfectadas = ps.executeUpdate();

        if (filasAfectadas > 0){
            JOptionPane.showMessageDialog(null, "Empleado Modificado");
        }else {
            JOptionPane.showMessageDialog(null, "Empleado no encontrado");
        }
    }

    // Metodo para eliminar un usuario en la base de datos
    public void eliminar() throws SQLException{
        conectar();
        ps = conexion.prepareStatement("DELETE FROM empleados WHERE IdEmpleado = ?");
        ps.setInt(1,Integer.parseInt(textEmpleados.getText()));

        int filasAfectadas = ps.executeUpdate();

        if (filasAfectadas > 0){
            JOptionPane.showMessageDialog(null, "Empleado Eliminado");
        }else {
            JOptionPane.showMessageDialog(null,"Empleado no encontrado");
        }
    }

    // Metodo para limpiar el panel
    public void limpiar() throws SQLException{
        conectar();
        textEmpleados.setText("");
        textNombre.setText("");
        textDomicilio.setText("");
        textTelefono.setText("");
        textEmail.setText("");
        textFecha.setText("");
        textGenero.setText("");

    }

    // Metodo para conectar la base de datos con el servidor PHPMYADMIN
    public void conectar(){
        try{
            // jdbc(Java Data Base Conection )
            conexion = DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/empresa", "root", "");

            System.out.println("conectado");
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    // Metodo para ejecutar la aplicacion
    public static void main(String[] args){
        //Mostrar el formulario
        app_de_Abraham crear = new app_de_Abraham();
        //Mostrar panelel panel con el objeto user de la clase app_de_Abraham
        crear.setContentPane(new app_de_Abraham().Panel);
        crear.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        crear.setVisible(true);
        crear.pack();
        crear.setLocationRelativeTo(null);
    }
}