package bar;

/**
 * Clase que repsresenta un bar y toda la infomarcion asociada a el
 * TODO: falta poner un campo para las imagenes
 */
public class Bar {
    private String nombre;
    private String descripcion;
    private String direccion;

    public String getEmail() {
        return email;
    }

    public String getFacebook() {
        return facebook;
    }

    public String getTelefono() {
        return telefono;
    }

    private String telefono;
    private String email;
    private String facebook;

    public Bar(String nombre, String descripcion, String direccion, String telefono, String email, String facebook) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.facebook = facebook;
    }

    /**
     * Metodo que devuelve el nombre de un bar
     *
     * @return nombre del bar
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Metodo que devuelve la descripcion de un bar
     *
     * @return descripcion del bar
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Metodo que devuelve la direccion de un bar
     *
     * @return direccion del bar
     */
    public String getDireccion() {
        return direccion;
    }


}
