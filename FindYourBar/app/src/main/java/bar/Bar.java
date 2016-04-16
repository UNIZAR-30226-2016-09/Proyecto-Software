package bar;

import java.util.List;

/**
 * Clase que repsresenta un bar y toda la infomarcion asociada a el
 */
public class Bar {

    private String nombre;
    private String descripcion;
    private String direccion;
    private String facebook;
    private String principal;
    private List<String> secundaria;
    private List<String> eventos;
    private int edad;
    private float horaCierre;
    private float horaApertura;
    private List<String> musica;


    public Bar(String nombre, String descripcion, String direccion, String telefono, String email,
               String facebook, String principal, List<String> secundaria, List<String> eventos, int edad,
               float horaCierre, float horaApertura, List<String> musica) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.facebook = facebook;
        this.principal = principal;
        this.secundaria = secundaria;
        this.eventos = eventos;
        this.edad = edad;
        this.horaCierre = horaCierre;
        this.horaApertura = horaApertura;
        this.musica = musica;
    }

    /**
     * Devuelve verdad si el bar contiene el tipo de musica especificado
     *
     * @param genre
     * @return
     */
    public boolean hasMusicGenre(String genre) {
        return musica.contains(genre);
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

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public List getSecundaria() {
        return secundaria;
    }

    public void setSecundaria(List secundaria) {
        this.secundaria = secundaria;
    }

    public List getEventos() {
        return eventos;
    }

    public void setEventos(List eventos) {
        this.eventos = eventos;
    }

    public int getEdad() {
        return edad;
    }

    public float getHoraCierre() {
        return horaCierre;
    }

    public float getHoraApertura() {
        return horaApertura;
    }

    public List getMusica() {
        return musica;
    }

    public void setMusica(List musica) {
        this.musica = musica;
    }

}
