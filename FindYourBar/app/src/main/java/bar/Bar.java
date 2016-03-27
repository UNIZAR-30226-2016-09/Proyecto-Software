package bar;

/**
 * Clase que repsresenta un bar y toda la infomarcion asociada a el
 * TODO: falta poner un campo para las imagenes
 */
public class Bar {
    private String nombre;
    private String descripcion;

    public Bar(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
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
}
